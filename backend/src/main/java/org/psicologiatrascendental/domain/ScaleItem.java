package org.psicologiatrascendental.domain;

/**
 * Ítem de la escala trascendental rediseñado en narrativa.
 * Inspirado en PsychoGAT: cada decisión mapea a scores psicométricos.
 */
public record ScaleItem(
    Constructo constructo,
    MitPhase phase,
    String narrativeParagraph,
    String instructionA,
    String instructionB,
    double scoreA,  // 0.0 = ausencia, 1.0 = presencia del constructo
    double scoreB
) {}
