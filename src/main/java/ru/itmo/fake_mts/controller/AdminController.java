package ru.itmo.fake_mts.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmo.fake_mts.dto.AdminRequestDTO;
import ru.itmo.fake_mts.service.AdminRequestService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminRequestService adminRequestService;

    @PostMapping("/request")
    public ResponseEntity<String> requestAdminApproval() {
        String responseMessage = adminRequestService.requestAdminApproval();
        return ResponseEntity.ok(responseMessage);
    }

    @GetMapping("/requests/status")
    public ResponseEntity<String> getAdminRequestStatus() {
        String statusMessage = adminRequestService.getAdminRequestStatus();
        return ResponseEntity.ok(statusMessage);
    }

    @GetMapping("/requests")
    public ResponseEntity<List<AdminRequestDTO>> getAdminApprovalRequests() {
        List<AdminRequestDTO> pendingRequests = adminRequestService.getAdminApprovalRequests();
        return ResponseEntity.ok(pendingRequests);
    }

    @PostMapping("/approve")
    public ResponseEntity<String> approveAdminRequest(@RequestParam Long targetUserId) {
        adminRequestService.approveAdminRequest(targetUserId);
        return ResponseEntity.ok("Administrator rights granted.");
    }

    @PostMapping("/reject")
    public ResponseEntity<String> rejectAdminRequest(@RequestParam Long targetUserId) {
        adminRequestService.rejectAdminRequest(targetUserId);
        return ResponseEntity.ok("Admin request denied.");
    }
}

