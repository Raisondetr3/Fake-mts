package ru.itmo.fake_mts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.fake_mts.admin_status.*;
import ru.itmo.fake_mts.dto.AdminRequestDTO;
import ru.itmo.fake_mts.dto.SimpleResponse;
import ru.itmo.fake_mts.entity.User;
import ru.itmo.fake_mts.entity.enums.AdminRequestStatus;
import ru.itmo.fake_mts.entity.enums.Role;
import ru.itmo.fake_mts.exception.AdminAlreadyGrantedException;
import ru.itmo.fake_mts.exception.AdminRequestNotFoundException;
import ru.itmo.fake_mts.exception.UserNotFoundException;
import ru.itmo.fake_mts.repo.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminRequestService {

    private final UserRepository userRepository;
    private final Map<String, AdminRequestStatusHandler> statusHandlers = Map.of(
            "ACCEPTED", new AcceptedStatusHandler(),
            "NONE", new NoneStatusHandler(),
            "PENDING", new PendingStatusHandler(),
            "REJECTED", new RejectedStatusHandler()
    );

    private final CurrentUserService currentUserService;

    @Transactional
    public SimpleResponse requestAdminApproval() {
        User user = currentUserService.getCurrentUserOrThrow();

        if (user.getRoles().contains(Role.ADMIN)) {
            throw new AdminAlreadyGrantedException("User already has ADMIN rights.");
        }

        if (userRepository.existsByRolesContaining(Role.ADMIN)) {
            user.setAdminRequestStatus(AdminRequestStatus.PENDING);
            userRepository.save(user);
            return new SimpleResponse("The request for ADMIN rights has been submitted for review.");
        } else {
            Set<Role> roles = new HashSet<>(user.getRoles());
            roles.add(Role.ADMIN);
            user.setRoles(roles);
            user.setAdminRequestStatus(AdminRequestStatus.ACCEPTED);
            userRepository.save(user);
            return new SimpleResponse("There are no administrators in the system. The user has been granted ADMIN rights immediately.");
        }
    }

    @Transactional(readOnly = true)
    public SimpleResponse getAdminRequestStatus() {
        User user = currentUserService.getCurrentUserOrThrow();

        AdminRequestStatusHandler handler = statusHandlers.get(user.getAdminRequestStatus().name());
        if (handler == null) {
            throw new IllegalStateException("Handler not found for status: " + user.getAdminRequestStatus());
        }
        return new SimpleResponse(handler.getStatusMessage());
    }

    @Transactional(readOnly = true)
    public List<AdminRequestDTO> getAdminApprovalRequests() {
        List<User> pendingUsers = userRepository.findAllByAdminRequestStatus(AdminRequestStatus.PENDING);
        return pendingUsers.stream()
                .map(user -> new AdminRequestDTO(
                        user.getId(),
                        user.getPhoneNumber(),
                        user.getFullName(),
                        user.getAdminRequestStatus().name()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void approveAdminRequest(Long targetUserId) {
        User user = userRepository.findById(targetUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user.getAdminRequestStatus() != AdminRequestStatus.PENDING) {
            throw new AdminRequestNotFoundException("No pending admin approval request for this user.");
        }

        Set<Role> roles = new HashSet<>(user.getRoles());
        roles.add(Role.ADMIN);
        user.setRoles(roles);
        user.setAdminRequestStatus(AdminRequestStatus.ACCEPTED);
        userRepository.save(user);
    }

    @Transactional
    public void rejectAdminRequest(Long targetUserId) {
        User user = userRepository.findById(targetUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user.getAdminRequestStatus() != AdminRequestStatus.PENDING) {
            throw new AdminRequestNotFoundException("No pending admin approval request for this user.");
        }

        user.setAdminRequestStatus(AdminRequestStatus.REJECTED);
        userRepository.save(user);
    }
}