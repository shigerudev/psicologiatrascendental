package org.psicologiatrascendental.agents;

import org.psicologiatrascendental.domain.Constructo;
import org.psicologiatrascendental.domain.MitPhase;

/**
 * Agente Jungiano: interpretación arquetípica, sombra, individuación.
 * Responsable de constructos IV–VII (energía, arquetipo, integración, trascendencia).
 *
 * Basado en: Jung (1959) - Arquetipos e inconsciente colectivo.
 */
public interface JungianAgent {

    /**
     * Genera contexto de prompt enriquecido con perspectiva junguiana
     * para el Game Controller.
     */
    String enrichPromptContext(Constructo constructo, MitPhase phase, String previousMemory);
}
