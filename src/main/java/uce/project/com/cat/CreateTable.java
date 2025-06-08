package uce.project.com.cat;

import uce.project.com.cat.anotations.TableActions;

public class CreateTable implements TableActions {
    @Override
    public boolean createTable(Class<?> entity) {
        String sql = TableActions.getSql(entity);
        System.out.println(sql);
        return false;
    }

    @Override
    public boolean checkIfTableExist(String tableName) {
        return false;
    }

    @Override
    public boolean dropTableIfExist(String tableName) {
        return false;
    }
}
