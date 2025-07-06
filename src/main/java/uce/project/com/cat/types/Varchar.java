package uce.project.com.cat.types;

import uce.project.com.cat.types.inter.IDataType;

/**
 * Implementación de {@link IDataType} para el tipo de dato VARCHAR en SQL.
 * Sigue el patrón Singleton para asegurar una única instancia.
 */
public class Varchar implements IDataType {
    static Varchar varchar = null;

    /**
     * Constructor privado para el patrón Singleton.
     */
    private Varchar()
    {

    }

    /**
     * Obtiene la instancia única de la clase Varchar.
     * @return La instancia de Varchar.
     */
    public static Varchar get()
    {
        if(varchar == null)
        {
            varchar = new Varchar();
        }
        return varchar;
    }

    /**
     * Verifica si la clase proporcionada coincide con el tipo String.
     * @param clazz La clase a verificar.
     * @return `true` si la clase es String, `false` en caso contrario.
     */
    @Override
    public boolean match(Class<?> clazz) {
        return String.class.equals(clazz);
    }

    /**
     * Devuelve el nombre del tipo de dato SQL correspondiente, incluyendo un marcador de posición para la longitud.
     * @return La cadena "VARCHAR(%s)".
     */
    @Override
    public String sqlType() {
        return "VARCHAR(%s)";
    }

    /**
     * Devuelve un formato de cadena para el tipo de dato, incluyendo comillas para valores de cadena.
     * @return La cadena de formato "\"%s\"".
     */
    @Override
    public String stringifyType()
    {
        return "\"%s\"";
    }
}
