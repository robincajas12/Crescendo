package uce.project.com.main;

import uce.project.com.cat.anotations.Dao;
import uce.project.com.cat.anotations.Query;

import java.util.List;

@Dao
public interface UserDao{
    @Query("select * from User")
    public List<User> getAll();
    @Query("select * from User where gender=true order by name")
    public List<User> getGenderTrue();
}
