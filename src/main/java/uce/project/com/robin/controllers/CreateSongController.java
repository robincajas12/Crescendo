package uce.project.com.robin.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uce.project.com.Main;
import uce.project.com.robin.ConfigReader;
import uce.project.com.robin.ai.GoogleAIBase;
import uce.project.com.robin.ai.TextToSong;

@RestController
public class CreateSongController {

    @PostMapping("/api/create/song")
    public String createSong(@RequestBody BodyCreateSong body) {
        GoogleAIBase ai = new GoogleAIBase(ConfigReader.get("google_ai.apikey"));
        ai.setModel("gemini-2.5-flash-lite-preview-06-17");
        ai.setMaxTokens(100000);
        String res = new TextToSong(ai).ask(body.getPrompt());
        System.out.println(res);
        return res;
    }
    @GetMapping("/api/create/song")
    public String createSong2()
    {

        return "XD";
    }
}
