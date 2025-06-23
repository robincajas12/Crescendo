package uce.project.com.condor;

import lombok.*;
import uce.project.com.cat.anotations.Entity;
import uce.project.com.cat.anotations.PrimaryKey;
import uce.project.com.cat.anotations.ColumnInfo;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity("users") //nombre de la tabla en la base de datos
public class User {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoIncrement = true)
    private Integer id;

    @ColumnInfo(name = "name", params = "255")
    private String name;

    @ColumnInfo(name = "email", params = "255")
    private String email;

    @ColumnInfo(name = "password", params = "255")
    private String password;
}
