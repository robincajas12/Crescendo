package uce.project.com.cat.proxy.handlers;

import uce.project.com.cat.anotations.Query;
import uce.project.com.main.User;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;

public class AppDatabaseHandler implements InvocationHandler
{

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return Proxy.newProxyInstance(AppDatabaseHandler.class.getClassLoader(), new Class[]{method.getReturnType()}, new DaoHandler());
    }
}
