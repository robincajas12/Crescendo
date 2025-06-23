package uce.project.com.database;

import uce.project.com.baraja.Song;
import uce.project.com.baraja.SongDao;
import uce.project.com.cat.anotations.Database;
import uce.project.com.condor.User;
import uce.project.com.condor.UserDao;
import uce.project.com.mateo.PromptDao;
import uce.project.com.mateo.PromptEntity;

@Database(entities = {User.class, Song.class, PromptEntity.class})
public interface AppDataBase {
    public UserDao userDao();
    public SongDao songDao();
    public PromptDao promtDao();
}
