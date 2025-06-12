package uce.project.com.baraja;
import uce.project.com.cat.anotations.*;
import java.util.List;

@Dao
public interface SongDao {
    @Query("SELECT * FROM Song")
    List<Song> getAll();

    @Query("SELECT * FROM Song WHERE id = :id")
    List<Song> getSongByID(@P("id") Integer id);

    @Query("SELECT * FROM Song WHERE genero = :genero")
    List<Song> getSongsByGender(@P("genero") String genero);

    @Query("SELECT * FROM Song WHERE nombre LIKE :nombre")
    List<Song> filterByName(@P("nombre") String nombre);

    @Insert
    boolean insertSong(Song song);

    @Update
    boolean updateSong(Song song);

    @Delete
    boolean deleteSong(Song song);
}