package org.psicologiatrascendental.domain;

import java.util.UUID;

/**
 * Nodo del juego de ficción interactiva.
 * Corresponde a un ítem de escala trascendental rediseñado en narrativa.
 */
public record GameNode(
    UUID id,
    UUID sessionId,
    Constructo constructo,
    int phaseIndex,
    String narrativeParagraph,
    String instructionA,
    String instructionB,
    double scoreA,
    double scoreB,
    String memorySnapshot
) {}
