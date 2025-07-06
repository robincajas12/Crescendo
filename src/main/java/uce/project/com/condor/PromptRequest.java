package uce.project.com.condor;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase que representa una solicitud para un prompt.
 * Utiliza Lombok para generar automáticamente los métodos getter y setter.
 */
@Getter
@Setter
public class PromptRequest {
    /**
     * El texto del prompt.
     */
    private String prompt;
}