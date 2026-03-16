package org.psicologiatrascendental.infrastructure.supervisor;

import org.psicologiatrascendental.domain.Constructo;

import java.util.Map;
import java.util.UUID;

/**
 * Resumen de sesión para revisión del psicólogo supervisor.
 */
public record SessionSummary(
    UUID sessionId,
    String gameType,
    String gameTopic,
    int nodesCompleted,
    Map<Constructo, Double> constructoScores,
    double indiceConcienciaTrascendental
) {}
