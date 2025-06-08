package uce.project.com.database;

import uce.project.com.cat.anotations.Database;
import uce.project.com.database.daos.ProductDao;
import uce.project.com.database.daos.UserDao;
import uce.project.com.database.entities.Product;
import uce.project.com.database.entities.User;

@Database(entities = {User.class, Product.class})
public interface AppDataBase {
    public UserDao userDao();
    public ProductDao productDao();
}
