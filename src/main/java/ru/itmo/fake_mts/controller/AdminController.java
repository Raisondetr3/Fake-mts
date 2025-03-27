package ru.itmo.fake_mts.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itmo.fake_mts.dto.AdminRequestDTO;
import ru.itmo.fake_mts.dto.SimpleResponse;
import ru.itmo.fake_mts.service.AdminRequestService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminRequestService adminRequestService;

    @PostMapping("/request")
    public ResponseEntity<SimpleResponse> requestAdminApproval() {
        SimpleResponse responseMessage = adminRequestService.requestAdminApproval();
        return ResponseEntity.ok(responseMessage);
    }

    @GetMapping("/requests/status")
    public ResponseEntity<SimpleResponse> getAdminRequestStatus() {
        SimpleResponse statusMessage = adminRequestService.getAdminRequestStatus();
        return ResponseEntity.ok(statusMessage);
    }

    @GetMapping("/requests")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AdminRequestDTO>> getAdminApprovalRequests() {
        List<AdminRequestDTO> pendingRequests = adminRequestService.getAdminApprovalRequests();
        return ResponseEntity.ok(pendingRequests);
    }

    @PostMapping("/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SimpleResponse> approveAdminRequest(@RequestParam Long targetUserId) {
        adminRequestService.approveAdminRequest(targetUserId);
        return ResponseEntity.ok(new SimpleResponse("Administrator rights granted."));
    }

    @PostMapping("/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SimpleResponse> rejectAdminRequest(@RequestParam Long targetUserId) {
        adminRequestService.rejectAdminRequest(targetUserId);
        return ResponseEntity.ok(new SimpleResponse("Admin request denied."));
    }
}

