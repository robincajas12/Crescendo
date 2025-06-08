package uce.project.com.database.daos;

import uce.project.com.cat.anotations.Dao;
import uce.project.com.cat.anotations.Insert;
import uce.project.com.cat.anotations.Query;
import uce.project.com.database.entities.User;

import java.util.List;

@Dao
public interface UserDao{
    @Query("select * from User")
    public List<User> getAll();
    @Query("select * from User where gender=true order by name")
    public List<User> getGenderTrue();
    @Insert
    public boolean insertUser(User user);
}
