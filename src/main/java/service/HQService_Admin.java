package service;

import dao.HQDao_Admin;
import entity.UploadFile;

import java.sql.Timestamp;

/**
 * return返回值规定：0表示受影响行数为0，数据库出错；1表示受影响行数为1，
 * -1表示程序运行出错，服务器出错，-2表示该条记录在数据库中已存在，null也表示程序运行出错，服务器出错
 */
public class HQService_Admin extends BaseService {
	
	private HQDao_Admin hqDao_Admin = new HQDao_Admin(dataSource);

	/**
	 * 方法序号：1_1 登录
	 */
	public String login(String username, String password) {
		try {
			return this.hqDao_Admin.login(username, password);
		} catch (Exception e) {
			System.out.println("1_1登录时出错！");
			e.printStackTrace();
			return null;//程序运行出错，服务器出错
		}
	}
	/**
	 * 方法序号：1_2  新增机器编号
	 */
	public int addMachineId(String machineType, String machineId,Timestamp timestamp) {
		try {
			//验证待添加的机器编号是否在数据库中已存在
			if(this.hqDao_Admin.verifymachineId(machineType, machineId)== 0){//数据库中不存在该记录
				return this.hqDao_Admin.addMachineId(machineType, machineId,timestamp);
			}else {
				return -2;//数据库中已存在该记录
			}
			
		} catch (Exception e) {
			System.out.println("1_2 新增机器编号出错！");
			e.printStackTrace();
			return -1;//程序运行出错，服务器出错
		}
	}
	/**
	 * 方法序号：1_3 查询所有机器编号
	 * @return json数组字符串
	 */
	public String findAllMachine() {
		try {
			return this.hqDao_Admin.findAllMachine();
		} catch (Exception e) {
			System.out.println("1_3查询所有机器编号时出错！");
			e.printStackTrace();
			return "-1";//程序运行出错，服务器出错
		}
	}
	/**
	 * 方法序号：1_4  删除机器编号
	 */
	public String deleteOneMachine(String machineType, String machineId) {
		try {
			return this.hqDao_Admin.deleteOneMachine(machineType, machineId);
		} catch (Exception e) {
			System.out.println("1_4  删除机器编号时出错！");
			e.printStackTrace();
			return "-1";//程序运行出错，服务器出错
		}
	}
	/**
	 * 方法序号：1_5  修改机器编号
	 */
	public int updateOneMachine(String machineType, String machineId,Timestamp timestamp, String id) {
		try {
			//验证待修改的机器编号是否在数据库中已存在
			if(this.hqDao_Admin.verifymachineId(machineType, machineId)== 0){ //表中不存在此编号时
				return this.hqDao_Admin.updateOneMachine(machineType, machineId, timestamp, id);
			}else {
				return -2;//表中已有此数据
			}
			
		} catch (Exception e) {
			System.out.println("1_5  修改机器编号时出错！");
			e.printStackTrace();
			return -1;//程序运行出错，服务器出错
		}
	}
	/**
	 * 方法序号：2_1 查询所有用户
	 * @return json数组字符串
	 */
	public String findAllUsers() {
		try {
			return this.hqDao_Admin.findAllUsers();
		} catch (Exception e) {
			System.out.println("2_1 查询所有用户时出错！");
			e.printStackTrace();
			return null;//程序运行出错，服务器出错
		}
	}
	/**
	 * 方法序号：1_7 添加上传的文件信息到数据库
	 */
	public int addUploadFile(UploadFile uploadFile) {
		try {
			//验证待添加的机器编号是否在数据库中已存在
			if(true){//数据库中不存在该记录
				return this.hqDao_Admin.addUploadFile(uploadFile);
			}else {
				return -2;//数据库中已存在该记录
			}
			
		} catch (Exception e) {
			System.out.println("1_2 新增机器编号出错！");
			e.printStackTrace();
			return -1;//程序运行出错，服务器出错
		}
	}
	/**
	 * 方法序号：1_8 查询所有上传的文件
	 * @return json数组字符串
	 */
	public String findAllFile() {
		try {
			return this.hqDao_Admin.findAllFile();
		} catch (Exception e) {
			System.out.println("2_1 查询所有用户时出错！");
			e.printStackTrace();
			return null;//程序运行出错，服务器出错
		}
	}
	
	/** 方法序号：1_9 删除一个文件
	 */
	public int deleteOneFile(String id) {
		try {
			return this.hqDao_Admin.deleteOneFile(id);
		} catch (Exception e) {
			System.out.println("1_9 删除一个文件时出错！");
			e.printStackTrace();
			return -1;//程序运行出错，服务器出错
		}
	}
	/**
	 * 方法序号：1_10 根据id查找文件的保存路径
	 */
	public String findFileSavePath(String id) {
		try {
			return this.hqDao_Admin.findFileSavePath(id);
		} catch (Exception e) {
			System.out.println("1_10 根据id查找文件的保存路径时出错！");
			e.printStackTrace();
			return null;//程序运行出错，服务器出错
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
