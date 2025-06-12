package uce.project.com.mateo;

import uce.project.com.cat.anotations.*;

import java.util.List;

public interface PromptDao {

  @Query("SELECT * FROM promt WHERE id = :id")
  PromptEntity getPromtById(@P("id") Integer id);

  @Query("SELECT * FROM promt WHERE prompt LIKE '%' || :word || '%'")
  PromptEntity filterByWord(@P("word") String word);

  @Query("SELECT * FROM promt")
  List<PromptEntity> getAll();

  @Insert()
  boolean insertPromt(PromptEntity promtEntity);

  @Update()
  boolean updatePromt(PromptEntity promtEntity);

  @Delete()
  boolean deletePromt(PromptEntity promtEntity);
}
