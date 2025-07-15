package uce.project.com.server.controllers.managers;

import uce.project.com.Main;
import uce.project.com.database.daos.PromptDao;
import uce.project.com.database.entities.PromptEntity;
import java.util.List;

/**
 * Clase Singleton para gestionar las operaciones relacionadas con los prompts.
 * Proporciona métodos para acceder y manipular datos de prompts a través de PromptDao.
 */
public class PromptManager {

    /**
     * Instancia única de PromptManager (Singleton).
     */
    private static PromptManager instancia;
    /**
     * Objeto DAO para interactuar con la base de datos de prompts.
     */
    private final PromptDao promtDao;

    /**
     * Constructor privado para implementar el patrón Singleton.
     * Inicializa el PromptDao a partir de la base de datos principal de la aplicación.
     */
    private PromptManager() {
        this.promtDao = Main.db.promtDao();
    }

    /**
     * Obtiene la instancia única de PromptManager.
     * Si la instancia no existe, la crea.
     * @return La instancia de PromptManager.
     */
    public static synchronized PromptManager getInstancia() {
        if (instancia == null) {
            instancia = new PromptManager();
        }
        return instancia;
    }

    /**
     * Obtiene todos los prompts de la base de datos.
     * @return Una lista de objetos PromptEntity.
     */
    public List<PromptEntity> getAllPromts() {
        return promtDao.getAll();
    }

    /**
     * Guarda un nuevo prompt en la base de datos.
     * @param promt El texto del prompt a guardar.
     * @return El objeto PromptEntity guardado.
     * @throws IllegalArgumentException Si el prompt es nulo o está vacío.
     */
    public PromptEntity guardarPromt(String promt) {
        if (promt == null || promt.trim().isEmpty()) {
            throw new IllegalArgumentException("El promt no puede quedar vacio");
        }

        //Builder
        PromptEntity builderPromt = PromptEntity.builder()
                .prompt(promt)
                .build();

        promtDao.insertPromt(builderPromt);
        return builderPromt;
    }

    /**
     * Elimina un prompt de la base de datos por su ID.
     * @param id El ID del prompt a eliminar.
     * @return `true` si el prompt fue eliminado exitosamente, `false` en caso contrario.
     * @throws IllegalArgumentException Si el ID proporcionado no es válido (nulo o menor o igual a 0).
     */
    public boolean deletePrompt(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID no es valido");
        }
        PromptEntity promtDelete = PromptEntity.builder().id(id).build();
        return promtDao.deletePromt(promtDelete);
    }
}