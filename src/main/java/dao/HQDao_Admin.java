package dao;

import control.HQAction_Admin;
import entity.UploadFile;

import javax.sql.DataSource;
import java.sql.Timestamp;

public class HQDao_Admin extends BaseDAO {

	public HQDao_Admin(DataSource dataSource) {
		super(dataSource);
	}

	/**
	 * 方法序号：1_1 登录
	 */
	public String login(String username, String password) throws Exception {
		String sql = " SELECT Id FROM admin_table WHERE Username=? AND Password=?";
		return this.getOneRecard(sql, username, password);
	}

	/**
	 * 方法序号：1_2 新增机器编号
	 */
	public int addMachineId(String machineType, String machineId,
			Timestamp timestamp) throws Exception {
		String sql = "INSERT INTO machine_table (machineType,machineId,AddDateTime) VALUES (?,?,?)";
		return this.saveOrUpdateOrDelete(sql, machineType, machineId, timestamp);
	}

	/**
	 * 方法序号： 1_3 查询所有机器编号
	 * 
	 * @return json数组
	 */
	public String findAllMachine() throws Exception {
		String sql = " SELECT Id AS value1, machineType AS value2,machineId AS value3, AddDateTime AS value4 FROM machine_table ORDER BY machineType , machineId";
		return this.getForJson(sql);
	}

	/**
	 * 方法序号：1_4 验证机器编号是否存在 结果返回count的值
	 */
	public int verifymachineId(String machineType, String machineId)
			throws Exception {
		String sql = "SELECT count(machineId) AS count FROM machine_table WHERE machineType=? AND machineId=?";
		return this.getCount(sql, machineType, machineId);
	}

	/**
	 * 方法序号：1_4 删除机器编号
	 */
	public String deleteOneMachine(String machineType, String machineId)
			throws Exception {
		String sql = "DELETE FROM machine_table WHERE machineType=? AND machineId=?";
		return Integer.toString(this.saveOrUpdateOrDelete(sql, machineType, machineId));
	}

	/**
	 * 方法序号：1_5 修改机器编号
	 */
	public int updateOneMachine(String machineType, String machineId,
			Timestamp timestamp, String id) throws Exception {
		String sql = "UPDATE machine_table SET machineType=?, machineId=?, AddDateTime=? WHERE Id=?";
		return this.saveOrUpdateOrDelete(sql, machineType, machineId,
				timestamp, id);
	}

	/**
	 * 方法序号： 2_1 查询所有用户
	 * @return json数组
	 */
	public String findAllUsers() throws Exception {
		String sql = " SELECT Username AS value2, Email AS value3, Telephone AS value4, Address AS value5, MachineType AS value6, MachineId AS value7, AddDateTime AS value8 FROM user_table ORDER BY MachineId";
		return this.getForJson(sql);
	}

	/**
	 * 方法序号： 1_7 添加上传的文件信息到数据库
	 * 
	 * @return json数组
	 */
	public int addUploadFile(UploadFile uploadFile) throws Exception {
		String sql = "INSERT INTO file_table (Name,Size,Version,Savepath,Downloadpath,Datetime) VALUES (?,?,?,?,?,?)";
		return this.saveOrUpdateOrDelete(sql, uploadFile.getName(),
				uploadFile.getSize(), uploadFile.getVersion(),
				uploadFile.getSavepath(), uploadFile.getDownloadpath(), uploadFile.getDatetime());
	}

	/**
	 * 方法序号： 1_8 查询所有上传的文件
	 * @return json数组
	 */
	public String findAllFile() throws Exception {
		String sql = "SELECT Id AS value1, Name AS value2,Size AS value3, Version AS value4,Datetime AS value5, Downloadpath AS value6 FROM file_table ORDER BY Id";
		return this.getForJson(sql);
	}

	/**
	 * 方法序号：1_9 删除一个文件
	 */
	public int deleteOneFile(String id)
			throws Exception {
		String sql = "DELETE FROM file_table WHERE Id=?";
		return this.saveOrUpdateOrDelete(sql, id);
	}
	
	/**
	 * 方法序号：1_10 根据id查找文件的保存路径
	 */
	public String findFileSavePath(String id) throws Exception {
		String sql = "SELECT Savepath FROM file_table WHERE Id=?";
		return this.getOneRecard(sql, id);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
