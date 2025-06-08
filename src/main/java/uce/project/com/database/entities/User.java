package uce.project.com.database.entities;
import lombok.*;
import uce.project.com.cat.anotations.Entity;
import uce.project.com.cat.anotations.PrimaryKey;
import uce.project.com.cat.anotations.ColumnInfo;


@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity("User")
public class User {
    @ColumnInfo(name="id")
    @PrimaryKey()
    private Integer id;
    @ColumnInfo(name="name", params = "255")
    private String name;
    @ColumnInfo(name = "apellido", params = "255")
    private String apellido;
    @ColumnInfo(name = "gender")
    private Boolean gender;
}
