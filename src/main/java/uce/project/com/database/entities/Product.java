package uce.project.com.database.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import uce.project.com.cat.anotations.ColumnInfo;
import uce.project.com.cat.anotations.Entity;
import uce.project.com.cat.anotations.PrimaryKey;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity("Product")
public class Product {
    @ColumnInfo(name = "id")
    @PrimaryKey
    private Integer id;
    @ColumnInfo(name = "name", params = "255")
    private String name;
    @ColumnInfo(name = "price")
    private Integer price;
}
