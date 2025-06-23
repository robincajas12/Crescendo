package uce.project.com.mateo.shared.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class CreateUserDto {
  private String name;
  private String email;
  private String password;
}
