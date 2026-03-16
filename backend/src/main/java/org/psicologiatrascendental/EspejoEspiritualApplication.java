package org.psicologiatrascendental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Punto de entrada del backend Espejo Espiritual.
 * Arquitectura Multi-Agente para Psicología Trascendental (PsychoGAT).
 *
 * @see DOCS/TRANSCENDENTAL_SPECS.md
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class EspejoEspiritualApplication {

    public static void main(String[] args) {
        SpringApplication.run(EspejoEspiritualApplication.class, args);
    }
}
