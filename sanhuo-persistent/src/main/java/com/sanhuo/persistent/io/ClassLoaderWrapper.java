package com.sanhuo.persistent.io;

/**
 * 封装了若干个类加载器来加载各种类
 */
public class ClassLoaderWrapper {

    //默认加载器是从外部传进来的
    ClassLoader defaultClassLoader;
    //获取系统加载器
    ClassLoader systemClassLoader;

    ClassLoaderWrapper() {
        try {
            systemClassLoader = ClassLoader.getSystemClassLoader();
        } catch (SecurityException ignored) {
        }
    }


    public Class<?> classForName(String name) throws ClassNotFoundException {
        return classForName(name, getClassLoaders(null));
    }

    public Class<?> classForName(String name, ClassLoader classLoader) throws ClassNotFoundException {
        return classForName(name, getClassLoaders(classLoader));
    }


    /*
     * 用5个类加载器一个个调用Class.forName(加载类)，只要其中任何一个加载成功，就返回
     */
    Class<?> classForName(String name, ClassLoader[] classLoader) throws ClassNotFoundException {

        for (ClassLoader cl : classLoader) {
            if (null != cl) {
                try {
                    Class<?> c = Class.forName(name, true, cl);
                    if (null != c) {
                        return c;
                    }
                } catch (ClassNotFoundException e) {
                }

            }

        }
        throw new ClassNotFoundException("Cannot find class: " + name);

    }

    //一共5个类加载器
    ClassLoader[] getClassLoaders(ClassLoader classLoader) {
        return new ClassLoader[]{
                classLoader,
                defaultClassLoader,
                Thread.currentThread().getContextClassLoader(),
                getClass().getClassLoader(),
                systemClassLoader};
    }

}
