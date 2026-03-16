package org.psicologiatrascendental.application.impl;

import org.psicologiatrascendental.application.ConsciousnessProfile;
import org.psicologiatrascendental.application.SessionOrchestrator;
import org.psicologiatrascendental.domain.Constructo;
import org.psicologiatrascendental.domain.GameNode;
import org.psicologiatrascendental.domain.ScaleItem;
import org.psicologiatrascendental.infrastructure.scale.TranscendentalScale;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementación en memoria del orquestador de sesiones.
 * Para producción: integrar con GameControllerAgent (LLM) y persistencia.
 */
@Service
public class InMemorySessionOrchestrator implements SessionOrchestrator {

    private final Map<UUID, SessionState> sessions = new ConcurrentHashMap<>();
    private final TranscendentalScale scale;

    public InMemorySessionOrchestrator(TranscendentalScale scale) {
        this.scale = scale;
    }

    @Override
    public UUID startSession(String gameType, String gameTopic) {
        UUID id = UUID.randomUUID();
        sessions.put(id, new SessionState(id, gameType, gameTopic));
        return id;
    }

    @Override
    public GameNode getNextNode(UUID sessionId) {
        SessionState state = sessions.get(sessionId);
        if (state == null) return null;
        return buildNode(state);
    }

    @Override
    public GameNode recordDecision(UUID sessionId, UUID nodeId, boolean selectedA) {
        SessionState state = sessions.get(sessionId);
        if (state == null) return null;

        ScaleItem item = scale.get(state.nodeIndex);
        double score = selectedA ? item.scoreA() : item.scoreB();
        state.scores.merge(item.constructo(), score, (a, b) -> (a + b) / 2);
        state.nodeIndex++;
        state.memoryBuilder.append("Decisión ").append(state.nodeIndex)
            .append(": ").append(selectedA ? "A" : "B").append(". ");

        return buildNode(state);
    }

    @Override
    public ConsciousnessProfile getConsciousnessProfile(UUID sessionId) {
        SessionState state = sessions.get(sessionId);
        if (state == null) return new ConsciousnessProfile(Map.of(), 0.0, 0);

        Map<Constructo, Double> scores = new EnumMap<>(state.scores);
        double ict = ConsciousnessProfile.computeICT(scores);
        return new ConsciousnessProfile(scores, ict, state.nodeIndex);
    }

    private GameNode buildNode(SessionState state) {
        if (state.nodeIndex >= scale.size()) {
            return null; // Sesión completada
        }

        ScaleItem item = scale.get(state.nodeIndex);
        state.currentConstructo = item.constructo();

        return new GameNode(
            UUID.randomUUID(),
            state.sessionId,
            item.constructo(),
            state.nodeIndex,
            item.narrativeParagraph(),
            item.instructionA(),
            item.instructionB(),
            item.scoreA(),
            item.scoreB(),
            state.memoryBuilder.toString()
        );
    }

    private static class SessionState {
        final UUID sessionId;
        final String gameType;
        final String gameTopic;
        final Map<Constructo, Double> scores = new EnumMap<>(Constructo.class);
        final StringBuilder memoryBuilder = new StringBuilder();
        int nodeIndex = 0;
        Constructo currentConstructo;

        SessionState(UUID sessionId, String gameType, String gameTopic) {
            this.sessionId = sessionId;
            this.gameType = gameType;
            this.gameTopic = gameTopic;
        }
    }
}
