# Especificación del Sistema "Espejo Espiritual"
## Arquitectura de Software para Psicología Trascendental + PsychoGAT

**Versión:** 1.0.0  
**Autor:** Hugo Arias (Investigación) + Spec-Driven Development  
**Fecha:** 2025  
**Estado:** Especificación Maestra (Verdad Única)

---

## 1. Síntesis de la Conexión Teórico-Técnica

### 1.1 Psicología Trascendental (Arias) — Fundamentos Teóricos

La Psicología Trascendental integra:
- **Psicología cuántica** (Goswami): conciencia como base ontológica
- **Psicología transpersonal** (Grof, Maslow): estados ampliados de conciencia
- **Psicología analítica** (Jung): arquetipos, inconsciente colectivo, individuación
- **Logoterapia** (Frankl): voluntad de sentido
- **Humanismo** (Maslow, Rogers): autorrealización

**7 Constructos operativos:**
| Nivel | Constructo | Función |
|-------|------------|---------|
| I | Conciencia trascendental | Despertar interior |
| II | Propósito trascendental | Dirección del crecimiento |
| III | Sentido de vida | Coherencia existencial |
| IV | Energía psíquica | Vitalidad y equilibrio |
| V | Arquetipo | Guía simbólica |
| VI | Integración | Unidad del ser |
| VII | Trascendencia | Conexión con la totalidad |

**Modelo terapéutico MIT (5 fases):**
1. Reconocimiento → 2. Confrontación → 3. Integración → 4. Trascendencia → 5. Manifestación

### 1.2 PsychoGAT (Yang et al.) — Arquitectura de Agentes

Paradigma de medición psicológica mediante **ficción interactiva** con agentes LLM:

- **Game Designer**: crea título, outline, nodos (items de escala rediseñados)
- **Game Controller**: genera párrafos, memoria, instrucciones
- **Critic**: refina coherencia, reduce sesgo, corrige omisiones

**Flujo:** Escala → Nodos → Controller ↔ Critic ↔ Usuario → Evaluador psicométrico

### 1.3 Integración: El Espejo Espiritual

El **Espejo Espiritual** es la arquitectura que:

1. **Adapta PsychoGAT** a las escalas de constructos trascendentales (no depresión/MBTI)
2. **Introduce agentes especializados** Jungianos y Existenciales
3. **Mapea** las fases MIT a nodos narrativos del juego
4. **Captura** la expansión de conciencia mediante decisiones en juego
5. **Mantiene** auditoría Human-in-the-Loop para supervisión clínica

---

## 2. Arquitectura del Sistema

### 2.1 Capas (Clean Architecture)

```
┌─────────────────────────────────────────────────────────────────────────┐
│  INTERFAZ (React/Next.js)                                               │
│  - Experiencia de juego inmersiva                                       │
│  - Dashboard de psicólogo (supervisión)                                  │
└─────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────┐
│  CAPA DE APLICACIÓN (API REST / WebSocket)                              │
│  - Orquestador de sesiones                                               │
│  - Puertas de aprobación (Human-in-the-Loop)                             │
└─────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────┐
│  CAPA DE INFERENCIA (Agentes LLM)                                       │
│  - Agente Orquestador                                                    │
│  - Agente Jungiano (arquetipos, sombra, individuación)                   │
│  - Agente Existencial (sentido, logoterapia, propósito)                  │
│  - Game Designer (narrativa, nodos)                                      │
│  - Game Controller (párrafos, instrucciones)                             │
│  - Critic (coherencia, sesgo)                                            │
└─────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────┐
│  CAPA DE JUEGO (Motor de Ficción Interactiva)                           │
│  - Gestión de nodos, memoria narrativa                                  │
│  - Mapeo decisión → constructo/ escala                                  │
└─────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────┐
│  CAPA DE DATOS                                                          │
│  - Arquetipos, escalas trascendentales                                  │
│  - Perfiles de usuario, sesiones, decisiones                             │
│  - Auditoría de aprobaciones                                             │
└─────────────────────────────────────────────────────────────────────────┘
```

### 2.2 Orquestador Multi-Agente

**Rol del Agente Orquestador:**
- Recibe la fase MIT actual y el constructo objetivo
- Selecciona el agente especializado (Jungiano vs Existencial) según el nodo
- Dirige el flujo: Designer → Controller ↔ Critic ↔ Usuario
- Agrega puntuaciones psicométricas por constructo
- Dispara eventos de aprobación cuando se alcanzan umbrales sensibles

**Reglas de enrutamiento:**
- Constructos I–III (conciencia, propósito, sentido) → **Agente Existencial**
- Constructos IV–VII (energía, arquetipo, integración, trascendencia) → **Agente Jungiano**
- Fase MIT determina el tono narrativo (Reconocimiento: exploratorio; Confrontación: intenso; etc.)

---

## 3. Esquema de Datos para Expansión de Conciencia

### 3.1 Entidades Principales

