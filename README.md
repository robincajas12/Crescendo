# Manual Técnico: Arquitectura y Patrones de Diseño

Este documento proporciona un análisis técnico exhaustivo de los paquetes y clases del proyecto, con un enfoque especial en los patrones de diseño, la arquitectura general y las guías de extensibilidad.

[Documentacion tecnica en ingles aquí](https://deepwiki.com/robincajas12/grupal_patrones)

------------------------------------------------------------------------

## Análisis de Patrones de Diseño

### Paquete `ai` (Inteligencia Artificial)

1.  **Inyección de Dependencias (Dependency Injection)**:
    -   **Ubicación**: `TextToSong` recibe una instancia de `GoogleAIBase` en su constructor.
    -   **Explicación**: Se desacopla la clase `TextToSong` de la implementación concreta de la IA, lo que facilita las pruebas unitarias (permitiendo el uso de Mocks) y mejora la flexibilidad del sistema.
2.  **Estrategia (Strategy)**:
    -   **Ubicación**: La interfaz `IAsk<T>` y su implementación `TextToSong`.
    -   **Explicación**: `IAsk` define una "familia de algoritmos". `TextToSong` es una estrategia concreta. Esto permite cambiar o añadir nuevas formas de interactuar con la IA sin alterar el código cliente que las utiliza.

### Paquete `cat` (Micro-ORM)

Este paquete es el corazón del sistema de persistencia y el más rico en patrones.

1.  **Proxy (Dynamic Proxy)**:
    -   **Ubicación**: `Cat.buildDataBase(...)`, `AppDatabaseHandler`, `DaoHandler`.
    -   **Explicación**: Es el patrón central. En lugar de escribir implementaciones de DAOs a mano, se generan **proxies dinámicos** en tiempo de ejecución. `DaoHandler` actúa como el `InvocationHandler` que intercepta cada llamada a un método del DAO (ej. `getAll()`), analiza sus anotaciones (`@Query`, `@Insert`) y ejecuta la lógica JDBC necesaria. Esto elimina código repetitivo y propenso a errores.
2.  **DAO (Data Access Object)**:
    -   **Ubicación**: Interfaces `UserDao`, `SongDao`, `PromptDao`.
    -   **Explicación**: Abstraen y encapsulan el acceso a la fuente de datos. Definen un contrato claro para las operaciones de persistencia sin exponer los detalles de implementación de JDBC.
3.  **Reflexión (Reflection)**:
    -   **Ubicación**: `TableActions.getSql()`, `DaoHandler`.
    -   **Explicación**: La reflexión es la técnica que permite al ORM funcionar. Se utiliza para inspeccionar clases y objetos en tiempo de ejecución. Por ejemplo, `TableActions.getSql()` usa reflexión para leer las anotaciones (`@Entity`, `@ColumnInfo`) de una clase, sus nombres de campo y tipos para construir dinámicamente la sentencia `CREATE TABLE`. `DaoHandler` la usa para mapear los `ResultSet` de JDBC a objetos de entidad, estableciendo los valores de los campos correspondientes.
4.  **Builder**:
    -   **Ubicación**: `SqlColumnInfo.Builder`.
    -   **Explicación**: Proporciona una API fluida y legible para construir objetos `SqlColumnInfo`, asegurando que se creen en un estado consistente y válido.
5.  **Singleton**:
    -   **Ubicación**: Clases en `cat.types` (`Int`, `Bool`, `Varchar`).
    -   **Explicación**: Garantiza que solo exista una instancia de cada tipo de dato, ya que son objetos sin estado y reutilizables. Esto optimiza el uso de memoria.
6.  **Fábrica (Simple Factory)**:
    -   **Ubicación**: `SqlTypes.get(Class<?> clazz)`.
    -   **Explicación**: Centraliza la lógica para obtener el objeto `IDataType` correcto basado en un tipo de clase Java, desacoplando el resto del ORM de las implementaciones concretas de los tipos.

### Paquete `database`

1.  **Fachada (Facade) / Fábrica Abstracta (Abstract Factory)**:
    -   **Ubicación**: Interfaz `AppDataBase`.
    -   **Explicación**: Actúa como una **Fachada**, proporcionando un punto de entrada único y simplificado a toda la capa de persistencia. También se comporta como una **Fábrica Abstracta** para crear la familia de objetos DAO.

------------------------------------------------------------------------

## Visión General de la Arquitectura y Flujo de Control

Comprender cómo interactúan las piezas es clave. Este es el flujo de una operación típica, como `userDao.getAll()`:

1.  **Inicialización**: La aplicación llama a `Cat.buildDataBase(AppDataBase.class, connection, ...)`.

    -   `Cat` usa **Reflexión** para leer la anotación `@Database`, encontrar las entidades (`User.class`, etc.) y crear las tablas en la BD si no existen (`TableActions.getSql`).
    -   Se crea un **Proxy Dinámico** para la interfaz `AppDataBase` usando `AppDatabaseHandler` y se devuelve.

2.  **Solicitud de un DAO**: El cliente llama a `db.userDao()` sobre la instancia de `AppDataBase` (que es un proxy).

    -   La llamada es interceptada por `AppDatabaseHandler`.
    -   Este manejador no hace más que crear otro **Proxy Dinámico**, esta vez para la interfaz `UserDao`, usando `DaoHandler` como manejador, y lo devuelve.

3.  **Ejecución de un Método DAO**: El cliente llama a `userDao.getAll()` sobre el proxy del DAO.

    -   La llamada es interceptada por `DaoHandler.invoke()`.
    -   El manejador usa **Reflexión** para ver las anotaciones del método `getAll()`. Encuentra `@Query("SELECT * FROM users")`.
    -   Ejecuta la consulta SQL vía JDBC.
    -   Recibe un `ResultSet` de la base de datos.
    -   Nuevamente, usa **Reflexión** para crear instancias de la clase `User` y poblar sus campos con los datos del `ResultSet`, columna por columna.
    -   Devuelve la `List<User>` al cliente.

------------------------------------------------------------------------

## Guía de Uso del ORM "Cat"

(Esta sección permanece igual que en la versión anterior, detallando cómo definir entidades, DAOs y usar el ORM).

### 1. Definir una Entidad

``` java
@Entity("users")
public class User { ... }
```

### 2. Crear una Interfaz DAO

``` java
@Dao
public interface UserDao { ... }
```

### 3. Definir la Fachada de la Base de Datos

``` java
@Database(entities = {User.class, Song.class})
public interface AppDataBase { ... }
```

### 4. Construir y Usar la Base de Datos

``` java
AppDataBase db = Cat.buildDataBase(AppDataBase.class, connection, true);
UserDao userDao = db.userDao();
List<User> users = userDao.getAll();
```

------------------------------------------------------------------------

## Tutorial de Extensibilidad: Añadir un Nuevo Tipo de Dato

El ORM está diseñado para ser extensible. Aquí se muestra cómo añadir soporte para el tipo `FLOAT`.

### Paso 1: Crear la Clase del Tipo de Dato

Crea una nueva clase en el paquete `uce.project.com.cat.types` que implemente `IDataType`. Debe seguir el patrón **Singleton**.

``` java
package uce.project.com.cat.types;

import uce.project.com.cat.types.inter.IDataType;

public class FloatType implements IDataType {
    private static FloatType instance = null;

    private FloatType() { }

    public static FloatType get() {
        if (instance == null) {
            instance = new FloatType();
        }
        return instance;
    }

    @Override
    public boolean match(Class<?> clazz) {
        // Coincide con la clase Float de Java
        return Float.class.equals(clazz);
    }

    @Override
    public String sqlType() {
        // El tipo SQL correspondiente
        return "FLOAT";
    }

    @Override
    public String stringifyType() {
        // Cómo formatear el valor en una consulta (los números no llevan comillas)
        return "%s";
    }
}
```

### Paso 2: Registrar el Nuevo Tipo en la Fábrica

Modifica la clase `SqlTypes` para que la **Fábrica** conozca el nuevo tipo. Añade una comprobación en el método `get()`.

``` java
package uce.project.com.cat.types;

import uce.project.com.cat.types.inter.IDataType;

public class SqlTypes {
    public static IDataType get(Class<?> clazz) {
        if (Varchar.get().match(clazz)) return Varchar.get();
        if (Int.get().match(clazz)) return Int.get();
        if (Bool.get().match(clazz)) return Bool.get();
        // Añadir esta línea
        if (FloatType.get().match(clazz)) return FloatType.get();

        throw new RuntimeException(clazz.getName() + " is not a compatible data type");
    }
}
```

### Paso 3: ¡Usar el Nuevo Tipo!

¡Eso es todo! Ahora puedes usar `Float` como tipo de dato en tus entidades, y el ORM sabrá cómo manejarlo.

``` java
@Entity("products")
public class Product {
    @ColumnInfo(name = "price")
    private Float price;
    // ... otros campos
}
```
## Configuración de CORS

El proyecto está configurado para permitir solicitudes de Cross-Origin Resource Sharing (CORS) desde cualquier origen.

### `CorsConfig.java`

-   **Propósito**: Configura las reglas de CORS para la aplicación Spring Boot.
-   **Detalles**: Permite todas las solicitudes (`/**`) desde cualquier origen (`*`), con cualquier método (`*`) y cualquier cabecera (`*`).

## Módulo de Autenticación (`auth`)

Este módulo se encarga de la gestión de usuarios, incluyendo el registro, inicio de sesión tradicional y social.

### Utilidades

#### `Encrypter.java`

-   **Propósito**: Clase de utilidad para un "hashing" simple de cadenas de texto y su verificación.
-   **Métodos Clave**:
    -   `hash(String input)`: Genera un "hash" simple. **Nota**: Este método es solo para demostración y no debe usarse para seguridad real en producción.
    -   `verify(String input, String inputExpected)`: Verifica si una cadena coincide con un hash dado.

### DTOs (Data Transfer Objects)

-   **`CreateUserDto.java`**: Utilizado para el registro de nuevos usuarios. Contiene `name`, `email` y `password`.
-   **`SigninRequestDto.java`**: Utilizado para el inicio de sesión tradicional. Contiene `email` y `password`.
-   **`SocialSigninRequestDto.java`**: Utilizado para el inicio de sesión social (e.g., Google). Contiene `email` y `name`.
-   **`UserResponseDto.java`**: Objeto de respuesta para la información del usuario. Contiene `id`, `email` y `name`.

### Servicios y Casos de Uso

#### `IHandler.java`

-   **Propósito**: Interfaz genérica para implementar el patrón Chain of Responsibility, utilizado en el inicio de sesión social.
-   **Métodos Clave**:
    -   `handleRequest(T request)`: Procesa una solicitud.
    -   `setNextHandler(IHandler<T> nextHandler)`: Establece el siguiente manejador en la cadena.

#### `GoogleSigninHandler.java`

-   **Propósito**: Manejador de la cadena de responsabilidad para el inicio de sesión social con Google.
-   **Lógica**: Busca un usuario por correo electrónico. Si existe, devuelve sus datos. Si no, pasa la solicitud al siguiente manejador.

#### `GoogleSignupHandler.java`

-   **Propósito**: Manejador de la cadena de responsabilidad para el registro de usuarios a través de Google.
-   **Lógica**: Crea un nuevo usuario en la base de datos con la información proporcionada. Es el final de la cadena de responsabilidad para el inicio de sesión social.

#### `SignupUseCase.java`

-   **Propósito**: Caso de uso para el registro de nuevos usuarios.
-   **Método Clave**:
    -   `createUser(CreateUserDto createUserDto)`: Valida los datos, verifica la unicidad del correo, encripta la contraseña y guarda el usuario. Lanza excepciones `ResponseStatusException` en caso de errores de validación o creación.

#### `SigninUseCase.java`

-   **Propósito**: Caso de uso para el inicio de sesión tradicional y social.
-   **Métodos Clave**:
    -   `signin(String email, String password)`: Autentica a un usuario con correo y contraseña.
    -   `socialSignin(SocialSigninRequestDto socialSigninRequestDto)`: Inicia el proceso de inicio de sesión social, delegando a la cadena de responsabilidad (`GoogleSigninHandler` -> `GoogleSignupHandler`).

### Controlador de Autenticación (`AuthController.java`)

-   **Ruta Base**: `/auth`
-   **Endpoints**:
    -   `POST /signup`: Registra un nuevo usuario.
        -   **Request Body**: `CreateUserDto`
        -   **Response**: `UserResponseDto`
    -   `POST /signin`: Inicia sesión con credenciales tradicionales.
        -   **Request Body**: `SigninRequestDto`
        -   **Response**: `UserResponseDto`
    -   `POST /social/signin`: Inicia sesión social.
        -   **Request Body**: `SocialSigninRequestDto`
        -   **Response**: `UserResponseDto`

## Módulo de Gestión de Prompts

Este módulo permite gestionar prompts en la base de datos.

### Managers

#### `PromptManager.java`

-   **Propósito**: Clase Singleton para gestionar las operaciones CRUD de prompts.
-   **Métodos Clave**:
    -   `getInstancia()`: Obtiene la instancia única del manager.
    -   `getAllPromts()`: Recupera todos los prompts.
    -   `guardarPromt(String promt)`: Guarda un nuevo prompt.
    -   `deletePrompt(Integer id)`: Elimina un prompt por ID.

### DTOs

-   **`PromptRequest.java`**: Utilizado para solicitudes relacionadas con prompts. Contiene el campo `prompt`.

### Controlador de Prompts (`PromptController.java`)

-   **Ruta Base**: `/api/prompt`
-   **Endpoints**:
    -   `GET /`: Obtiene todos los prompts.
        -   **Response**: `List<PromptEntity>`
    -   `POST /`: Guarda un nuevo prompt.
        -   **Request Body**: `PromptRequest`
        -   **Response**: `PromptEntity` (HTTP 201 Created)
    -   `DELETE /`: Elimina un prompt por ID.
        -   **Request Param**: `id` (Integer)
        -   **Response**: `String` (mensaje de éxito o error)

## Módulo de Gestión de Canciones

Este módulo permite gestionar canciones, incluyendo la generación de canciones a partir de prompts utilizando IA.

### Managers

#### `SongManager.java`

-   **Propósito**: Clase Singleton para gestionar las operaciones CRUD de canciones.
-   **Métodos Clave**:
    -   `getInstancia()`: Obtiene la instancia única del manager.
    -   `getAllSongs()`: Recupera todas las canciones.
    -   `eliminarSong(Integer id)`: Elimina una canción por ID.

### DTOs

-   **`PostSongBody.java`**: Utilizado para crear una canción. Contiene `prompt` y `userId`.
-   **`BodyCreateSong.java`**: (Comentado en `CreateSongController`) Similar a `PostSongBody`, contiene `prompt` y `userId`.
-   **`SongRequest.java`**: Utilizado para solicitudes relacionadas con canciones. Contiene `userId` y `songId`.

### Controlador de Canciones (`SongController.java`)

-   **Ruta Base**: `/api/song`
-   **Endpoints**:
    -   `GET /`: Obtiene todas las canciones.
        -   **Response**: `List<Song>`
    -   `POST /`: Crea una nueva canción utilizando Google AI.
        -   **Request Body**: `PostSongBody`
        -   **Response**: `String` (el contenido musical generado)
    -   `DELETE /`: Elimina una canción por ID.
        -   **Request Param**: `id` (Integer)
        -   **Response**: `String` (mensaje de éxito o error)

### `CreateSongController.java`

-   **Propósito**: Controlador para la creación y gestión de canciones.
-   **Nota**: Los métodos de este controlador están actualmente comentados y la funcionalidad se ha trasladado a `SongController.java`.

## Cómo Ejecutar el Proyecto

1.  **Clonar el Repositorio**:
    ```bash
    git clone <URL_DEL_REPOSITORIO>
    cd <NOMBRE_DEL_PROYECTO>
    ```
2.  **Configurar la Base de Datos**: Asegúrate de que la configuración de la base de datos en `Main.java` o en un archivo de propiedades esté correcta.
3.  **Configurar la API de Google AI**:
    -   Obtén una clave API para Google AI (Gemini).
    -   Asegúrate de que la clave API esté configurada correctamente en `ConfigReader.get("google_ai.apikey")`. Esto podría implicar un archivo de configuración o variables de entorno.

    Ejecutar directamente la clase `Main` dado que es una aplicación Spring Boot estándar.

## Tecnologías Utilizadas

-   **Spring Boot**: Framework para el desarrollo de aplicaciones Java.
-   **Lombok**: Librería para reducir el código repetitivo (getters, setters, constructores).
-   **Google AI API (Gemini)**: Para la generación de contenido musical.
-   **Base de Datos**: (No especificada en el contexto, pero se infiere el uso de DAOs).

---
## Consumo de Endpoints (API Reference)

### Autenticación

#### 1. Registro de Usuario (Signup)
**Endpoint**: `POST /auth/signup`

**Request Body (JSON)**:
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "SecurePassword123!"
}
```
**Response (Éxito - 201 Created):**
```json
{
  "id": 1,
  "email": "john@example.com",
  "name": "John Doe"
}
```
**Posibles Errores:**

- `400 Bad Request`: Si faltan campos o el email es inválido
- `400 Bad Request`: Si el email ya está registrado

#### 2. Inicio de Sesión Tradicional (Signin)
**Endpoint:** `POST /auth/signin`

**Request Body (JSON)**:
```json
{
  "email": "john@example.com",
  "password": "SecurePassword123!"
}
```
**Response (Éxito - 200 OK):**
```json
{
  "id": 1,
  "email": "john@example.com",
  "name": "John Doe"
}
```
**Posibles Errores:**
- `400 Bad Request`: Credenciales inválidas
- `400 Bad Request`: Usuario no existe
#### 3. Inicio de Sesión Social (Google)
**Endpoint:** `POST /auth/social/signin`

**Request Body (JSON)**:
```json
{
  "email": "googleuser@example.com",
  "name": "Google User"
}
```
**Response (Éxito - 200 OK):**
```json
{
  "id": 2,
  "email": "googleuser@example.com",
  "name": "Google User"
}
```
### Gestión de Prompts
#### 1. Obtener Todos los Prompts
**Endpoint:** `GET /api/prompt`

**Response (Éxito - 200 OK):**
```json
[
  {
    "id": 1,
    "text": "Genera una canción sobre el mar",
    "createdAt": "2023-11-20T10:00:00Z"
  },
  {
    "id": 2,
    "text": "Escribe una balada romántica",
    "createdAt": "2023-11-21T11:30:00Z"
  }
]
```
#### 2. Crear un Nuevo Prompt
**Endpoint:** `POST /api/prompt`

**Request Body (JSON):**
```json
{
  "prompt": "Crea una canción sobre viajes interestelares"
}
```
**Response (Éxito - 201 Created):**
```json
{
  "id": 3,
  "text": "Crea una canción sobre viajes interestelares",
  "createdAt": "2023-11-22T09:15:00Z"
}
```
#### 3. Eliminar un Prompt
**Endpoint:** `DELETE /api/prompt?id={id}`
**Parametro URL**:
- `id`: : ID del prompt a eliminar (ej: `3`)
**Response (Éxito - 200 OK):**
```json
"Prompt eliminado correctamente"
```
### Gestion de canciones
#### 1. Obtener todas las canciones
**Endpoint:** `GET /api/song`

**Response (Éxito - 200 OK):**
```json
[
  {
    "id": 1,
    "content": "Verso 1: Navegando por el cosmos...",
    "userId": 1,
    "createdAt": "2023-11-20T14:30:00Z"
  }
]
```
#### 2. Generar una Nueva Canción con IA
**Endpoint:** `POST /api/song`
**Request Body (JSON):**
```json
{
  "prompt": "Escribe una canción sobre inteligencia artificial",
  "userId": 1
}
```
**Response (Éxito - 200 OK):**
```json
{
  "id": 2,
  "content": "Verso 1: Los algoritmos sueñan...\nCoro: IA, IA, qué será...",
  "userId": 1,
  "createdAt": "2023-11-22T10:45:00Z"
}
```
**Posibles Errores:**

- `400 Bad Request`: Prompt inválido o usuario no existe
- `500 Internal Server Error`: Error al conectar con la API de Google AI
#### 3. Eliminar una Canción
**Endpoint:** `DELETE /api/song?id={id}`
**Parametro URL**:
- `id`: : ID de la cancion a eliminar (ej: `3`)
**Response (Éxito - 200 OK):**
```json
"Canción eliminada correctamente"
```
