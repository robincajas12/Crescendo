package uce.project.com.mateo;

import lombok.*;
import uce.project.com.cat.anotations.ColumnInfo;
import uce.project.com.cat.anotations.Entity;
import uce.project.com.cat.anotations.PrimaryKey;

@Getter()
@Setter()
@Builder()
@NoArgsConstructor()
@AllArgsConstructor()
@Entity("prompts")
public class PromptEntity {
  @ColumnInfo(name = "id")
  @PrimaryKey(autoIncrement = true)
  private Integer id;

  @ColumnInfo(name = "prompt")
  private String prompt;
}
