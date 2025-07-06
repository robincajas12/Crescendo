package uce.project.com.baraja;

import uce.project.com.Main;
import uce.project.com.baraja.SongDao;
import java.util.List;

/**
 * Clase Singleton para gestionar las operaciones relacionadas con las canciones.
 * Proporciona métodos para acceder y manipular datos de canciones a través de SongDao.
 */
public class SongManager {

    /**
     * Instancia única de SongManager (Singleton).
     */
    private static SongManager instancia;
    /**
     * Objeto DAO para interactuar con la base de datos de canciones.
     */
    private final SongDao songDao;

    /**
     * Constructor privado para implementar el patrón Singleton.
     * Inicializa el SongDao a partir de la base de datos principal de la aplicación.
     */
    private SongManager() {
        this.songDao = Main.db.songDao(); 
    }

    /**
     * Obtiene la instancia única de SongManager.
     * Si la instancia no existe, la crea.
     * @return La instancia de SongManager.
     */
    public static synchronized SongManager getInstancia() {
        if (instancia == null) {
            instancia = new SongManager();
        }
        return instancia;
    }

    /**
     * Obtiene todas las canciones de la base de datos.
     * @return Una lista de objetos Song.
     */
    public List<Song> getAllSongs() {
        return songDao.getAll();
    }

    /**
     * Elimina una canción de la base de datos por su ID.
     * @param id El ID de la canción a eliminar.
     * @return `true` si la canción fue eliminada exitosamente, `false` en caso contrario.
     * @throws IllegalArgumentException Si el ID proporcionado no es válido (nulo o menor o igual a 0).
     */
    public boolean eliminarSong(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID no es válido");
        }

        Song songAEliminar = Song.builder()
                .id(id)
                .build();

        return songDao.deleteSong(songAEliminar);
    }
}
