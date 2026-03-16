package org.psicologiatrascendental.application;

import org.psicologiatrascendental.domain.Constructo;
import org.psicologiatrascendental.domain.GameNode;
import org.psicologiatrascendental.domain.MitPhase;

import java.util.UUID;

/**
 * Coordina la sesión de juego: fases MIT, nodos, decisiones.
 * Punto central de orquestación Multi-Agente.
 */
public interface SessionOrchestrator {

    /**
     * Inicia una nueva sesión de juego.
     */
    UUID startSession(String gameType, String gameTopic);

    /**
     * Obtiene el siguiente nodo para la sesión.
     */
    GameNode getNextNode(UUID sessionId);

    /**
     * Registra la decisión del usuario y avanza el flujo.
     * Retorna el siguiente nodo o null si la sesión requiere aprobación.
     */
    GameNode recordDecision(UUID sessionId, UUID nodeId, boolean selectedA);

    /**
     * Obtiene el perfil de conciencia actual de la sesión.
     */
    ConsciousnessProfile getConsciousnessProfile(UUID sessionId);
}
