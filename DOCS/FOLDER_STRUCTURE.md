# Estructura de Carpetas — Psicología Trascendental

## Vista General

```
psicologiatrascendental/
├── DOCS/                          # Documentación y especificaciones
│   ├── TRANSCENDENTAL_SPECS.md     # Verdad única del sistema
│   └── FOLDER_STRUCTURE.md         # Este archivo
│
├── backend/                       # Java Spring Boot (Capa de Inferencia + Juego + Datos)
│   └── src/main/java/.../
│       ├── domain/                 # Entidades de dominio
│       ├── application/            # Casos de uso, orquestación
│       ├── infrastructure/         # Persistencia, LLM, Web
│       └── agents/                 # Agentes especializados
│
├── frontend/                      # React/Next.js (actual: HTML/CSS/JS estático)
│   ├── app/                       # Next.js App Router (futuro)
│   ├── components/
│   └── ...
│
└── assets/                        # PDFs de investigación
```

## Capa de Inferencia (backend/agents)

| Carpeta/Archivo | Responsabilidad |
|-----------------|-----------------|
| `OrchestratorAgent` | Dirige flujo, selecciona agente especializado |
| `JungianAgent` | Interpretación arquetípica, sombra, individuación |
| `ExistentialAgent` | Sentido, logoterapia, propósito |
| `GameDesignerAgent` | Crea título, outline, nodos narrativos |
| `GameControllerAgent` | Genera párrafos, instrucciones, memoria |
| `CriticAgent` | Refina coherencia, reduce sesgo |

## Capa de Juego (backend/application)

| Componente | Responsabilidad |
|------------|-----------------|
| `GameEngine` | Motor de ficción interactiva, gestión de nodos |
| `SessionOrchestrator` | Coordina sesión, fases MIT, decisiones |
| `PsychometricEvaluator` | Agrega scores por constructo |

## Capa de Datos (backend/infrastructure)

| Componente | Responsabilidad |
|------------|-----------------|
| `ArchetypeRepository` | Almacenamiento de arquetipos |
| `SessionRepository` | Sesiones, nodos, decisiones |
| `ApprovalGateRepository` | Puertas de aprobación Human-in-the-Loop |
