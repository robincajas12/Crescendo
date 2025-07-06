package uce.project.com.cat.types;

import uce.project.com.cat.types.inter.IDataType;

/**
 * Implementación de {@link IDataType} para el tipo de dato entero (INT) en SQL.
 * Sigue el patrón Singleton para asegurar una única instancia.
 */
public class Int implements IDataType {
    static Int dInt = null;

    /**
     * Constructor privado para el patrón Singleton.
     */
    private Int()
    {

    }

    /**
     * Obtiene la instancia única de la clase Int.
     * @return La instancia de Int.
     */
    public static Int get()
    {
        if(dInt == null)
        {
            dInt = new Int();
        }
        return dInt;
    }

    /**
     * Verifica si la clase proporcionada coincide con el tipo Integer.
     * @param clazz La clase a verificar.
     * @return `true` si la clase es Integer, `false` en caso contrario.
     */
    @Override
    public boolean match(Class<?> clazz) {
        return Integer.class.equals(clazz);
    }

    /**
     * Devuelve el nombre del tipo de dato SQL correspondiente.
     * @return La cadena "INT".
     */
    @Override
    public String sqlType() {
        return "INT";
    }

    /**
     * Devuelve un formato de cadena para el tipo de dato.
     * @return La cadena de formato "%s".
     */
    @Override
    public String stringifyType()
    {
        return "%s";
    }
}

