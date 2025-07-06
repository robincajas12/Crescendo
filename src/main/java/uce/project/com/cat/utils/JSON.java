package uce.project.com.cat.utils;

import uce.project.com.cat.anotations.ColumnInfo;
import uce.project.com.cat.anotations.Entity;
import uce.project.com.cat.types.SqlTypes;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase de utilidad para serializar objetos de entidad a formato JSON.
 * Permite convertir una entidad individual o una lista de entidades en una cadena JSON.
 */
public class JSON {
    /**
     * Convierte un objeto de entidad a su representación en cadena JSON.
     * Solo procesa campos anotados con {@link ColumnInfo}.
     * @param obj El objeto de entidad a serializar. Debe estar anotado con {@link Entity}.
     * @param <T> El tipo del objeto de entidad.
     * @return Una cadena JSON que representa el objeto de entidad.
     * @throws RuntimeException Si el objeto no está anotado con {@link Entity} o si ocurre un error durante la serialización.
     */
    public static <T> String stringifyEntity(T obj) {
        if(!obj.getClass().isAnnotationPresent(Entity.class)) throw new RuntimeException(obj.getClass().getName() + " can not be parsed because is not an entity");
        try {
            var fields = obj.getClass().getDeclaredFields();
            StringBuilder jsonTxt = new StringBuilder();
            jsonTxt.append("{");
            for(var field : fields)
            {
                if(field.isAnnotationPresent(ColumnInfo.class))
                field.setAccessible(true);
                if(field.get(obj) == null) continue;
                var data = String.format(SqlTypes.get(field.getType()).stringifyType(), field.get(obj));
                jsonTxt.append(String.format("%s: %s,",field.getAnnotation(ColumnInfo.class).name(),data));
            }
            jsonTxt = new StringBuilder(jsonTxt.substring(0, jsonTxt.length()-1));
            jsonTxt.append("}");
            return jsonTxt.toString();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        throw new RuntimeException("Error  in JSON class xddd xd");
    }

    /**
     * Convierte una lista de objetos de entidad a su representación en cadena JSON.
     * Cada entidad en la lista se serializa utilizando {@link #stringifyEntity(Object)}.
     * @param item La lista de objetos de entidad a serializar.
     * @param <T> El tipo de los objetos de entidad en la lista.
     * @return Una cadena JSON que representa la lista de entidades.
     */
    public static <T> String stringify(List<T> item) {
        return item.stream().map(JSON::stringifyEntity).toList().toString();
    }
}
