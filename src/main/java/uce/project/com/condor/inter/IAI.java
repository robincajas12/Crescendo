package uce.project.com.condor.inter;

import java.util.List;

/**
 * Interfaz para definir el comportamiento básico de una Inteligencia Artificial (IA).
 * Proporciona métodos para configurar el modelo, la temperatura, el número máximo de tokens
 * y para obtener la lista de modelos disponibles.
 */
public interface IAI {
    /**
     * Establece el modelo de IA a utilizar.
     * @param model El nombre del modelo de IA.
     */
    void setModel(String model);

    /**
     * Obtiene el nombre del modelo de IA actualmente configurado.
     * @return El nombre del modelo de IA.
     */
    String getModel();

    /**
     * Establece la temperatura para la generación de texto de la IA.
     * Un valor más alto (ej. 0.8) hace la salida más aleatoria, mientras que un valor más bajo (ej. 0.2) la hace más determinista.
     * @param temperature La temperatura a establecer.
     */
    void setTemperature(float temperature);

    /**
     * Obtiene la temperatura actualmente configurada para la generación de texto de la IA.
     * @return La temperatura actual.
     */
    float getTemperature();

    /**
     * Establece el número máximo de tokens que la IA debe generar en su respuesta.
     * @param maxTokens El número máximo de tokens.
     */
    void setMaxTokens(int maxTokens);

    /**
     * Obtiene el número máximo de tokens actualmente configurado para la generación de texto de la IA.
     * @return El número máximo de tokens.
     */
    int getMaxTokens();

    /**
     * Obtiene una lista de los modelos de IA disponibles.
     * @return Una lista de cadenas que representan los nombres de los modelos disponibles.
     */
    List<String> getModels();
}