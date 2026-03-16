/**
 * Espejo Espiritual — Lógica del juego de ficción interactiva
 * Inspirado en PsychoGAT: decisiones → perfil de conciencia
 */

// Usar ruta relativa si se sirve desde el mismo origen (ej. Spring Boot)
const API_BASE = window.location.port === '8080' ? '' : 'http://localhost:8080';
const TOTAL_NODES = 14;

const CONSTRUCTO_LABELS = {
    CONCIENCIA_TRASCENDENTAL: 'Conciencia trascendental',
    PROPOSITO_TRASCENDENTAL: 'Propósito trascendental',
    SENTIDO_DE_VIDA: 'Sentido de vida',
    ENERGIA_PSIQUICA: 'Energía psíquica',
    ARQUETIPO: 'Arquetipo',
    INTEGRACION: 'Integración',
    TRASCENDENCIA: 'Trascendencia'
};

let sessionId = null;
let currentNode = null;
let nodeIndex = 0;

const screens = {
    start: document.getElementById('game-start'),
    play: document.getElementById('game-play'),
    profile: document.getElementById('game-profile'),
    error: document.getElementById('game-error')
};

const elements = {
    startForm: document.getElementById('start-form'),
    nodeConstructo: document.getElementById('node-constructo'),
    nodeNarrative: document.getElementById('node-narrative'),
    choiceA: document.getElementById('choice-a-text'),
    choiceB: document.getElementById('choice-b-text'),
    choiceBtns: document.querySelectorAll('.choice-btn'),
    loading: document.getElementById('game-loading'),
    progressText: document.getElementById('progress-text'),
    progressFill: document.getElementById('progress-fill'),
    navbar: document.querySelector('.game-navbar'),
    ictValue: document.getElementById('ict-value'),
    constructosChart: document.getElementById('constructos-chart'),
    errorMessage: document.getElementById('error-message')
};

function showScreen(id) {
    Object.values(screens).forEach(s => {
        if (s) s.classList.toggle('active', s.id === `game-${id}`);
    });
    if (id === 'play') {
        elements.navbar?.classList.add('playing');
    } else {
        elements.navbar?.classList.remove('playing');
    }
}

function showError(msg) {
    if (elements.errorMessage) elements.errorMessage.textContent = msg;
    showScreen('error');
}

function setLoading(visible) {
    elements.loading?.classList.toggle('visible', visible);
    elements.choiceBtns?.forEach(btn => btn.disabled = visible);
}

function updateProgress(index) {
    const pct = Math.min(100, (index / TOTAL_NODES) * 100);
    if (elements.progressText) elements.progressText.textContent = `Escena ${index + 1} de ${TOTAL_NODES}`;
    if (elements.progressFill) elements.progressFill.style.width = `${pct}%`;
}

function renderNode(node) {
    currentNode = node;
    const constructoLabel = CONSTRUCTO_LABELS[node.constructo] || node.constructo;

    if (elements.nodeConstructo) elements.nodeConstructo.textContent = constructoLabel;
    if (elements.nodeNarrative) elements.nodeNarrative.textContent = node.paragraph;
    if (elements.choiceA) elements.choiceA.textContent = node.instructionA;
    if (elements.choiceB) elements.choiceB.textContent = node.instructionB;

    updateProgress(nodeIndex);
}

function renderProfile(profile) {
    const ict = (profile.indiceConcienciaTrascendental ?? 0).toFixed(2);
    if (elements.ictValue) elements.ictValue.textContent = ict;

    const scores = profile.constructoScores || {};
    const entries = Object.entries(scores).sort((a, b) => b[1] - a[1]);

    if (elements.constructosChart) {
        elements.constructosChart.innerHTML = entries.map(([key, value]) => {
            const label = CONSTRUCTO_LABELS[key] || key;
            const pct = Math.round((value ?? 0) * 100);
            return `
                <div class="constructo-row">
                    <span class="constructo-name">${label}</span>
                    <div class="constructo-bar-wrap">
                        <div class="constructo-bar" style="width: ${pct}%"></div>
                    </div>
                    <span class="constructo-score">${pct}%</span>
                </div>
            `;
        }).join('');
    }
}

async function startSession(gameType, gameTopic) {
    const res = await fetch(`${API_BASE}/api/session/start`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ gameType, gameTopic })
    });
    if (!res.ok) throw new Error('No se pudo iniciar la sesión');
    return res.json();
}

async function recordDecision(selectedA) {
    const res = await fetch(`${API_BASE}/api/session/${sessionId}/decision`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            nodeId: currentNode?.id,
            selectedA
        })
    });
    if (!res.ok) throw new Error('No se pudo registrar la decisión');
    return res.json();
}

async function fetchProfile() {
    const res = await fetch(`${API_BASE}/api/session/${sessionId}/profile`);
    if (!res.ok) throw new Error('No se pudo obtener el perfil');
    return res.json();
}

// Event: start form
elements.startForm?.addEventListener('submit', async (e) => {
    e.preventDefault();
    const gameType = document.getElementById('game-type')?.value || 'mystical';
    const gameTopic = document.getElementById('game-topic')?.value || '';

    setLoading(true);
    try {
        const data = await startSession(gameType, gameTopic);
        sessionId = data.sessionId;
        nodeIndex = 0;

        if (data.node) {
            showScreen('play');
            renderNode(data.node);
        } else {
            showError('No se recibió el primer nodo.');
        }
    } catch (err) {
        console.error(err);
        showError(err.message || 'No se pudo conectar con el servidor. ¿Está el backend en http://localhost:8080?');
    } finally {
        setLoading(false);
    }
});

// Event: choice buttons
elements.choiceBtns?.forEach(btn => {
    btn.addEventListener('click', async () => {
        const choice = btn.dataset.choice;
        if (!choice || !sessionId || !currentNode) return;

        const selectedA = choice === 'a';
        setLoading(true);

        try {
            const data = await recordDecision(selectedA);

            if (data.sessionComplete && data.profile) {
                showScreen('profile');
                renderProfile(data.profile);
                return;
            }

            if (data.requiresApproval) {
                showError(data.message || 'La sesión requiere revisión.');
                return;
            }

            if (data.node) {
                nodeIndex++;
                renderNode(data.node);
            } else {
                // Fallback: fetch profile if session complete without node
                const profile = await fetchProfile();
                showScreen('profile');
                renderProfile(profile);
            }
        } catch (err) {
            console.error(err);
            showError(err.message || 'Error al procesar tu decisión.');
        } finally {
            setLoading(false);
        }
    });
});
