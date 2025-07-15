package uce.project.com.server.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import uce.project.com.database.entities.PromptEntity;
import uce.project.com.server.controllers.body.PromptRequest;
import uce.project.com.server.controllers.managers.PromptManager;

import java.util.List;

/**
 * Controlador REST para la gestión de prompts.
 * Proporciona endpoints para obtener, guardar y eliminar prompts.
 */
@RestController
@RequestMapping("/api/prompt")
public class PromptController {

    /**
     * Obtiene todos los prompts disponibles.
     * @return Una respuesta HTTP que contiene una lista de objetos PromptEntity.
     */
    @GetMapping
    public ResponseEntity<List<PromptEntity>> getAllPrompts() {
        List<PromptEntity> prompts = PromptManager.getInstancia().getAllPromts();
        return ResponseEntity.ok(prompts);
    }

    /**
     * Guarda un nuevo prompt en la base de datos.
     * @param request Objeto PromptRequest que contiene el texto del prompt a guardar.
     * @return Una respuesta HTTP que contiene el PromptEntity guardado y un estado 201 (Created).
     */
    @PostMapping
    public ResponseEntity<PromptEntity> savePrompt(@RequestBody PromptRequest request) {
        PromptEntity savedPrompt = PromptManager.getInstancia().guardarPromt(request.getPrompt());
        return ResponseEntity.status(201).body(savedPrompt);
    }

    /**
     * Elimina un prompt por su ID.
     * @param id El ID del prompt a eliminar.
     * @return Una respuesta HTTP que indica el éxito o fracaso de la operación.
     */
    @DeleteMapping
    public ResponseEntity<String> deletePrompt(@RequestParam Integer id) {
        boolean success = PromptManager.getInstancia().deletePrompt(id);
        if (success) {
            return ResponseEntity.ok("El Prompt con ID " + id + " eliminado correctamente");
        } else {
            return ResponseEntity.status(404).body("No se ha encontrado el prompt con la ID " + id);
        }
    }
}