package ru.itmo.node_a_core.controller;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmo.common.dto.StartProcess;
import ru.itmo.node_a_core.camunda.ProcessStarter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/workflow")
public class WorkflowController {
    private final ProcessStarter processStarter;

    @PostMapping("/start")
    public ResponseEntity<String> startProcess(
            @RequestBody StartProcess dto
    ) {
        ProcessInstance pi = processStarter.start(dto.getProcessId());
        return ResponseEntity.ok("Started process instance id = " + pi.getId());
    }
}

