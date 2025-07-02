package uce.project.com.baraja;

import uce.project.com.Main;
import uce.project.com.baraja.SongDao;
import java.util.List;


public class SongManager {

    private static SongManager instancia;
    private final SongDao songDao;

    private SongManager() {
        this.songDao = Main.db.songDao(); 
    }

    public static synchronized SongManager getInstancia() {
        if (instancia == null) {
            instancia = new SongManager();
        }
        return instancia;
    }

    public List<Song> getAllSongs() {
        return songDao.getAll();
    }

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
