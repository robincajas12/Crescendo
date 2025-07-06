package uce.project.com.mateo.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uce.project.com.Main;
import uce.project.com.condor.User;
import uce.project.com.mateo.shared.dto.CreateUserDto;
import uce.project.com.mateo.shared.dto.UserResponseDto;
import uce.project.com.mateo.utils.Encrypter;

import java.util.List;
import java.util.Objects;

/**
 * Caso de uso para el registro de nuevos usuarios en el sistema.
 * Se encarga de validar los datos de entrada, verificar la unicidad del correo electrónico,
 * encriptar la contraseña y guardar el nuevo usuario en la base de datos.
 */
@Service
public class SignupUseCase {
  /**
   * Crea un nuevo usuario en la base de datos.
   * Realiza validaciones sobre los datos de entrada (email, password, name) y verifica
   * que el correo electrónico no esté ya registrado.
   * Encripta la contraseña antes de guardar el usuario.
   * @param createUserDto Objeto que contiene la información necesaria para crear un usuario (email, password, name).
   * @return Un {@link UserResponseDto} con la información del usuario recién creado.
   * @throws ResponseStatusException Si `createUserDto` es nulo, o si alguno de sus campos requeridos (email, password, name) es nulo/vacío,
   *                                 si el correo electrónico ya existe, o si falla la creación del usuario en la base de datos.
   */
  public UserResponseDto createUser(CreateUserDto createUserDto) {
    if(Objects.isNull(createUserDto)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CreateUserDto cannot be null");
    }

    if(Objects.isNull(createUserDto.getEmail()) || createUserDto.getEmail().isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email cannot be null or empty");
    }

    if(Objects.isNull(createUserDto.getPassword()) || createUserDto.getPassword().isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password cannot be null or empty");
    }

    if(Objects.isNull(createUserDto.getName()) || createUserDto.getName().isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name cannot be null or empty");
    }

    List<User> userFoundByEmail = Main.db.userDao().findOneByEmail(createUserDto.getEmail());

    if(!userFoundByEmail.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
    }

    String passwordHashed = Encrypter.hash(createUserDto.getPassword());

    User user = User.builder()
        .email(createUserDto.getEmail())
        .password(passwordHashed)
        .name(createUserDto.getName())
        .build();

    boolean wasSuccessful = Main.db.userDao().insertUser(user);

    if(!wasSuccessful) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create user");
    }

    var userCreated = Main.db.userDao().findOneByEmail(createUserDto.getEmail()).get(0);
    
    return UserResponseDto.builder().id(userCreated.getId()).email(userCreated.getEmail()).name(userCreated.getName()).build();
  }
}
