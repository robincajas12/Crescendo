package uce.project.com.cat.proxy.handlers;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Manejador de invocación para la interfaz {@link uce.project.com.database.AppDataBase}.
 * Este manejador intercepta las llamadas a los métodos de la interfaz AppDataBase
 * y devuelve una instancia de proxy para el DAO correspondiente, manejado por {@link DaoHandler}.
 */
public class AppDatabaseHandler implements InvocationHandler
{

    /**
     * Procesa las invocaciones de métodos en la interfaz proxy de AppDataBase.
     * Cuando se llama a un método que devuelve una interfaz DAO (ej. `songDao()`),
     * este método crea y devuelve un proxy para esa interfaz DAO, utilizando {@link DaoHandler}.
     * @param proxy La instancia de proxy en la que se invocó el método.
     * @param method El objeto Method correspondiente al método invocado.
     * @param args Un array de objetos que contiene los valores de los argumentos pasados al método invocado.
     * @return Una instancia de proxy para la interfaz DAO solicitada.
     * @throws Throwable Si ocurre un error durante la invocación del método.
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return Proxy.newProxyInstance(AppDatabaseHandler.class.getClassLoader(), new Class[]{method.getReturnType()}, new DaoHandler());
    }
}
