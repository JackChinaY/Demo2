package service;

import dao.HQDao;
import entity.User;

/**
 * return返回值规定：0表示受影响行数为0，数据库出错；1表示受影响行数为1，
 * -1表示程序运行出错，服务器出错，-2表示该条记录在数据库中已存在，null也表示程序运行出错，服务器出错
 */
public class HQService extends BaseService {

    private HQDao hqDao = new HQDao(dataSource);

    /**
     * 方法序号：1_1 登录
     */
    public String login(String username, String password) {
        try {
            return this.hqDao.login(username, password);
        } catch (Exception e) {
            System.out.println("1_1登录时出错！");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 方法序号： 1_2验证用户名是否存在
     */
    public String verifyUsername(String id) {
        try {
            return this.hqDao.verifyUsername(id);
        } catch (Exception e) {
            System.out.println("1_2验证用户名是否存在时出错！");
            e.printStackTrace();
            return "-1";
        }
    }

    /**
     * 方法序号：1_3 注册
     */
    public String register(User user) {
        try {
            return this.hqDao.register(user);// 添加
        } catch (Exception e) {
            System.out.println("1_3注册时出错！");
            e.printStackTrace();
            return "-1";
        }
    }

    /**
     * 方法序号： 1_4 验证该找回密码的用户名是否存在
     */
    public int verifyUsernameIsExist(User user) {
        try {
            return this.hqDao.verifyUsernameIsExist(user);
        } catch (Exception e) {
            System.out.println("1_4验证该找回密码的用户名是否存在时出错！");
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 方法序号：1_5重置密码
     */
    public boolean resetPassord(User user) {
        try {
            return this.hqDao.resetPassord(user);
        } catch (Exception e) {
            System.out.println("1_5重置密码时出错！");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 方法序号：1_6  验证验证机器是否为正品
     */
    public String verifyMachine(String machineType, String machineId) {
        try {
            return this.hqDao.verifyMachine(machineType, machineId);
        } catch (Exception e) {
            System.out.println("1_6  验证验证机器是否为正品时出错！");
            e.printStackTrace();
            return "-1";
        }
    }

    /**
     * 方法序号：1_7  验证验证机器是否是否已被注册
     */
    public String verifyMachineIsExist(String machineType, String machineId) {
        try {
            return this.hqDao.verifyMachineIsExist(machineType, machineId);
        } catch (Exception e) {
            System.out.println("1_7  验证验证机器是否是否已被注册时出错！");
            e.printStackTrace();
            return "-1";
        }
    }

    /**
     * 方法序号：5_1 查询用户个人信息
     */
    public String findUserInfo(String id) {
        try {
            return this.hqDao.findUserInfo(id);
        } catch (Exception e) {
            System.out.println("5_1 查询用户个人信息时出错！");
            e.printStackTrace();
            return "-1";//程序运行出错，服务器出错
        }
    }

    /**
     * 方法序号：5_2 保存修改后用户个人信息
     */
    public String saveUserInfo(User user) {
        try {
            return this.hqDao.saveUserInfo(user);
        } catch (Exception e) {
            System.out.println("5_2 保存修改后用户个人信息时出错！");
            e.printStackTrace();
            return "-1";//程序运行出错，服务器出错
        }
    }

    /**
     * 方法序号：5_3 保存修改后用户密码
     */
    public String saveNewPassword(User user) {
        try {
            return this.hqDao.saveNewPassword(user);
        } catch (Exception e) {
            System.out.println("5_3 保存修改后用户密码时出错！");
            e.printStackTrace();
            return "-1";//程序运行出错，服务器出错
        }
    }
}
