package org.psicologiatrascendental.agents.impl;

import org.psicologiatrascendental.agents.AgentType;
import org.psicologiatrascendental.agents.OrchestratorAgent;
import org.psicologiatrascendental.domain.Constructo;
import org.psicologiatrascendental.domain.MitPhase;
import org.springframework.stereotype.Component;

/**
 * Implementación del Orquestador según TRANSCENDENTAL_SPECS.
 * Enruta a Jungian o Existencial según constructo.
 */
@Component
public class DefaultOrchestratorAgent implements OrchestratorAgent {

    private static final double THRESHOLD_LOW = 0.3;
    private static final double THRESHOLD_HIGH = 0.9;
    private static final int MANUAL_REVIEW_INTERVAL = 5;

    @Override
    public AgentType selectSpecialistAgent(Constructo constructo) {
        return switch (constructo) {
            case CONCIENCIA_TRASCENDENTAL, PROPOSITO_TRASCENDENTAL, SENTIDO_DE_VIDA -> AgentType.EXISTENTIAL;
            case ENERGIA_PSIQUICA, ARQUETIPO, INTEGRACION, TRASCENDENCIA -> AgentType.JUNGIAN;
        };
    }

    @Override
    public boolean shouldTriggerApprovalGate(Constructo constructo, double accumulatedScore, int nodeIndex) {
        if (accumulatedScore < THRESHOLD_LOW || accumulatedScore > THRESHOLD_HIGH) {
            return true; // THRESHOLD_ALERT
        }
        if (nodeIndex > 0 && nodeIndex % MANUAL_REVIEW_INTERVAL == 0) {
            return true; // MANUAL_REVIEW
        }
        return false;
    }
}
