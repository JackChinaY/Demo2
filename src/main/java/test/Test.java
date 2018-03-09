package test;

import dao.BaseDAO_Sqlite;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import util.FileOperation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

    public static void main(String[] args) throws IOException {
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
//        Test a = new Test();
//        System.out.println("输出：" + a.getCount("SELECT COUNT(*) as COUNTS from Goods_Info", "jdbc:sqlite:D:\\database\\548547fa-6fb1-4689-9d0c-4b66c3ca9eb8\\goodsDB.db"));
//        }
        File file = new File("E:\\2.xls");
        // 获得工作簿
        Workbook workbook = null;
        //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
        if (file.getName().endsWith("xls")) {
            //2003版
            workbook = new HSSFWorkbook(new FileInputStream(file));
        } else if (file.getName().endsWith("xlsx")) {
            //2007版
            workbook = new XSSFWorkbook(new FileInputStream(file));
        }
        //工作表
        Sheet sheet = null;
        // 遍历所有工作表
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {// 获取每个Sheet表
            sheet = workbook.getSheetAt(i);
            // getLastRowNum，获取最后一行的行标
            for (int j = 0; j < sheet.getLastRowNum() + 1; j++) {
                Row row = sheet.getRow(j);
                if (row != null) {
                    // getLastCellNum，是获取最后一个不为空的列是第几个
                    for (int k = 0; k < row.getLastCellNum(); k++) {
                        if (row.getCell(k) != null) { // getCell 获取单元格数据
                            System.out.print(row.getCell(k) + "\t");
                        } else {
                            System.out.print("\t");
                        }
                    }
                }
                System.out.println(""); // 读完一行后换行
            }
            System.out.println("读取sheet表：" + workbook.getSheetName(i) + " 完成");
        }
        workbook.close();

    }
}
