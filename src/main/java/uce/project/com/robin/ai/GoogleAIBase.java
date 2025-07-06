package uce.project.com.robin.ai;

import com.google.genai.Client;
import uce.project.com.condor.inter.IAI;

import java.util.List;

/**
 * Implementación de la interfaz {@link IAI} para interactuar con la API de Google AI.
 * Permite configurar el modelo, la temperatura, el número máximo de tokens y obtener el cliente de la API.
 */
public class GoogleAIBase implements IAI {
    /**
     * Cliente de la API de Google AI.
     */
    private Client client;
    /**
     * Nombre del modelo de IA a utilizar.
     */
    private String model;
    /**
     * Temperatura para la generación de texto (controla la aleatoriedad).
     */
    private float temperature;
    /**
     * Número máximo de tokens a generar.
     */
    private int maxTokens;

    /**
     * Constructor de GoogleAIBase.
     * Inicializa el cliente de la API con la clave proporcionada y establece valores predeterminados para el modelo, temperatura y tokens.
     * @param apiKey La clave de API para autenticarse con Google AI.
     */
    public GoogleAIBase(String apiKey)
    {
        this.client = Client.builder().apiKey(apiKey).build();
        this.model = "gemma-3-27b-it";
        this.temperature = 1;
        this.maxTokens = 100;

    }

    /**
     * Establece el modelo de IA a utilizar.
     * @param model El nombre del modelo de IA.
     */
    @Override
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Obtiene el nombre del modelo de IA actualmente configurado.
     * @return El nombre del modelo de IA.
     */
    @Override
    public String getModel() {
        return this.model;
    }

    /**
     * Establece la temperatura para la generación de texto de la IA.
     * @param temperature La temperatura a establecer.
     */
    @Override
    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    /**
     * Obtiene la temperatura actualmente configurada para la generación de texto de la IA.
     * @return La temperatura actual.
     */
    @Override
    public float getTemperature() {
        return this.temperature;
    }

    /**
     * Establece el número máximo de tokens que la IA debe generar en su respuesta.
     * @param maxTokens El número máximo de tokens.
     */
    @Override
    public void setMaxTokens(int maxTokens) {
        this.maxTokens = maxTokens;
    }

    /**
     * Obtiene el número máximo de tokens actualmente configurado para la generación de texto de la IA.
     * @return El número máximo de tokens.
     */
    @Override
    public int getMaxTokens() {
        return this.maxTokens;
    }

    /**
     * Obtiene una lista de los modelos de IA disponibles.
     * Actualmente, este método devuelve una lista vacía.
     * @return Una lista de cadenas que representan los nombres de los modelos disponibles.
     */
    @Override
    public List<String> getModels() {
        return List.of();
    }

    /**
     * Obtiene la instancia del cliente de la API de Google AI.
     * @return El objeto Client de Google AI.
     */
    public Client getClient() {
        return client;
    }
}
