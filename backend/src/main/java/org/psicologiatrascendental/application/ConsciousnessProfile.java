package org.psicologiatrascendental.application;

import org.psicologiatrascendental.domain.Constructo;

import java.util.Map;

/**
 * Perfil de expansión de conciencia del usuario.
 * Agregación de scores por constructo a partir de decisiones en juego.
 */
public record ConsciousnessProfile(
    Map<Constructo, Double> constructoScores,
    double indiceConcienciaTrascendental,
    int nodesCompleted
) {
    /**
     * ICT = promedio ponderado de scores por constructo.
     */
    public static double computeICT(Map<Constructo, Double> scores) {
        if (scores == null || scores.isEmpty()) return 0.0;
        return scores.values().stream()
            .mapToDouble(Double::doubleValue)
            .average()
            .orElse(0.0);
    }
}
