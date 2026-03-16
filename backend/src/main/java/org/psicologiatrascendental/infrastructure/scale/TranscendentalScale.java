package org.psicologiatrascendental.infrastructure.scale;

import org.psicologiatrascendental.domain.Constructo;
import org.psicologiatrascendental.domain.MitPhase;
import org.psicologiatrascendental.domain.ScaleItem;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Escala trascendental basada en Psicología Trascendental (Arias).
 * Items narrativos inspirados en PsychoGAT: ficción interactiva que mapea
 * decisiones a los 7 constructos y 5 fases MIT.
 */
@Component
public class TranscendentalScale {

    private final List<ScaleItem> items;

    public TranscendentalScale() {
        this.items = buildScale();
    }

    public List<ScaleItem> getItems() {
        return items;
    }

    public int size() {
        return items.size();
    }

    public ScaleItem get(int index) {
        return items.get(index);
    }

    private static List<ScaleItem> buildScale() {
        return List.of(
            // === CONCIENCIA TRASCENDENTAL (I) ===
            new ScaleItem(
                Constructo.CONCIENCIA_TRASCENDENTAL,
                MitPhase.RECONOCIMIENTO,
                "Te encuentras en un espacio entre el sueño y la vigilia. Una voz interior te invita a observar tu respiración. " +
                    "Sientes que algo más grande que tu identidad cotidiana está presente.",
                "A. Cierras los ojos y te entregas a la observación, permitiendo que la conciencia se expanda sin forzar.",
                "B. Te mantienes alerta pero distante, analizando la experiencia desde la razón.",
                0.85, 0.35
            ),
            new ScaleItem(
                Constructo.CONCIENCIA_TRASCENDENTAL,
                MitPhase.CONFRONTACION,
                "En un sueño recurrente, atraviesas un laberinto de espejos. Cada reflejo muestra una faceta distinta de ti. " +
                    "Al final del pasillo, una luz difusa parece llamarte.",
                "A. Avanzas hacia la luz, aceptando que cada reflejo es parte de un todo mayor.",
                "B. Te detienes ante el primer espejo, intentando comprender antes de seguir.",
                0.80, 0.40
            ),
            // === PROPÓSITO TRASCENDENTAL (II) ===
            new ScaleItem(
                Constructo.PROPOSITO_TRASCENDENTAL,
                MitPhase.RECONOCIMIENTO,
                "Un mentor simbólico te pregunta: «¿Qué te mueve cuando nada externo te obliga?» " +
                    "La pregunta resuena en tu interior como un eco antiguo.",
                "A. Respondes desde la intuición: «Algo en mí sabe, aunque no pueda nombrarlo».",
                "B. Buscas en tu memoria experiencias concretas que justifiquen una respuesta.",
                0.75, 0.45
            ),
            new ScaleItem(
                Constructo.PROPOSITO_TRASCENDENTAL,
                MitPhase.INTEGRACION,
                "Has llegado a una encrucijada. Un camino lleva a la seguridad conocida; el otro, a lo desconocido pero resonante. " +
                    "Sientes que tu propósito está ligado a uno de ellos.",
                "A. Eliges el camino resonante, confiando en que el sentido se revelará al caminar.",
                "B. Prefieres el camino conocido, convencido de que el propósito puede esperar.",
                0.80, 0.30
            ),
            // === SENTIDO DE VIDA (III) ===
            new ScaleItem(
                Constructo.SENTIDO_DE_VIDA,
                MitPhase.RECONOCIMIENTO,
                "En un momento de crisis, alguien cercano te dice: «La vida nunca deja de tener sentido». " +
                    "Te preguntas qué significa eso para ti en este instante.",
                "A. Sientes que el sufrimiento puede transformarse en crecimiento si encuentras un «para qué».",
                "B. Te cuesta ver sentido cuando el dolor es intenso; buscas respuestas racionales.",
                0.70, 0.50
            ),
            new ScaleItem(
                Constructo.SENTIDO_DE_VIDA,
                MitPhase.MANIFESTACION,
                "Llegas al final de un viaje interior. Tienes la oportunidad de compartir lo vivido con otros. " +
                    "La pregunta es cómo hacerlo.",
                "A. Compartes desde la autenticidad, sin pretender convencer, solo testimoniar.",
                "B. Prefieres guardarlo para ti hasta tener certeza de que «funciona».",
                0.75, 0.40
            ),
            // === ENERGÍA PSÍQUICA (IV) ===
            new ScaleItem(
                Constructo.ENERGIA_PSIQUICA,
                MitPhase.CONFRONTACION,
                "Sientes un bloqueo interno. Una energía antigua parece atrapada en tu cuerpo. " +
                    "Un guía simbólico te invita a explorarla.",
                "A. Te permites sentir la emoción sin reprimirla, dejando que fluya.",
                "B. Prefieres racionalizar el bloqueo antes de soltarlo.",
                0.80, 0.35
            ),
            new ScaleItem(
                Constructo.ENERGIA_PSIQUICA,
                MitPhase.INTEGRACION,
                "Tras un periodo de desequilibrio, notas que tu cuerpo y tu mente buscan armonía. " +
                    "Hay una sensación de «volver a casa».",
                "A. Te entregas al proceso de restablecer la coherencia, confiando en el ritmo interno.",
                "B. Te esfuerzas por controlar cada aspecto del proceso.",
                0.70, 0.45
            ),
            // === ARQUETIPO (V) ===
            new ScaleItem(
                Constructo.ARQUETIPO,
                MitPhase.CONFRONTACION,
                "En un sueño, encuentras un símbolo universal: un mandala, un árbol, un héroe. " +
                    "El símbolo parece portar un mensaje para ti.",
                "A. Te acercas al símbolo con curiosidad, permitiendo que hable a tu inconsciente.",
                "B. Intentas interpretarlo con la mente consciente, buscando significados conocidos.",
                0.75, 0.40
            ),
            new ScaleItem(
                Constructo.ARQUETIPO,
                MitPhase.TRASCENDENCIA,
                "Un arquetipo —el sabio, el cuidador, el creador— se manifiesta en tu vida cotidiana. " +
                    "Reconoces su presencia en tus acciones recientes.",
                "A. Integras el arquetipo como guía, sin identificarte exclusivamente con él.",
                "B. Lo observas desde fuera, como un concepto interesante.",
                0.80, 0.35
            ),
            // === INTEGRACIÓN (VI) ===
            new ScaleItem(
                Constructo.INTEGRACION,
                MitPhase.INTEGRACION,
                "Te das cuenta de que tienes aspectos que has rechazado: la sombra, la vulnerabilidad, la ira. " +
                    "La meta no es la perfección, sino la totalidad.",
                "A. Te abres a reconocer y reconciliar esas partes, aceptando la complejidad.",
                "B. Prefieres seguir trabajando solo en lo que consideras «positivo».",
                0.85, 0.30
            ),
            new ScaleItem(
                Constructo.INTEGRACION,
                MitPhase.MANIFESTACION,
                "Ves que razón e intuición, cuerpo y mente, pueden trabajar juntos. " +
                    "La unidad interior se refleja en tus decisiones diarias.",
                "A. Actúas desde esa unidad, sin forzar la coherencia.",
                "B. Aún separas «lo racional» de «lo intuitivo» en la práctica.",
                0.75, 0.45
            ),
            // === TRASCENDENCIA (VII) ===
            new ScaleItem(
                Constructo.TRASCENDENCIA,
                MitPhase.TRASCENDENCIA,
                "Experimentas un momento de conexión con algo mayor: la naturaleza, la humanidad, lo inefable. " +
                    "Tu identidad personal parece expandirse.",
                "A. Te entregas al momento sin aferrarte a definirlo; la experiencia es suficiente.",
                "B. Buscas explicaciones o etiquetas para no perder el control.",
                0.85, 0.35
            ),
            new ScaleItem(
                Constructo.TRASCENDENCIA,
                MitPhase.MANIFESTACION,
                "Tienes la oportunidad de servir a una causa que trasciende tu beneficio inmediato. " +
                    "Algo en ti reconoce que esto es parte de tu evolución.",
                "A. Actúas en sintonía con esa causa, aunque implique renuncia temporal.",
                "B. Priorizas tu seguridad antes de comprometerte.",
                0.80, 0.40
            )
        );
    }
}
