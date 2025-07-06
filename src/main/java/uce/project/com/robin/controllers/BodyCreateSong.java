package uce.project.com.robin.controllers;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase que representa el cuerpo de la solicitud para crear una canción.
 * Contiene el prompt de texto para la generación de la canción y el ID del usuario solicitante.
 * Utiliza Lombok para generar automáticamente los métodos getter y setter.
 */
@Getter
@Setter
public class BodyCreateSong {
    /**
     * El prompt de texto que se utilizará para generar la canción.
     */
    private String prompt;
    /**
     * El ID del usuario que solicita la creación de la canción.
     */
    private Integer userId;
}
