package uce.project.com.baraja;
import uce.project.com.cat.anotations.Entity;
import uce.project.com.cat.anotations.PrimaryKey;
import uce.project.com.cat.anotations.ColumnInfo;
import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity("Song")
public class Song {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoIncrement = true)
    private Integer id;

    @ColumnInfo(name = "nombre", params = "255")
    private String nombre;

    @ColumnInfo(name = "promtId", params = "255")
    private String promtId;

    @ColumnInfo(name = "genero", params = "100")
    private String genero;

    @ColumnInfo(name = "notasMusicales", params = "1000")
    private String notasMusicales;
}