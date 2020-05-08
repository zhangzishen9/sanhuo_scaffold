package com.sanhuo.persistent.util;



/**
 * SQL语句操作工具类
 *
 * @author sanhuo
 * @version 0.9
 * @updateTime 2019-4-26
 */
public class SqlUtil {
//    private final String SQL = "sql";
//    private final String PARAMS = "params";
//
//    /**
//     * 解析sql
//     *
//     * @param method
//     * @param args
//     * @param genricClass
//     * @return
//     */
//    public static String getSql(Method method, Object[] args, Class genricClass) {
//        String sql = null;
//        if (AnnotationUtil.isSelect(method)) {
//            Select select = AnnotationUtil.getSelectAnnotation(method);
//            SelectProvider selectProvider = AnnotationUtil.getSelectProviderAnnotation(method);
//            if (select != null) {
//                sql = select.value();
//            } else if (selectProvider != null) {
//                sql = providerBuilder(method, args, genricClass);
//            }
//        } else if (AnnotationUtil.isInsert(method)) {
//            Insert insert = AnnotationUtil.getInsertAnnotation(method);
//            InsertProvider insertProvider = AnnotationUtil.getInsertProviderAnnotation(method);
//            if (insert != null) {
//                sql = insert.value();
//            } else if (insertProvider != null) {
//                sql = providerBuilder(method, args, genricClass);
//            }
//        } else if (AnnotationUtil.isUpdate(method)) {
//            Update update = AnnotationUtil.getUpdateAnnotation(method);
//            UpdateProvider updateProvider = AnnotationUtil.getUpdateProviderAnnotation(method);
//            if (update != null) {
//                sql = update.value();
//            } else if (updateProvider != null) {
//                sql = providerBuilder(method, args, genricClass);
//            }
//        } else if (AnnotationUtil.isDelete(method)) {
//            Delete delete = AnnotationUtil.getDeleteAnnotation(method);
//            DeleteProvider deleteProvider = AnnotationUtil.getDeleteProviderAnnotation(method);
//            if (delete != null) {
//                sql = delete.value();
//            } else if (deleteProvider != null) {
//                sql = providerBuilder(method, args, genricClass);
//            }
//        }
//        return sql;
//    }
//
//    /**
//     * 解析provider生成的sql
//     *
//     * @param method
//     * @param args
//     * @param genricClass
//     * @return
//     */
//    public static String providerBuilder(Method method, Object[] args, Class genricClass) {
//        Class providerClass = null;
//        String methodName = null;
//        Method providerMethod = null;
//        String sql = null;
//        try {
//            /*
//                   获取provider类和方法名
//             */
//            if (AnnotationUtil.isSelect(method)) {
//                SelectProvider selectProvider = AnnotationUtil.getSelectProviderAnnotation(method);
//                providerClass = selectProvider.type();
//                methodName = selectProvider.method();
//            } else if (AnnotationUtil.isUpdate(method)) {
//                UpdateProvider updateProvider = AnnotationUtil.getUpdateProviderAnnotation(method);
//                providerClass = updateProvider.type();
//                methodName = updateProvider.method();
//            } else if (AnnotationUtil.isInsert(method)) {
//                InsertProvider insertProvider = AnnotationUtil.getInsertProviderAnnotation(method);
//                providerClass = insertProvider.type();
//                methodName = insertProvider.method();
//            } else if (AnnotationUtil.isDelete(method)) {
//                DeleteProvider deleteProvider = AnnotationUtil.getDeleteProviderAnnotation(method);
//                providerClass = deleteProvider.type();
//                methodName = deleteProvider.method();
//            }
//            /*
//                判断是否是provider类是否是BaseProvider,是的话说明是BaseMapper类,生成一个Baseprovider进去找方法
//            */
//            if (BaseProvider.class.getSimpleName().toLowerCase().equals(providerClass.getSimpleName().toLowerCase())) {
//                Constructor constructor = BaseProvider.class.getDeclaredConstructor(String.class);
//                constructor.setAccessible(true);
//                String tableName = AnnotationUtil.getTableName(genricClass);
//                /*
//                 调用带参构造,生成一个baseprovider,把表名传进去
//                 */
//                BaseProvider baseProvider = (BaseProvider) constructor.newInstance(tableName);
//                /*
//                 * 获取参数类型
//                 */
//                Class[] methodArgsClass = ClazzUtil.getArgsClass(args);
//                /*
//                反射获取方法
//                 */
//                providerMethod = BaseProvider.class.getDeclaredMethod(methodName, methodArgsClass);
////                ExceptionUtil.throwException(BeanUtil.isNull(providerMethod),
////                        "不存在该方法:" + "BaseProvider.Class." + methodName + "()");
//                if (BeanUtil.isNull(providerMethod)) {
//                    throw new RuntimeException("不存在该方法:" + "BaseProvider.Class." + methodName + "()");
//                }
//                providerMethod.setAccessible(true);
//                sql = (String) providerMethod.invoke(baseProvider, methodArgsClass);
//            } else {
//                /*
//                 * 获取参数类型
//                 */
//                Class[] methodArgsClass = ClazzUtil.getArgsClass(args);
//                   /*
//                反射获取方法
//                 */
//                providerMethod = BaseProvider.class.getDeclaredMethod(methodName, methodArgsClass);
//                sql = (String) providerMethod.invoke(providerClass.newInstance(), methodArgsClass);
//            }
//
//        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
//            e.printStackTrace();
//        }
//        return sql;
//    }
//
//
//    /**
//     * 将带有#{}的sql语句解析,获取#{}的参数名称和解析后的sql语句
//     *
//     * @param sql
//     * @return
//     */
//    private Map<String, Object> buildSql(String sql) {
//        int end = -1;
//        List<String> params = new ArrayList<>();
//        Map<String, Object> result = new HashMap<>();
//        StringBuilder sqlBuilder = new StringBuilder(sql);
//        for (int begin = sqlBuilder.indexOf("#{"); begin != -1; begin = sqlBuilder.indexOf("#{", begin + 1)) {
//            end = sqlBuilder.indexOf("}", end + 1);
//            params.add(sqlBuilder.substring(begin + 2, end));
//            sqlBuilder.replace(begin, end + 1, "?");
//            end = -1;
//        }
//        result.put(SQL, sqlBuilder.toString());
//        result.put(PARAMS, params);
//        return result;
//    }
}
