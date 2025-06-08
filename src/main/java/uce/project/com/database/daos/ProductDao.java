package uce.project.com.database.daos;

import uce.project.com.cat.anotations.Dao;
import uce.project.com.cat.anotations.Insert;
import uce.project.com.cat.anotations.Query;
import uce.project.com.database.entities.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("select * from Product")
    public List<Product> getAll();
    @Insert
    public boolean insert(Product product);
}
