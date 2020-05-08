package com.sanhuo.persistent.base;


public class BaseProvider<T, D> extends SQL {
//    private String tableName;
//    private String primaryKey;
//    private final String DEFAULT_PRIMARY_KEY = "id";    //默认主键名
//
//
//    protected BaseProvider(String tableName) {
//        this.tableName = tableName;
//        this.primaryKey = DEFAULT_PRIMARY_KEY;
//    }
//
//    protected BaseProvider() {
//        this.tableName = getTClass().getSimpleName().toLowerCase();
//        this.primaryKey = DEFAULT_PRIMARY_KEY;
//    }
//
//    private String findAll() {
//        SQL sql = SQL.getSQL();
//        sql.select().from(tableName);
//        return sql.build();
//    }
//
//
//    private String findById() {
//        SQL sql = SQL.getSQL();
//        sql.before(findAll()).where(buildCondition(primaryKey));
//        return sql.build();
//    }
//
////    private String remove(String conditionName) {
////        SQL sql = SQL.getSQL();
////        sql.delete().from(tableName).where(buildCondition(conditionName));
////        return sql.build();
////    }
//
//    private String remove(String id) {
//        SQL sql = SQL.getSQL();
//        sql.delete().from(tableName).where(buildCondition(primaryKey));
//        return sql.build();
//    }
//
//    private String update(Object entity) throws IllegalAccessException {
//        SQL sql = getSQL();
//        sql.update(tableName);
//        List<String> conditionList = new ArrayList<>();
//        Field[] fields = ReflectionUtil.getFieldList(entity);
//        for (Field field : fields) {
//            field.setAccessible(true);
//            if (field.get(entity) != null) {
//                conditionList.add(buildCondition(field.getName()));
//            }
//        }
//        String[] condition = conditionList.toArray(new String[0]);
//        sql.set(condition);
//        sql.where(buildCondition(primaryKey));
//        return sql.build();
//    }
//
//    private String save(Object entity) {
//        List<String> condition = new ArrayList<>();
//        List<String> condition_ps = new ArrayList<>();
//        Field[] fields = ReflectionUtil.getFieldList(entity);
//        for (Field field : fields) {
//            if (BeanUtil.isNotNull(ReflectionUtil.getFieldValue(entity, field))) {
//                condition.add(field.getName());
//                condition_ps.add(buildps(field.getName()));
//            }
//        }
//        SQL sql = getSQL();
//        sql.insert(tableName).Insert_column(condition.toArray(new String[0]))
//                .value(condition_ps.toArray(new String[0]));
//        return sql.build();
//    }
//
//
//    private Class getTClass() {
//        Type type = getClass().getGenericSuperclass();
//        if (type instanceof ParameterizedType) {
//            Type[] ptype = ((ParameterizedType) type).getActualTypeArguments();
//            return (Class) ptype[0];
//        }
//        return null;
//    }
//
//
//    public String getTableName() {
//        return tableName;
//    }
//
//    public void setTableName(String tableName) {
//        this.tableName = tableName;
//    }
//
//    public String getPrimaryKey() {
//        return primaryKey;
//    }
//
//    public void setPrimaryKey(String primaryKey) {
//        this.primaryKey = primaryKey;
//    }
}
