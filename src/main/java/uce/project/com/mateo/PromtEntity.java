package uce.project.com.mateo;

import uce.project.com.cat.anotations.ColumnInfo;
import uce.project.com.cat.anotations.PrimaryKey;

public class PromtEntity {
  @ColumnInfo(name = "id")
  @PrimaryKey(autoIncrement = true)
  private Integer id;

  @ColumnInfo(name = "prompt")
  private String prompt;
}
