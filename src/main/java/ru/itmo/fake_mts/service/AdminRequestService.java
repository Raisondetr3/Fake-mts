package ru.itmo.fake_mts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.fake_mts.dto.AdminRequestDTO;
import ru.itmo.fake_mts.entity.User;
import ru.itmo.fake_mts.entity.enums.AdminRequestStatus;
import ru.itmo.fake_mts.entity.enums.Role;
import ru.itmo.fake_mts.exception.AdminRequestNotFoundException;
import ru.itmo.fake_mts.exception.UserNotFoundException;
import ru.itmo.fake_mts.repo.UserRepository;
import ru.itmo.fake_mts.adminStatus.AdminRequestStatusHandler;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminRequestService {

    private final UserRepository userRepository;
    private final Map<String, AdminRequestStatusHandler> statusHandlers;
    private final CurrentUserService currentUserService;

    @Transactional(readOnly = true)
    public boolean doesAdminExist() {
        return userRepository.existsByRolesContaining(Role.ADMIN);
    }

    @Transactional
    public String requestAdminApproval() {
        User user = currentUserService.getCurrentUserOrThrow();

        if (user.isAdmin()) {
            throw new IllegalArgumentException("User already has ADMIN rights.");
        }

        if (!doesAdminExist()) {
            Set<Role> roles = new HashSet<>(user.getRoles());
            roles.add(Role.ADMIN);
            user.setRoles(roles);
            user.setAdminRequestStatus(AdminRequestStatus.ACCEPTED);
            userRepository.save(user);
            return "There are no administrators in the system. The user has been granted ADMIN rights immediately.";
        } else {
            user.setAdminRequestStatus(AdminRequestStatus.PENDING);
            userRepository.save(user);
            return "The request for ADMIN rights has been submitted for review.";
        }
    }

    @Transactional(readOnly = true)
    public String getAdminRequestStatus() {
        User user = currentUserService.getCurrentUserOrThrow();

        AdminRequestStatusHandler handler = statusHandlers.get(user.getAdminRequestStatus().name());
        if (handler == null) {
            throw new IllegalStateException("Handler not found for status: " + user.getAdminRequestStatus());
        }
        return handler.getStatusMessage();
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
