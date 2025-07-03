package uce.project.com.baraja;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uce.project.com.Main;
import uce.project.com.mateo.PromptEntity;
import uce.project.com.robin.ConfigReader;
import uce.project.com.robin.ai.GoogleAIBase;
import uce.project.com.robin.ai.TextToSong;
import uce.project.com.robin.controllers.BodyCreateSong;

import java.util.List;

@RestController
@RequestMapping("/api/song")
public class SongController {

    @GetMapping
    public ResponseEntity<List<Song>> getAllSongs() {
        List<Song> canciones = SongManager.getInstancia().getAllSongs();
        return ResponseEntity.ok(canciones);
    }

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
