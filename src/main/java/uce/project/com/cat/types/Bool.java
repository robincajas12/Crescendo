package uce.project.com.cat.types;

import uce.project.com.cat.types.inter.IDataType;

/**
 * Implementación de {@link IDataType} para el tipo de dato booleano (BOOL) en SQL.
 * Sigue el patrón Singleton para asegurar una única instancia.
 */
public class Bool implements IDataType {
    private static Bool dBool = null;

    /**
     * Constructor privado para el patrón Singleton.
     */
    private Bool()
    {
    }

    /**
     * Obtiene la instancia única de la clase Bool.
     * @return La instancia de Bool.
     */
    public static Bool get()
    {
        if(dBool == null) dBool = new Bool();
        return dBool;
    }

    /**
     * Verifica si la clase proporcionada coincide con el tipo Boolean.
     * @param clazz La clase a verificar.
     * @return `true` si la clase es Boolean, `false` en caso contrario.
     */
    @Override
    public boolean match(Class<?> clazz) {
        return Boolean.class.equals(clazz);
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

    /**
     * Devuelve el nombre del tipo de dato SQL correspondiente.
     * @return La cadena "BOOL".
     */
    @Override
    public String sqlType() {
        return "BOOL";
    }
}
