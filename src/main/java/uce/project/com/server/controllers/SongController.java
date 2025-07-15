package uce.project.com.server.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uce.project.com.Main;
import uce.project.com.database.entities.Song;
import uce.project.com.server.controllers.body.PostSongBody;
import uce.project.com.server.controllers.managers.SongManager;
import uce.project.com.utils.ConfigReader;
import uce.project.com.ai.GoogleAIBase;
import uce.project.com.ai.TextToSong;

import java.util.List;

/**
 * Controlador REST para la gestión de canciones.
 * Proporciona endpoints para obtener, crear y eliminar canciones.
 */
@RestController
@RequestMapping("/api/song")
public class SongController {

    /**
     * Obtiene todas las canciones disponibles.
     * @return Una respuesta HTTP que contiene una lista de objetos Song.
     */
    @GetMapping
    public ResponseEntity<List<Song>> getAllSongs() {
        List<Song> canciones = SongManager.getInstancia().getAllSongs();
        return ResponseEntity.ok(canciones);
    }

    /**
     * Crea una nueva canción basada en un prompt y un ID de usuario.
     * Utiliza la IA de Google para generar el contenido musical.
     * @param body Objeto PostSongBody que contiene el prompt y el ID de usuario.
     * @return Una respuesta HTTP que contiene el resultado de la generación de la canción.
     */
    @PostMapping
    public ResponseEntity<String> createSong(@RequestBody PostSongBody body) {
        GoogleAIBase ai = new GoogleAIBase(ConfigReader.get("google_ai.apikey"));
        ai.setModel("gemini-2.5-flash");
        ai.setMaxTokens(100000);
        String res = new TextToSong(ai).ask(body.getPrompt());
        Song song = Song.builder().userId(body.getUserId()).nombre((body.getPrompt().length() > 30 ? body.getPrompt().substring(0, 30) + "..." : body.getPrompt())).notasMusicales(res).build();
        Main.db.songDao().insertSong(song);
        return ResponseEntity.ok(res);
    }

    /**
     * Elimina una canción por su ID.
     * @param id El ID de la canción a eliminar.
     * @return Una respuesta HTTP que indica el éxito o fracaso de la operación.
     */
    @DeleteMapping
    public ResponseEntity<String> deleteSong(@RequestParam Integer id) {
        boolean success = SongManager.getInstancia().eliminarSong(id);
        if (success) {
            return ResponseEntity.ok("La canción con ID " + id + " fue eliminada correctamente");
        } else {
            return ResponseEntity.status(404).body("No se encontró la canción con ID " + id);
        }
    }
}
