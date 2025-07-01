package uce.project.com.robin.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uce.project.com.Main;
import uce.project.com.baraja.Song;
import uce.project.com.cat.utils.JSON;
import uce.project.com.robin.ConfigReader;
import uce.project.com.robin.ai.GoogleAIBase;
import uce.project.com.robin.ai.TextToSong;

import java.util.List;

@RestController
public class CreateSongController {

   /* @PostMapping("/api/song")
    public String createSong(@RequestBody BodyCreateSong body) {
        GoogleAIBase ai = new GoogleAIBase(ConfigReader.get("google_ai.apikey"));
        ai.setModel("gemini-2.5-flash-lite-preview-06-17");
        ai.setMaxTokens(100000);
        String res = new TextToSong(ai).ask(body.getPrompt());
        Song song = Song.builder().userId(body.getUserId()).nombre((body.getPrompt().length() > 30 ? body.getPrompt().substring(0, 30) + "..." : body.getPrompt())).notasMusicales(res).build();
        Main.db.songDao().insertSong(song);
        return res;
    }
    @GetMapping("/api/song")
    public List<Song> getSongs()
    {
        return Main.db.songDao().getAll();
    }*/
}
