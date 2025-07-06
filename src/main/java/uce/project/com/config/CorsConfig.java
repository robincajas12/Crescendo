package uce.project.com.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración de CORS (Cross-Origin Resource Sharing) para la aplicación Spring Boot.
 * Permite que recursos de diferentes orígenes accedan a la API.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

  /**
   * Configura las reglas de mapeo de CORS.
   * Permite todas las solicitudes de cualquier origen, método y cabecera.
   * @param registry El registro de CORS para añadir mapeos.
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("*")
            .allowedHeaders("*");
  }
}
