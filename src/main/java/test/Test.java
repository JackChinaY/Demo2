package test;
import dao.BaseDAO_Sqlite;
import org.json.JSONArray;
import util.FileOperation;


public class Test {
	public static void main(String[] args) {
//        String sql = " SELECT name FROM internet";
//        BaseDAO_Sqlite a = new BaseDAO_Sqlite();
//		JSONArray array = new JSONArray();
//		System.out.println(a.getForJson(sql));
//		System.out.println(array.toString());
		FileOperation.copyFolder("D:\\database\\cbb418cc-8520-459f-ab02-ae3516388eb5","D:\\database\\abc");
		System.out.println("复制成功");
	}
}
