# Manual Técnico: Arquitectura y Patrones de Diseño

Este documento proporciona un análisis técnico exhaustivo de los paquetes y clases del proyecto, con un enfoque especial en los patrones de diseño, la arquitectura general y las guías de extensibilidad.

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
