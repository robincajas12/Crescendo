package uce.project.com.robin.ai;

import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.Schema;
import com.google.genai.types.Type;
import uce.project.com.baraja.IAsk;
import uce.project.com.condor.inter.IAI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextToSong implements IAsk<String> {
    GoogleAIBase aiBase;
    public TextToSong(GoogleAIBase aiBase)
    {
        this.aiBase = aiBase;
    }
    @Override
    public String ask(String promt) {
        Map<String, Schema> map = new HashMap();
        map.put("notes", Schema
                .builder()
                .type(Type.Known.ARRAY)
                        .items(Schema.builder().type(Type.Known.STRING).description("notes like Chord or single notes to play in octaves format").build())
                .build());
        map.put("duration", Schema.builder().type(Type.Known.NUMBER).description("Time the note should play in seconds").build());
        GenerateContentConfig config = GenerateContentConfig
                .builder()
                .temperature(aiBase.getTemperature())
                .maxOutputTokens(aiBase.getMaxTokens())
                .responseMimeType("application/json")
                .responseSchema(Schema.builder()
                        .type(Type.Known.ARRAY)
                        .minItems(10L)
                        .items(Schema
                                .builder()
                                .type(Type.Known.OBJECT)
                                .properties(map)
                                .required(List.of("notes", "duration"))
                                .build())
                        .build())
                .build();
        return aiBase.getClient().models.generateContent(aiBase.getModel(),promt, config).text();
    }

    @Override
    public String ask(String promt, String instructions) {
        return "";
    }
}
