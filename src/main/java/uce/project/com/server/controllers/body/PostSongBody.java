package uce.project.com.server.controllers.body;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase que representa el cuerpo de la solicitud para crear una canción.
 * Utiliza Lombok para generar automáticamente los métodos getter y setter.
 */
@Getter
@Setter
public class PostSongBody {
    /**
     * El prompt o texto base para la creación de la canción.
     */
    private String prompt;
    /**
     * El ID del usuario que solicita la creación de la canción.
     */
    private Integer userId;
}
