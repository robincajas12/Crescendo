package uce.project.com.server.controllers;

import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para la creación y gestión de canciones.
 * Nota: Los métodos de este controlador están actualmente comentados.
 */
@RestController
public class CreateSongController {

   /*
    * @PostMapping("/api/song")
    * public String createSong(@RequestBody BodyCreateSong body) {
    *     GoogleAIBase ai = new GoogleAIBase(ConfigReader.get("google_ai.apikey"));
    *     ai.setModel("gemini-2.5-flash-lite-preview-06-17");
    *     ai.setMaxTokens(100000);
    *     String res = new TextToSong(ai).ask(body.getPrompt());
    *     Song song = Song.builder().userId(body.getUserId()).nombre((body.getPrompt().length() > 30 ? body.getPrompt().substring(0, 30) + "..." : body.getPrompt())).notasMusicales(res).build();
    *     Main.db.songDao().insertSong(song);
    *     return res;
    * }
    */

   /*
    * @GetMapping("/api/song")
    * public List<Song> getSongs()
    * {
    *     return Main.db.songDao().getAll();
    * }
    */
}
