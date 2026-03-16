package org.psicologiatrascendental.infrastructure.supervisor.impl;

import org.psicologiatrascendental.domain.ApprovalGate;
import org.psicologiatrascendental.domain.Constructo;
import org.psicologiatrascendental.infrastructure.supervisor.ApprovalGateService;
import org.psicologiatrascendental.infrastructure.supervisor.SessionSummary;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementación en memoria del servicio de puertas de aprobación.
 * Para producción: persistir en BD y conectar con SessionOrchestrator.
 */
@Service
public class InMemoryApprovalGateService implements ApprovalGateService {

    private final Map<UUID, ApprovalGate> gates = new ConcurrentHashMap<>();
    private final Map<UUID, SessionSummary> summaries = new ConcurrentHashMap<>();

    @Override
    public List<ApprovalGate> listPending() {
        return gates.values().stream()
            .filter(g -> g.status() == ApprovalGate.ApprovalStatus.PENDING)
            .toList();
    }

    @Override
    public void approve(UUID gateId, UUID psychologistId, String notes) {
        ApprovalGate g = gates.get(gateId);
        if (g != null) {
            gates.put(gateId, new ApprovalGate(
                g.id(), g.sessionId(), g.triggerReason(),
                ApprovalGate.ApprovalStatus.APPROVED,
                psychologistId, Instant.now(), notes
            ));
        }
    }

    @Override
    public void reject(UUID gateId, UUID psychologistId, String notes) {
        ApprovalGate g = gates.get(gateId);
        if (g != null) {
            gates.put(gateId, new ApprovalGate(
                g.id(), g.sessionId(), g.triggerReason(),
                ApprovalGate.ApprovalStatus.REJECTED,
                psychologistId, Instant.now(), notes
            ));
        }
    }

    @Override
    public SessionSummary getSessionSummary(UUID sessionId) {
        SessionSummary s = summaries.get(sessionId);
        if (s != null) return s;
        return new SessionSummary(
            sessionId, "fantasy", "adventure", 0,
            Map.of(), 0.0
        );
    }
}
