package ru.itmo.node_a_core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.common.admin_status.*;
import ru.itmo.common.dto.AdminRequestDTO;
import ru.itmo.common.dto.AdminRequestMessage;
import ru.itmo.common.dto.SimpleResponse;
import ru.itmo.common.entity.User;
import ru.itmo.common.entity.enums.AdminRequestStatus;
import ru.itmo.common.entity.enums.Role;
import ru.itmo.common.exception.AdminAlreadyGrantedException;
import ru.itmo.common.exception.AdminRequestNotFoundException;
import ru.itmo.common.exception.UserNotFoundException;
import ru.itmo.common.repo.UserRepository;
import ru.itmo.node_a_core.config.MqttProps;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminRequestService {

    private final UserRepository userRepository;
    private final MqttPublisherService publisher;
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

            publisher.publish(new AdminRequestMessage(user.getEmail(), "Request for admin approval submitted"),
                    "admin.queue");

            return new SimpleResponse("The request for ADMIN rights has been submitted for review.");
        } else {
            Set<Role> roles = new HashSet<>(user.getRoles());
            roles.add(Role.ADMIN);
            user.setRoles(roles);
            user.setAdminRequestStatus(AdminRequestStatus.ACCEPTED);
            userRepository.save(user);
            return new SimpleResponse("There are no administrators in the system." +
                    " The user has been granted ADMIN rights immediately.");
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

        publisher.publish(new AdminRequestMessage(user.getEmail(), "Request for admin approval approved"),
                "admin.queue");
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

        publisher.publish(new AdminRequestMessage(user.getEmail(), "Request for admin approval rejected"),
                "admin.queue");
    }
}