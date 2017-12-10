package dao;
/*
 * DAO层 常用的基本方法，封装在BaseDAO中
 */

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;

/**
 * DAO层 常用的基本方法，封装在BaseDAO中
 */
public class BaseDAO_Sqlite {
    private Connection conn = null;
    private PreparedStatement ps = null;//使用PreparedStatement便于向sql语句中动态赋值
    private ResultSet rs = null;

    //    private static String url = "jdbc:sqlite:E:/DB/Internet.db";//数据源URL，如果数据库不存在，那么它就会被创建
    //程序运行期间只加载一次
    static {
        try {
            Class.forName("org.sqlite.JDBC");//加载驱动
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询符合条件的记录数 或者求取最大值
     * 格式：SELECT count(*) AS count FROM table
     *
     * @param sql  要执行的sql语句
     * @param args 给sql语句中的？赋值的参数列表
     * @return 符合条件的记录数 0或1/2/3实际值  或者返回字段的最大值比如1000，如果没值就返回0
     */
    public int getCount(String sql, String url, Object... args) {
        try {
            //连接数据库
            conn = DriverManager.getConnection(url);
            //向sql语句插入参数
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            //执行查询
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
    public String getOneRecard(String sql, String url, Object... args) {
        try {
            conn = DriverManager.getConnection(url);
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
    public String getForJson(String sql, String url, Object... args) {
        try {
            conn = DriverManager.getConnection(url);
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
     * 执行可变参数的SQL语句，进行保存或删除或更新操作，参数为零散，,常用于更新、保存
     *
     * @param sql  要执行的sql语句，赋值顺序必须与args数组的顺序相同
     * @param args 要赋值的参数列表
     * @return 操作结果，正数是成功，是受影响的行数，0为失败
     */
    public int saveOrUpdateOrDelete(String sql, String url, Object... args) {
        try {
            conn = DriverManager.getConnection(url);
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
    }
}