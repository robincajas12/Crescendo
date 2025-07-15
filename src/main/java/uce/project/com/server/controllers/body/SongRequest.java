package uce.project.com.server.controllers.body;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase que representa una solicitud relacionada con una canción, incluyendo el ID de usuario y el ID de la canción.
 * Utiliza Lombok para generar automáticamente los métodos getter y setter.
 */
@Getter
@Setter
public class SongRequest {
    /**
     * El ID del usuario que realiza la solicitud.
     */
    private Integer userId;
    /**
     * El ID de la canción a la que se refiere la solicitud.
     */
    private Integer songId;
}
