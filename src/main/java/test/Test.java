package test;

import dao.BaseDAO_Sqlite;
import org.json.JSONArray;
import util.FileOperation;

import java.sql.*;


public class Test {
    private Connection conn = null;
    private PreparedStatement ps = null;//使用PreparedStatement便于向sql语句中动态赋值
    private ResultSet rs = null;

    public int getCount(String sql, String url, Object... args) {
        try {
            Class.forName("org.sqlite.JDBC");//加载驱动
            //连接数据库
            conn = DriverManager.getConnection(url);
            //取消自动提交
            conn.setAutoCommit(false);
            //向sql语句插入参数
            ps = conn.prepareStatement("UPDATE Goods_Info SET Name=2 WHERE  id=2");
//            ps.execute();
            ps = conn.prepareStatement("UPDATE Goods_Info SET Name=3 WHERE  id=3");
//            ps.execute();
//            ps = conn.
//            ps = conn.prepareStatement(sql);
//            ps = conn.
//            for (int i = 0; i < args.length; i++) {
//                ps.setObject(i + 1, args[i]);
//            }

            conn.commit();
            //执行查询
//            rs = ps.executeQuery();
//            if (rs.next()) {  //若为数据库null值就不执行此代码，而是返回0L
//                return rs.getInt(1);  //返回count（*）的值
//            }
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            close(conn, ps, rs);
        }
        return 0;
    }

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

    public static void main(String[] args) {
//        String sql = " SELECT name FROM internet";
//        BaseDAO_Sqlite a = new BaseDAO_Sqlite();
//		JSONArray array = new JSONArray();
//		System.out.println(a.getForJson(sql));
//		System.out.println(array.toString());
//		FileOperation.copyFolder("D:\\database\\cbb418cc-8520-459f-ab02-ae3516388eb5","D:\\database\\abc");
//		System.out.println(new Timestamp(System.currentTimeMillis()));
//        int a = 1;
//        if (a == 1) {
//            System.out.println("1111");
//        } else if (a == 1) {
        Test a = new Test();
        System.out.println("输出：" + a.getCount("SELECT COUNT(*) as COUNTS from Goods_Info", "jdbc:sqlite:D:\\database\\548547fa-6fb1-4689-9d0c-4b66c3ca9eb8\\goodsDB.db"));
//        }


    }
}
