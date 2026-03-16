package org.psicologiatrascendental.infrastructure.supervisor;

import org.psicologiatrascendental.domain.ApprovalGate;

import java.util.List;
import java.util.UUID;

/**
 * Servicio de supervisión Human-in-the-Loop.
 * Gestiona puertas de aprobación para auditoría por psicólogo.
 */
public interface ApprovalGateService {

    /**
     * Lista puertas pendientes de revisión.
     */
    List<ApprovalGate> listPending();

    /**
     * Aprueba la continuidad de una sesión.
     */
    void approve(UUID gateId, UUID psychologistId, String notes);

    /**
     * Rechaza y registra motivo.
     */
    void reject(UUID gateId, UUID psychologistId, String notes);

    /**
     * Obtiene resumen de sesión para revisión.
     */
    SessionSummary getSessionSummary(UUID sessionId);
}
