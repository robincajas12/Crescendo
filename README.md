
 Condor
===========================

Tareas:
- Crear un RestController
- Usar la ruta base: @RequestMapping("/api/prompt")

Endpoints:
- Obtener todos los prompts
- Guardar un prompt
- Eliminar un prompt por ID
- Aplicar patrones de diseño: aaaaaaaaaaaaaaaa

---------------------------

Baraja
===========================

Tareas:
- Crear un RestController
- Usar la ruta base: @RequestMapping("/api/song")

Endpoints:
- Obtener todos los songs
- Guardar un song
- Eliminar un song por ID


- Aplicar patrones de diseño: aaaaaaaaaaaaaaaa




🧑‍💻 Tutorial: Cómo crear controladores en Spring Boot con @RestController

📌 ¿Qué es un @RestController?

Un @RestController en Spring Boot permite manejar solicitudes HTTP (GET, POST, etc.) y devolver datos directamente como texto o JSON. Es ideal para APIs RESTful.

Ejemplo:
------------------------------------
@RestController
public class MiControlador { }
------------------------------------

🛠️ Estructura básica de un controlador

------------------------------------
@RestController
@RequestMapping("/api/example") // Ruta base opcional
public class ExampleController {

    // Maneja GET en /api/example/hello
    @GetMapping("/hello")
    public String sayHello() {
        return "Hola mundo";
    }

    // Maneja POST en /api/example/data
    @PostMapping("/data")
    public String receiveData(@RequestBody MyData body) {
        return "Recibido: " + body.getName();
    }
}
------------------------------------

✅ Anotaciones clave:
- @RestController: indica que es un controlador REST.
- @RequestMapping: define una ruta base para el controlador.
- @GetMapping, @PostMapping: indican qué tipo de petición HTTP maneja.
- @RequestBody: permite recibir un objeto JSON como entrada.

📦 Clase para el cuerpo del POST

------------------------------------
public class MyData {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
------------------------------------

🧪 Ejemplo completo

------------------------------------
@RestController
@RequestMapping("/api/music")
public class MusicController {

    @GetMapping("/ping")
    public String ping() {
    	aqui va logica
        return "pong";
    }

    @PostMapping("/create")
    public String createSong(@RequestBody SongRequest request) {
    	// aqui va la logica 
        return "Creating song with prompt: " + request.getPrompt();
    }
}
------------------------------------

Clase del cuerpo:
------------------------------------
public class SongRequest {
    private String prompt;

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}


