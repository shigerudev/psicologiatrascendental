package org.psicologiatrascendental.agents;

import org.psicologiatrascendental.domain.Constructo;
import org.psicologiatrascendental.domain.MitPhase;

/**
 * Agente Existencial: sentido, logoterapia, propósito.
 * Responsable de constructos I–III (conciencia, propósito, sentido).
 *
 * Basado en: Frankl (1946) - Voluntad de sentido.
 */
public interface ExistentialAgent {

    /**
     * Genera contexto de prompt enriquecido con perspectiva existencial
     * para el Game Controller.
     */
    String enrichPromptContext(Constructo constructo, MitPhase phase, String previousMemory);
}
