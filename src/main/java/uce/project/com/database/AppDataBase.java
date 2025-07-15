package uce.project.com.database;

import uce.project.com.database.daos.SongDao;
import uce.project.com.cat.anotations.Database;
import uce.project.com.database.entities.Song;
import uce.project.com.database.entities.User;
import uce.project.com.database.daos.UserDao;
import uce.project.com.database.daos.PromptDao;
import uce.project.com.database.entities.PromptEntity;

/**
 * Interfaz que define la base de datos de la aplicación y sus DAOs asociados.
 * La anotación `@Database` especifica las entidades (tablas) que forman parte de esta base de datos.
 */
@Database(entities = {User.class, Song.class, PromptEntity.class})
public interface AppDataBase {
    /**
     * Proporciona una instancia del DAO para la entidad {@link User}.
     * @return Una instancia de {@link UserDao}.
     */
    public UserDao userDao();

    /**
     * Proporciona una instancia del DAO para la entidad {@link Song}.
     * @return Una instancia de {@link SongDao}.
     */
    public SongDao songDao();

    /**
     * Proporciona una instancia del DAO para la entidad {@link PromptEntity}.
     * @return Una instancia de {@link PromptDao}.
     */
    public PromptDao promtDao();
}