```yaml
User:
  id: UUID
  email: string (opcional, anonimizado)
  createdAt: timestamp
  consentVersion: string

Session:
  id: UUID
  userId: UUID
  gameType: enum [fantasy, slice_of_life, sci_fi, horror]
  gameTopic: string
  mitPhase: enum [RECONOCIMIENTO, CONFRONTACION, INTEGRACION, TRASCENDENCIA, MANIFESTACION]
  status: enum [ACTIVE, PAUSED, COMPLETED, REQUIRES_APPROVAL]
  createdAt: timestamp
  completedAt: timestamp (nullable)

GameNode:
  id: UUID
  sessionId: UUID
  constructoId: enum [CONCIENCIA, PROPOSITO, SENTIDO, ENERGIA, ARQUETIPO, INTEGRACION, TRASCENDENCIA]
  phaseIndex: int
  narrativeParagraph: text
  instructionA: text
  instructionB: text
  scoreA: float  # 0.0 = ausencia, 1.0 = presencia del constructo
  scoreB: float
  memorySnapshot: text

UserDecision:
  id: UUID
  nodeId: UUID
  selectedInstruction: enum [A, B]
  rawScore: float
  timestamp: timestamp
  responseTimeMs: int (opcional)

ConsciousnessProfile:
  id: UUID
  userId: UUID
  sessionId: UUID
  constructoScores: map<Constructo, float>

Archetype:
  id: UUID
  name: string
  symbol: string
  description: text
  associatedConstructos: list<Constructo>

ApprovalGate:
  id: UUID
  sessionId: UUID
  triggerReason: enum [THRESHOLD_ALERT, CRISIS_KEYWORD, MANUAL_REVIEW]
  status: enum [PENDING, APPROVED, REJECTED]
  psychologistId: UUID (nullable)
  reviewedAt: timestamp (nullable)
  notes: text
```

### 3.2 Métricas de Expansión de Conciencia

- **Índice de Conciencia Trascendental (ICT):** Promedio ponderado de scores por constructo
- **Trajectoria MIT:** Secuencia de fases completadas y tiempo por fase
- **Coherencia Arquetípica:** Correlación entre decisiones y patrones arquetípicos dominantes

### 3.3 Criterios Metodológicos (Hernández Sampieri + PsychoGAT)

Según *Metodología de la investigación* (Cap. 9) y PsychoGAT (§3.2):

| Requisito | Criterio | Fuente |
|-----------|----------|--------|
| Confiabilidad | α ≥ 0.70 (aceptable), ≥ 0.80 (bueno), ≥ 0.90 (excelente) | Hernández Sampieri, PsychoGAT |
| Validez convergente | Correlación con gold standard (escala tradicional) | Campbell & Fiske |
| Validez discriminante | Baja correlación con constructos no relacionados | PsychoGAT |
| Operacionalización | Constructo → ítem/nodo con scores binarios o Likert | Hernández Sampieri Cap. 6 |

---

## 4. Human-in-the-Loop (Auditoría)

### 4.1 Puertas de Aprobación

| Trigger | Condición | Acción |
|---------|-----------|--------|
| THRESHOLD_ALERT | ICT < 0.3 o > 0.9 en cualquier constructo | Pausar sesión, notificar psicólogo |
| CRISIS_KEYWORD | Detección de términos de riesgo (suicidio, autolesión) | Bloquear inmediato, redirigir a soporte |
| MANUAL_REVIEW | Cada N nodos (configurable) | Cola de revisión para psicólogo |
| SESSION_COMPLETE | Sesión finalizada | Resumen disponible para revisión |

### 4.2 API de Supervisión

- `GET /api/supervisor/pending` — Lista de puertas pendientes
- `POST /api/supervisor/approve/{gateId}` — Aprobar continuidad
- `POST /api/supervisor/reject/{gateId}` — Rechazar, con notas
- `GET /api/supervisor/session/{sessionId}/summary` — Resumen de sesión

---

## 5. Stack Tecnológico

| Capa | Tecnología |
|------|------------|
| Backend | Java 17+, Spring Boot 3.x |
| Frontend | React 18+, Next.js 14 |
| Base de datos | PostgreSQL (datos, auditoría) |
| LLM | OpenAI API / Azure OpenAI (configurable) |
| Comunicación | REST + WebSocket (SSE para streaming) |

---

## 6. Principios de Diseño (Manifiesto Shigeru)

- **Estabilidad forense:** Java/Spring para trazabilidad y auditoría
- **UX Psicológica:** Interfaz intuitiva, no invasiva, transformadora
- **Clean Architecture:** Separación de responsabilidades, testabilidad
- **Ética:** La IA es catalizador, no terapeuta; siempre redirigir a profesional en riesgo

---

## 7. Referencias

- Arias, H. (2025). *Psicología Trascendental: Una propuesta integradora para la evolución de la conciencia humana.*
- Yang, Q., Wang, Z., et al. (2024). *PsychoGAT: A Novel Psychological Measurement Paradigm through Interactive Fiction Games with LLM Agents.* arXiv:2402.12326
- Hernández Sampieri, R., Fernández Collado, C., & Baptista Lucio, P. (2014). *Metodología de la investigación* (6.ª ed.). McGraw-Hill.

---

## 8. Fuentes Fundamentales (Documentos del Proyecto)

**Consultar siempre** estos tres documentos al desarrollar el sistema:

| Documento | Ubicación | Aplicación |
|-----------|-----------|------------|
| Psicología Trascendental | `assets/Psicologia Trascendental_ Hugo Arias.pdf` | Constructos, MIT, ética |
| PsychoGAT | `assets/psychogat.pdf` | Agentes, métricas psicométricas |
| Metodología de la Investigación | `assets/metodologia-de-la-investigacion-sexta-edicion.compressed.pdf` | Confiabilidad, validez, operacionalización |

Ver **`DOCS/FUNDAMENTAL_SOURCES.md`** para la guía detallada de aplicación.
