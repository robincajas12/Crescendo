package uce.project.com.mateo.shared.dto;

import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class UserResponseDto {
  public Integer id;
  public String email;
  public String name;
}
