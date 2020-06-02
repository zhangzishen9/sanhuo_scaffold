package com.sanhuo.persistent.io;

import java.nio.charset.Charset;

/**
 * 通过类加载器获得各类资源
 */
public class Resources {

    //大多数方法都是委托给ClassLoaderWrapper，再去做真正的事
    private static ClassLoaderWrapper classLoaderWrapper = new ClassLoaderWrapper();

    /*
     * 调用getResourceAsReader用的字符集,null表示使用系统默认
     */
    private static Charset charset;

    Resources() {
    }

    public static ClassLoader getDefaultClassLoader() {
        return classLoaderWrapper.defaultClassLoader;
    }

    /**
     * 传入classLoaderWrapper使用的默认类加载器
     */
    public static void setDefaultClassLoader(ClassLoader defaultClassLoader) {
        classLoaderWrapper.defaultClassLoader = defaultClassLoader;
    }

    /**
     * 根据ClassName生成class
     */
    public static Class<?> classForName(String className) throws ClassNotFoundException {
        return classLoaderWrapper.classForName(className);
    }

    public static Charset getCharset() {
        return charset;
    }

    public static void setCharset(Charset charset) {
        Resources.charset = charset;
    }

}
