# Psicología Trascendental — Espejo Espiritual

Prototipo funcional que integra la investigación de Hugo Arias (Psicología Trascendental) con el paradigma PsychoGAT (Interactive Fiction Games for Psychological Measurement).

## Documentación

- **[TRANSCENDENTAL_SPECS.md](DOCS/TRANSCENDENTAL_SPECS.md)** — Especificación maestra del sistema
- **[FUNDAMENTAL_SOURCES.md](DOCS/FUNDAMENTAL_SOURCES.md)** — Fuentes obligatorias (Arias, PsychoGAT, Hernández Sampieri)
- **[FOLDER_STRUCTURE.md](DOCS/FOLDER_STRUCTURE.md)** — Estructura de carpetas

## Fuentes de Investigación (consultar siempre)

| Documento | Ubicación |
|-----------|-----------|
| Psicología Trascendental (Arias) | `assets/Psicologia Trascendental_ Hugo Arias.pdf` |
| PsychoGAT | `assets/psychogat.pdf` |
| Metodología de la Investigación (6.ª ed.) | `assets/metodologia-de-la-investigacion-sexta-edicion.compressed.pdf` |

## Estructura del Proyecto

```
psicologiatrascendental/
├── DOCS/                    # Especificaciones (Spec-Driven Development)
├── backend/                 # Java Spring Boot (Clean Architecture)
│   └── src/main/java/.../
│       ├── domain/          # Constructo, MitPhase, GameNode, ApprovalGate
│       ├── agents/          # Orchestrator, Jungian, Existential
│       ├── application/     # SessionOrchestrator, ConsciousnessProfile
│       ├── infrastructure/  # ApprovalGateService (Human-in-the-Loop)
│       └── web/             # REST API
├── frontend/                # Landing + futuro Next.js
└── assets/                 # PDFs de investigación
```

## Capas

| Capa | Componentes |
|------|-------------|
| **Inferencia** | OrchestratorAgent, JungianAgent, ExistentialAgent |
| **Juego** | SessionOrchestrator, GameNode, PsychometricEvaluator |
| **Datos** | ConsciousnessProfile, ApprovalGate (esquema en specs) |
| **Human-in-the-Loop** | SupervisorController, ApprovalGateService |

## Ejecución

### Backend (Spring Boot)

```bash
cd backend
./mvnw spring-boot:run
```

API disponible en `http://localhost:8080`:

- `POST /api/session/start` — Iniciar sesión
- `POST /api/session/{id}/decision` — Registrar decisión
- `GET /api/session/{id}/profile` — Perfil de conciencia
- `GET /api/supervisor/pending` — Puertas pendientes
- `POST /api/supervisor/approve/{gateId}` — Aprobar
- `POST /api/supervisor/reject/{gateId}` — Rechazar

### Frontend

**Opción A — Servido por el backend (recomendado para MVP):**

```bash
cd backend
./mvnw spring-boot:run
```

Luego abrir:
- `http://localhost:8080/` — Landing
- `http://localhost:8080/game.html` — Juego de ficción interactiva

**Opción B — Servidor estático independiente:**

```bash
cd frontend
npx serve .
```

Abrir `http://localhost:5000/game.html` (el frontend llamará a `http://localhost:8080` para la API).

## MVP: Juego de Ficción Interactiva

El MVP incluye:

- **Escala trascendental** — 14 ítems narrativos basados en los 7 constructos (Arias)
- **Flujo PsychoGAT** — Decisiones en juego → perfil de conciencia
- **Índice ICT** — Promedio de scores por constructo
- **UI inmersiva** — Narrativa, opciones A/B, visualización del perfil

## Próximos Pasos

1. Integrar LLM (OpenAI/Azure) para GameDesigner, GameController, Critic
2. Persistencia con PostgreSQL + JPA
3. Migrar frontend a Next.js con experiencia de juego inmersiva
4. Validación psicométrica de la escala (confiabilidad, validez)

## Referencias

- Arias, H. (2025). *Psicología Trascendental*
- Yang et al. (2024). *PsychoGAT*. arXiv:2402.12326
