package org.psicologiatrascendental.web;

import org.psicologiatrascendental.application.ConsciousnessProfile;
import org.psicologiatrascendental.application.SessionOrchestrator;
import org.psicologiatrascendental.domain.GameNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

/**
 * API REST para sesiones de juego Espejo Espiritual.
 */
@RestController
@RequestMapping("/api/session")
@CrossOrigin(origins = "*") // Configurar CORS en producción
public class SessionController {

    private final SessionOrchestrator orchestrator;

    public SessionController(SessionOrchestrator orchestrator) {
        this.orchestrator = orchestrator;
    }

    @PostMapping("/start")
    public ResponseEntity<Map<String, Object>> startSession(
            @RequestBody(required = false) StartSessionRequest request
    ) {
        String gameType = request != null && request.gameType() != null ? request.gameType() : "mystical";
        String gameTopic = request != null && request.gameTopic() != null ? request.gameTopic() : "";
        UUID sessionId = orchestrator.startSession(gameType, gameTopic);
        GameNode firstNode = orchestrator.getNextNode(sessionId);
        return ResponseEntity.ok(Map.of(
            "sessionId", sessionId.toString(),
            "node", toNodeDto(firstNode)
        ));
    }

    @PostMapping("/{sessionId}/decision")
    public ResponseEntity<Map<String, Object>> recordDecision(
            @PathVariable UUID sessionId,
            @RequestBody DecisionRequest request
    ) {
        GameNode nextNode = orchestrator.recordDecision(
            sessionId, request.nodeId(), request.selectedA()
        );
        if (nextNode == null) {
            // Puede ser: sesión completada o requiere aprobación
            ConsciousnessProfile profile = orchestrator.getConsciousnessProfile(sessionId);
            if (profile.nodesCompleted() >= 1) {
                // Sesión completada: todas las decisiones hechas
                return ResponseEntity.ok(Map.of(
                    "requiresApproval", false,
                    "sessionComplete", true,
                    "profile", profile
                ));
            }
            return ResponseEntity.ok(Map.of(
                "requiresApproval", true,
                "sessionComplete", false,
                "message", "Sesión en cola de revisión. Un psicólogo supervisará antes de continuar."
            ));
        }
        return ResponseEntity.ok(Map.of(
            "requiresApproval", false,
            "sessionComplete", false,
            "node", toNodeDto(nextNode)
        ));
    }

    @GetMapping("/{sessionId}/profile")
    public ResponseEntity<ConsciousnessProfile> getProfile(@PathVariable UUID sessionId) {
        return ResponseEntity.ok(orchestrator.getConsciousnessProfile(sessionId));
    }

    private Map<String, Object> toNodeDto(GameNode node) {
        return Map.of(
            "id", node.id().toString(),
            "constructo", node.constructo().name(),
            "paragraph", node.narrativeParagraph(),
            "instructionA", node.instructionA(),
            "instructionB", node.instructionB()
        );
    }

    public record StartSessionRequest(String gameType, String gameTopic) {}
    public record DecisionRequest(java.util.UUID nodeId, boolean selectedA) {}
}
