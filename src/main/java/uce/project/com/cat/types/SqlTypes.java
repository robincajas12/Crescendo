package uce.project.com.cat.types;

import uce.project.com.cat.types.inter.IDataType;

/**
 * Clase de utilidad para obtener la implementación correcta de {@link IDataType}
 * basada en el tipo de clase Java proporcionado.
 */
public class SqlTypes {
    /**
     * Obtiene la implementación de {@link IDataType} que corresponde a la clase Java dada.
     * @param clazz La clase Java para la cual se busca el tipo de dato SQL.
     * @return Una instancia de {@link IDataType} que puede manejar el tipo de dato SQL correspondiente.
     * @throws RuntimeException Si la clase proporcionada no es un tipo de dato compatible.
     */
    public static IDataType get(Class<?> clazz) {
        if(Varchar.get().match(clazz)) return Varchar.get();
        if(Int.get().match(clazz)) return Int.get();
        if(Bool.get().match(clazz)) return Bool.get();
        throw new RuntimeException(clazz.getName() + " is not a compatible data type");
    }
}
