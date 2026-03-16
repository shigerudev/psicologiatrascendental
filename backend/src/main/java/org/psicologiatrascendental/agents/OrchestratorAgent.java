package org.psicologiatrascendental.agents;

import org.psicologiatrascendental.domain.Constructo;
import org.psicologiatrascendental.domain.MitPhase;

/**
 * Agente Orquestador: dirige el flujo del juego según escalas psicológicas.
 *
 * Reglas de enrutamiento (TRANSCENDENTAL_SPECS):
 * - Constructos I–III (conciencia, propósito, sentido) → Agente Existencial
 * - Constructos IV–VII (energía, arquetipo, integración, trascendencia) → Agente Jungiano
 */
public interface OrchestratorAgent {

    /**
     * Selecciona el agente especializado según el constructo actual.
     */
    AgentType selectSpecialistAgent(Constructo constructo);

    /**
     * Determina si se debe disparar una puerta de aprobación Human-in-the-Loop.
     */
    boolean shouldTriggerApprovalGate(Constructo constructo, double accumulatedScore, int nodeIndex);
}
