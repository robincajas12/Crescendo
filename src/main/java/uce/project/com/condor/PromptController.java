package uce.project.com.condor;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import uce.project.com.mateo.PromptEntity;

import java.util.List;

@RestController
@RequestMapping("/api/prompt")
public class PromptController {

    @GetMapping
    public ResponseEntity<List<PromptEntity>> getAllPrompts() {
        List<PromptEntity> prompts = PromptManager.getInstancia().getAllPromts();
        return ResponseEntity.ok(prompts);
    }

    @PostMapping
    public ResponseEntity<PromptEntity> savePrompt(@RequestBody PromptRequest request) {
        PromptEntity savedPrompt = PromptManager.getInstancia().guardarPromt(request.getPrompt());
        return ResponseEntity.status(201).body(savedPrompt);
    }

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