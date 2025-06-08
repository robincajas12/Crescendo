package uce.project.com.main;

import uce.project.com.cat.RoomDatabase;
import uce.project.com.cat.anotations.Database;

@Database(entities = {User.class})
public interface AppDataBase {
    public UserDao userDao();
}
