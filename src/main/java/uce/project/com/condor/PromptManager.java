package uce.project.com.condor;

import uce.project.com.Main;
import uce.project.com.mateo.PromptDao;
import uce.project.com.mateo.PromptEntity;
import java.util.List;

//Singleton
public class PromptManager {

    private static PromptManager instancia;
    private final PromptDao promtDao;

    private PromptManager() {
        this.promtDao = Main.db.promtDao();
    }

    public static synchronized PromptManager getInstancia() {
        if (instancia == null) {
            instancia = new PromptManager();
        }
        return instancia;
    }

    public List<PromptEntity> getAllPromts() {
        return promtDao.getAll();
    }

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

    public boolean deletePrompt(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID no es valido");
        }
        PromptEntity promtDelete = PromptEntity.builder().id(id).build();
        return promtDao.deletePromt(promtDelete);
    }
}