package uce.project.com.cat.types;

import uce.project.com.cat.types.inter.IDataType;

public class Bool implements IDataType {
    private static Bool dBool = null;
    private Bool()
    {
    }
    public static Bool get()
    {
        if(dBool == null) dBool = new Bool();
        return dBool;
    }
    @Override
    public boolean match(Class<?> clazz) {
        return Boolean.class.equals(clazz);
    }
    @Override
    public String stringifyType()
    {
        return "%s";
    }

    @Override
    public String sqlType() {
        return "BOOL";
    }
}
