package uce.project.com.database.daos;

import uce.project.com.cat.anotations.*;
import uce.project.com.database.entities.User;

import java.util.List;

@Dao
public interface UserDao{
    @Query("select * from User")
    public List<User> getAll();
    @Query("select * from User where gender=:gender order by name")
    public List<User> getGenderTrue(@P("gender") Boolean gender);
    @Query("select * from User where id = :id")
    public List<User> getById(@P("id") Integer id);
    @Insert public boolean insertUser(User user);
    @Update public boolean updateUser(User user);
    @Delete public boolean deleteUser(User user);
}
