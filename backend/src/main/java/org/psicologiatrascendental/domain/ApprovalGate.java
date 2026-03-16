package org.psicologiatrascendental.domain;

import java.time.Instant;
import java.util.UUID;

/**
 * Puerta de aprobación Human-in-the-Loop.
 * Permite que un psicólogo supervise antes de continuar la sesión.
 */
public record ApprovalGate(
    UUID id,
    UUID sessionId,
    ApprovalTrigger triggerReason,
    ApprovalStatus status,
    UUID psychologistId,
    Instant reviewedAt,
    String notes
) {
    public enum ApprovalTrigger {
        THRESHOLD_ALERT,
        CRISIS_KEYWORD,
        MANUAL_REVIEW,
        SESSION_COMPLETE
    }

    public enum ApprovalStatus {
        PENDING,
        APPROVED,
        REJECTED
    }
}
