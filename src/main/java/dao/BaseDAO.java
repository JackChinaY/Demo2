package dao;
/*
 * DAO层 常用的基本方法，封装在BaseDAO中
 */

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONObject;

import util.ConnectionHolder;
import util.SingleThreadConnectionHolder;

/**
 * DAO层 常用的基本方法，封装在BaseDAO中
 */
public class BaseDAO {
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    private DataSource dataSource;
    private ConnectionHolder connectionHolder = new ConnectionHolder();

    public BaseDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 查询符合条件的记录数 或者求取最大值
     * 格式：SELECT count(*) AS count FROM table
     *
     * @param sql  要执行的sql语句
     * @param args 给sql语句中的？赋值的参数列表
     * @return 符合条件的记录数 0或1/2/3实际值  或者返回字段的最大值比如1000，如果没值就返回0
     */
    public int getCount(String sql, Object... args) {
        conn = getConnection(dataSource);
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            if (rs.next()) {  //若为数据库null值就不执行此代码，而是返回0L
                return rs.getInt(1);  //返回count（*）的值
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, rs);
        }
        return 0;
    }

    /**
     * 查询符合条件的记录,结果只有一条，且只有一个字段
     * 格式：SELECT name AS Name FROM table WHERE id=?
     *
     * @param sql  要执行的sql语句
     * @param args 给sql语句中的？赋值的参数列表
     * @return 字段的值, 如果没值的话就返回null，比如返回Name字段的值张三
     */
    public String getOneRecard(String sql, Object... args) {
        conn = getConnection(dataSource);
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);  //返回name的值
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, rs);
        }
        return null;
    }

    /**
     * 查询实体对象的，并将查询结果封装成Json数组，常用查询之一
     *
     * @param sql  要执行的sql语句
     * @param args 给sql语句中的？赋值的参数列表
     * @return 要查询的类的集合，返回Json，如果没值就返回空数组[ ]
     */
    public String getForJson(String sql, Object... args) {
        conn = getConnection(dataSource);
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            // json数组
            JSONArray array = new JSONArray();

            // 获取列数  
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // 遍历ResultSet中的每条数据  
            while (rs.next()) {
                JSONObject jsonObj = new JSONObject();

                // 遍历每一列
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnLabel(i);
                    String value = rs.getString(columnName);
                    jsonObj.put(columnName, value);
                }
                array.put(jsonObj);
            }

            return array.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, rs);
        }
        return null;
    }

    /**
     * 查询实体对象，并将查询封装到List
     *
     * @param sql  要执行的sql语句
     * @param args 给sql语句中的？赋值的参数列表
     * @return 要查询的类的集合，返回List,无结果时返回null
     */
    public List<Map<String, Object>> getListForMap(String sql, Object... args) {
        conn = getConnection(dataSource);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();   //获得列数 
            while (rs.next()) {
                Map<String, Object> rowData = new HashMap<String, Object>();
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(md.getColumnName(i), rs.getObject(i));
                }
                list.add(rowData);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, rs);
        }
        return null;
    }

    /**
     * 查询实体对象的，并封装到集合
     *
     * @param <T>   要查询的对象的集合
     * @param sql   要执行的sql语句
     * @param clazz 要查询的对象的类
     * @param args  给sql语句中的？赋值的参数列表
     * @return 要查询的类的集合，无结果时返回null
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> getListForEntity(String sql, Class<T> clazz, Object... args) {
        conn = getConnection(dataSource);
        @SuppressWarnings("rawtypes")
        List list = new ArrayList();
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            Field[] fs = clazz.getDeclaredFields();
            String[] colNames = new String[fs.length];
            String[] rTypes = new String[fs.length];
            Method[] methods = clazz.getMethods();
            while (rs.next()) {
                for (int i = 0; i < fs.length; i++) {
                    Field f = fs[i];
                    String colName = f.getName().substring(0, 1).toUpperCase()
                            + f.getName().substring(1);
                    colNames[i] = colName;
                    String rType = f.getType().getSimpleName();
                    rTypes[i] = rType;
                }

                Object object = (T) clazz.newInstance();
                for (int i = 0; i < colNames.length; i++) {
                    String colName = colNames[i];
                    String methodName = "set" + colName;
                    // 查找并调用对应的setter方法赋值
                    for (Method m : methods) {
                        if (methodName.equals((m.getName()))) {
                            // 如果抛了参数不匹配异常，检查JavaBean中该属性类型，并添加else分支进行处理
                            if ("int".equals(rTypes[i])
                                    || "Integer".equals(rTypes[i])) {
                                m.invoke(object, rs.getInt(colName));
                            } else if ("Date".equals(rTypes[i])) {
                                m.invoke(object, rs.getDate(colName));
                            } else if ("Timestamp".equals(rTypes[i])) {
                                m.invoke(object, rs.getTimestamp(colName));
                            } else {
                                m.invoke(object, rs.getObject(colName));
                            }
                            break;
                        }
                    }
                }
                list.add(object);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, rs);
        }
        return null;
    }

    /**
     * 以对象的形式保存或更新一个实体，参数为一个类实体，常用保存之一
     *
     * @param sql    要执行的sql语句
     * @param object 要保存或更新的实体对象
     * @param args   不需要赋值的列标组成的数组，例如sql语句
     *               "insert into tbl_user values(seq_user.nextval,?,?,?)"应为1
     * @return 操作结果: 成功返回1，失败返回0
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public int saveEntity(String sql, Object object, int... args) {
        conn = getConnection(dataSource);
        try {
            ps = conn.prepareStatement(sql);
            Class c = object.getClass();
            Field[] fields = object.getClass().getDeclaredFields();
            int temp = 1;// 正赋值的？的下标，最大下标为args的长度
            int colIndex = 1;// SQL语句中的当前字段下标  
            int t = 0;// args数组的下标
            for (int j = 0; j < fields.length; j++) {
                Field field = fields[j];// 得到某个声明属性 
                String methodName = "get"
                        + field.getName().substring(0, 1).toUpperCase()
                        + field.getName().substring(1);
                Method method = c.getMethod(methodName);// 得到了当前属性的get方法名  
                String rType = field.getType().getSimpleName().toString();
                if (t < args.length && colIndex == args[t]) {
                    t++;
                } else if ("int".equals(rType) || "INTEGER".equals(rType)) {
                    ps.setInt(temp++, (Integer) method.invoke(object));
                } else {
                    ps.setObject(temp++, method.invoke(object));
                }
                colIndex++;// 更新索引下标  
            }
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, null);
        }
        return 0;
    }

    /**
     * 执行可变参数的SQL语句，进行保存或删除或更新操作，参数为零散，,常用更新、保存之一
     *
     * @param sql  要执行的sql语句，赋值顺序必须与args数组的顺序相同
     * @param args 要赋值的参数列表
     * @return 操作结果，正数是成功，是受影响的行数，0为失败
     */
    public int saveOrUpdateOrDelete(String sql, Object... args) {
        conn = getConnection(dataSource);
        try {
            ps = conn.prepareStatement(sql);
            for (int j = 0; j < args.length; j++) {
                ps.setObject(j + 1, args[j]);
            }
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, null);
        }
        return 0;
    }

    /***********************************执行事务的方法******************************/
    /**
     * 查询符合条件的记录数 （执行事务）
     *
     * @param sql  要执行的sql语句
     * @param args 给sql语句中的？赋值的参数列表
     * @return 符合条件的记录数
     */
    public long getCountTran(String sql, Object... args) {
        conn = getSingleThreadConnection(dataSource);
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            } else {
                return 0L;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't close connection[" + conn + "].", e);
        }
    }

    /**
     * 查询实体对象的，并封装到Json中
     *
     * @param sql  要执行的sql语句
     * @param args 给sql语句中的？赋值的参数列表
     * @return 要查询的类的集合，返回Json
     */
    public String getForJsonTran(String sql, Object... args) {
        conn = getSingleThreadConnection(dataSource);
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            // json数组
            JSONArray array = new JSONArray();

            // 获取列数  
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // 遍历ResultSet中的每条数据  
            while (rs.next()) {
                JSONObject jsonObj = new JSONObject();

                // 遍历每一�?
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnLabel(i);
                    //String columnType=metaData.getColumnTypeName(i);
                    // System.out.println("此字段类型："+columnType);
                    String value = rs.getString(columnName);
                    jsonObj.put(columnName, value);
                }
                array.put(jsonObj);
//                 System.out.println("basedaolimian"+jsonObj.toString());
            }

            return array.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't close connection[" + conn + "].", e);
        }
    }

    /**
     * 查询实体对象，并将查询封装到�?��List中（执行事务�?
     *
     * @param sql  要执行的sql语句
     * @param args 给sql语句中的？赋值的参数列表
     * @return 要查询的类的集合，返回List,无结果时返回null
     */
    public List<Map<String, Object>> getListForMapTran(String sql, Object... args) {
        conn = getSingleThreadConnection(dataSource);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();   //获得列数 
            while (rs.next()) {
                Map<String, Object> rowData = new HashMap<String, Object>();
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(md.getColumnName(i), rs.getObject(i));
                }
                list.add(rowData);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't close connection[" + conn + "].", e);
        }

    }

    /**
     * 查询实体对象的，并封装到集合 （执行事务）
     *
     * @param <T>   要查询的对象的集合
     * @param sql   要执行的sql语句
     * @param clazz 要查询的对象的类
     * @param args  给sql语句中的？赋值的参数列表
     * @return 要查询的类的集合，无结果时返回null
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public <T> List<T> getListForEntityTran(String sql, Class<T> clazz, Object... args) {
        conn = getSingleThreadConnection(dataSource);
        List list = new ArrayList();
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            Field[] fs = clazz.getDeclaredFields();
            String[] colNames = new String[fs.length];
            String[] rTypes = new String[fs.length];
            Method[] methods = clazz.getMethods();
            while (rs.next()) {
                for (int i = 0; i < fs.length; i++) {
                    Field f = fs[i];
                    String colName = f.getName().substring(0, 1).toUpperCase()
                            + f.getName().substring(1);
                    colNames[i] = colName;
                    String rType = f.getType().getSimpleName();
                    rTypes[i] = rType;
                }

                Object object = (T) clazz.newInstance();
                for (int i = 0; i < colNames.length; i++) {
                    String colName = colNames[i];
                    String methodName = "set" + colName;
                    // 查找并调用对应的setter方法
                    for (Method m : methods) {
                        if (methodName.equals((m.getName()))) {
                            if ("int".equals(rTypes[i])
                                    || "Integer".equals(rTypes[i])) {
                                m.invoke(object, rs.getInt(colName));
                            } else if ("Date".equals(rTypes[i])) {
                                m.invoke(object, rs.getDate(colName));
                            } else if ("Timestamp".equals(rTypes[i])) {
                                m.invoke(object, rs.getTimestamp(colName));
                            } else {
                                m.invoke(object, rs.getObject(colName));
                            }
                            break;
                        }
                    }
                }
                list.add(object);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't close connection[" + conn + "].", e);
        }

    }

    /**
     * 以对象的形式保存或更新一个实例（执行事务）
     *
     * @param sql    要执行的sql语句
     * @param object 要保存或更新的实体对象
     * @param args   不需要赋值的列标组成的数组，例如sql语句
     *               "insert into tbl_user values(seq_user.nextval,?,?,?)"应为1
     *               又如1,3,5，表示第1/3/5列不需要赋值
     * @return 操作结果成功1或者失败 0
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public int saveEntityTran(String sql, Object object, int... args) {
        conn = getSingleThreadConnection(dataSource);
        try {
            ps = conn.prepareStatement(sql);
            Class c = object.getClass();
            Field[] fields = object.getClass().getDeclaredFields();
            int temp = 1;// 正赋值的？的下标，最大下标为args的长度
            int colIndex = 1;// SQL语句中的当前字段下标  
            int t = 0;// args数组的下标
            for (int j = 0; j < fields.length; j++) {
                Field field = fields[j];// 得到某个声明属性
                String methodName = "get"
                        + field.getName().substring(0, 1).toUpperCase()
                        + field.getName().substring(1);
                Method method = c.getMethod(methodName);
                String rType = field.getType().getSimpleName().toString();
                if (t < args.length && colIndex == args[t]) {
                    t++;
                } else if ("int".equals(rType) || "INTEGER".equals(rType)) {
                    ps.setInt(temp++, (Integer) method.invoke(object));
                } else {
                    ps.setObject(temp++, method.invoke(object));
                }
                colIndex++;// 更新索引下标  
            }
            int result = 0;
            result = ps.executeUpdate();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't close connection[" + conn + "].", e);
        }
    }

    /**
     * 执行可变参数的SQL语句，进行保存、删除或更新操作（执行事务），修改一行中某几个元素的值
     *
     * @param sql  要执行的sql语句的赋值顺序必须与args数组的顺序相关
     * @param args 要赋值的参数列表
     * @return
     */
    public int saveOrUpdateOrDeleteTran(String sql, Object... args) {
        conn = getSingleThreadConnection(dataSource);
        try {
            ps = conn.prepareStatement(sql);
            for (int j = 0; j < args.length; j++) {
                ps.setObject(j + 1, args[j]);
            }
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Couldn't close connection[" + conn + "].", e);
        }
    }

    /**
     * 从ConnectionHolder中获取连接，不使用事物的时候用
     */
    private Connection getConnection(DataSource datasource) {
        Connection connection = null;
        try {
            connection = connectionHolder.getConnection(dataSource);
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 从线程安全的SingleThreadConnectionHolder类中获取Connection对象，使用事物的时候要用
     */
    private Connection getSingleThreadConnection(DataSource datasource) {
        Connection connection = null;
        try {
            connection = SingleThreadConnectionHolder.getConnection(dataSource);
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 关闭连接
     */
    private void close(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        connectionHolder.removeConnection(dataSource);
    }


}  