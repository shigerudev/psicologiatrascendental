# Fuentes Fundamentales del Proyecto

**IMPORTANTE:** Todo desarrollo, especificación y decisión técnica debe fundamentarse en estos tres documentos. Consultarlos antes de implementar cambios sustantivos.

---

## 1. Documentos Obligatorios

| Documento | Ubicación | Rol en el Proyecto |
|-----------|-----------|---------------------|
| **Psicología Trascendental** | `assets/Psicologia Trascendental_ Hugo Arias.pdf` | Marco teórico: 7 constructos, 5 fases MIT, arquetipos junguianos, logoterapia, modelo terapéutico |
| **PsychoGAT** | `assets/psychogat.pdf` | Arquitectura técnica: agentes LLM (Designer, Controller, Critic), gamificación de escalas, métricas psicométricas |
| **Metodología de la Investigación** | `assets/metodologia-de-la-investigacion-sexta-edicion.compressed.pdf` | Rigor metodológico: confiabilidad, validez, diseño de instrumentos, análisis de datos |

---

## 2. Aplicación por Documento

### 2.1 Psicología Trascendental (Arias)

**Usar para:**
- Definición de constructos y escalas (conciencia, propósito, sentido, energía, arquetipo, integración, trascendencia)
- Estructura de fases MIT en el flujo del juego
- Tono narrativo y simbólico de los agentes Jungiano y Existencial
- Criterios éticos: IA como catalizador, no terapeuta

**Referencias clave:** Cap. II (Principios), Cap. IV (Constructos), Cap. V BIS (Modelo terapéutico)

### 2.2 PsychoGAT (Yang et al.)

**Usar para:**
- Diseño de agentes: Game Designer, Game Controller, Critic
- Mapeo escala → nodos narrativos
- Métricas: Cronbach's α, λ6, validez convergente/discriminante
- Flujo Controller ↔ Critic con memoria narrativa

**Referencias clave:** §2 (Framework), §2.5 (Psychometric Evaluator), §3.2 (Evaluation Metrics)

### 2.3 Metodología de la Investigación (Hernández Sampieri et al.)

**Usar para:**
- **Confiabilidad y validez** de instrumentos (Cap. 9): requisitos para las escalas trascendentales
- **Operacionalización** de variables (Cap. 6): constructos → ítems/nodos
- **Diseño de investigación** (Cap. 7): exploratorio, descriptivo, correlacional
- **Análisis de datos** (Cap. 10): estadística descriptiva, inferencial, pruebas de hipótesis
- **Enfoque mixto** (cuantitativo + cualitativo): validación psicométrica + evaluación humana de contenido

**Criterios aplicables al Espejo Espiritual:**
- α ≥ 0.70 (aceptable), ≥ 0.80 (bueno), ≥ 0.90 (excelente)
- Validez convergente: correlación con gold standard
- Validez discriminante: baja correlación con constructos no relacionados

---

## 3. Síntesis para Implementación

Al desarrollar nuevas funcionalidades:

1. **Constructos y escalas** → Arias (definición) + Hernández Sampieri (operacionalización)
2. **Agentes y flujo** → PsychoGAT (arquitectura)
3. **Validación psicométrica** → PsychoGAT (métricas) + Hernández Sampieri (confiabilidad/validez)
4. **Ética y marco terapéutico** → Arias (modelo MIT, rol del terapeuta)

---

## 4. Referencias Formales (APA 7)

- Arias, H. (2025). *Psicología Trascendental: Una propuesta integradora para la evolución de la conciencia humana.*
- Hernández Sampieri, R., Fernández Collado, C., & Baptista Lucio, P. (2014). *Metodología de la investigación* (6.ª ed.). McGraw-Hill.
- Yang, Q., Wang, Z., Chen, H., et al. (2024). *PsychoGAT: A Novel Psychological Measurement Paradigm through Interactive Fiction Games with LLM Agents.* arXiv:2402.12326.
