package uce.project.com.condor;

import uce.project.com.cat.anotations.Dao;
import uce.project.com.cat.anotations.Query;
import uce.project.com.cat.anotations.Insert;
import uce.project.com.cat.anotations.Update;
import uce.project.com.cat.anotations.Delete;
import uce.project.com.cat.anotations.P;
import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users")
    List<User> getAll();

    @Query("SELECT * FROM users WHERE id = :id")
    List<User> getById(@P("id") Integer id);

    @Query("SELECT username FROM users WHERE id = :id")
    String getUsernameById(@P("id") Integer id);

    @Query("SELECT * FROM users WHERE username LIKE :username")
    List<User> searchByUsername(@P("username") String username);

    @Insert
    boolean insertUser(User user);

    @Update
    boolean updateUser(User user);

    @Delete
    boolean deleteUser(User user);
}
