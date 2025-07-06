package uce.project.com.robin.ai;

import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.Schema;
import com.google.genai.types.Type;
import uce.project.com.baraja.IAsk;
import uce.project.com.condor.inter.IAI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementación de la interfaz {@link IAsk} para convertir texto en una secuencia de notas musicales
 * utilizando la API de Google AI. Genera una estructura JSON que representa la canción.
 */
public class TextToSong implements IAsk<String> {
    /**
     * Instancia de {@link GoogleAIBase} para interactuar con la API de Google AI.
     */
    GoogleAIBase aiBase;

    /**
     * Constructor de TextToSong.
     * @param aiBase La instancia de GoogleAIBase a utilizar para la generación de contenido.
     */
    public TextToSong(GoogleAIBase aiBase)
    {
        this.aiBase = aiBase;
    }

    /**
     * Convierte un prompt de texto en una secuencia de notas musicales en formato JSON.
     * Configura el modelo de IA para generar un array de objetos, donde cada objeto representa una nota
     * con sus propiedades (notas y duración).
     * @param promt El texto de entrada (prompt) que describe la canción a generar.
     * @return Una cadena JSON que representa la secuencia de notas musicales.
     */
    @Override
    public String ask(String promt) {
        // Define el esquema para las propiedades de cada nota musical (notes y duration)
        Map<String, Schema> map = new HashMap();
        map.put("notes", Schema
                .builder()
                .type(Type.Known.ARRAY)
                        .items(Schema.builder().type(Type.Known.STRING).description("notes like Chord or single notes to play in octaves format").build())
                .build());
        map.put("duration", Schema.builder().type(Type.Known.NUMBER).description("Time the note should play in seconds").build());

        // Configura la generación de contenido para la API de Google AI
        GenerateContentConfig config = GenerateContentConfig
                .builder()
                .temperature(aiBase.getTemperature())
                .maxOutputTokens(aiBase.getMaxTokens())
                .responseMimeType("application/json") // Especifica que la respuesta debe ser JSON
                .responseSchema(Schema.builder()
                        .type(Type.Known.ARRAY) // El tipo de respuesta esperado es un array
                        .minItems(10L) // Mínimo de 10 elementos en el array
                        .items(Schema
                                .builder()
                                .type(Type.Known.OBJECT) // Cada elemento del array es un objeto
                                .properties(map) // Define las propiedades de cada objeto (notes, duration)
                                .required(List.of("notes", "duration")) // Ambas propiedades son requeridas
                                .build())
                        .build())
                .build();

        // Llama a la API de Google AI para generar el contenido y devuelve el texto resultante
        return aiBase.getClient().models.generateContent(aiBase.getModel(),promt, config).text();
    }

    /**
     * Este método no está implementado para la generación de texto a canción y devuelve una cadena vacía.
     * @param promt El texto de entrada (prompt).
     * @param instructions Instrucciones adicionales.
     * @return Una cadena vacía.
     */
    @Override
    public String ask(String promt, String instructions) {
        return "";
    }
}
