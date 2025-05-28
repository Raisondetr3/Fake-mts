package ru.itmo.node_a_core.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmo.node_a_core.service.CodeStorage;

import java.util.Map;

@RestController
@RequestMapping("/api/debug")
@RequiredArgsConstructor
public class DebugController {
    private final CodeStorage codeStorage;

    @GetMapping("/codes")
    public ResponseEntity<Map<String, String>> getAllCodes() {
        return ResponseEntity.ok(codeStorage.getAllCodes());
    }
}
