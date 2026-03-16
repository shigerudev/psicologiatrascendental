package org.psicologiatrascendental.web;

import org.psicologiatrascendental.infrastructure.supervisor.ApprovalGateService;
import org.psicologiatrascendental.infrastructure.supervisor.SessionSummary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * API de supervisión Human-in-the-Loop para psicólogos.
 * Ver TRANSCENDENTAL_SPECS §4.
 */
@RestController
@RequestMapping("/api/supervisor")
@CrossOrigin(origins = "*")
public class SupervisorController {

    private final ApprovalGateService approvalGateService;

    public SupervisorController(ApprovalGateService approvalGateService) {
        this.approvalGateService = approvalGateService;
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Map<String, Object>>> listPending() {
        var gates = approvalGateService.listPending();
        var dtos = gates.stream()
            .map(g -> Map.<String, Object>of(
                "id", g.id().toString(),
                "sessionId", g.sessionId().toString(),
                "trigger", g.triggerReason().name(),
                "status", g.status().name()
            ))
            .toList();
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/approve/{gateId}")
    public ResponseEntity<Void> approve(
            @PathVariable UUID gateId,
            @RequestBody ApprovalRequest request
    ) {
        approvalGateService.approve(gateId, request.psychologistId(), request.notes());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reject/{gateId}")
    public ResponseEntity<Void> reject(
            @PathVariable UUID gateId,
            @RequestBody ApprovalRequest request
    ) {
        approvalGateService.reject(gateId, request.psychologistId(), request.notes());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/session/{sessionId}/summary")
    public ResponseEntity<SessionSummary> getSessionSummary(@PathVariable UUID sessionId) {
        return ResponseEntity.ok(approvalGateService.getSessionSummary(sessionId));
    }

    public record ApprovalRequest(UUID psychologistId, String notes) {}
}
