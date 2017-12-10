package control;

import entity.*;
import freemarker.log.Logger;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import service.HQService;
import util.FileOperation;
import util.SendEmail;
import util.UtilsAll;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * 在intellij快捷键：ctrl+/:注释及反注释；alt+L是格式化代码，ctrl+X删除行，sout、psvm是全拼，
 * ctrl+ +/-:某个函数打开或折叠;ctrl+shift+  +/-:某个函数打开或折叠；每次修改完页面ctrl+F9进行重新编译
 * return返回值规定：
 * 0表示受影响行数为0 数据库出错;
 * 1表示受影响行数为1或者是成功的标志;
 * -1表示程序运行出错，服务器出错;
 * -2表示该条记录在数据库中已存在;
 * null也表示程序运行出错，服务器出错
 * -3表示用户登录超时，需要重新登录;
 * 向前台值返回数字：如0,1,-1,-2等
 */
public class HQAction extends BaseAction {
    /**
     * 序列化
     */
//    private static final long serialVersionUID = 415712263988003225L;
    private HQService hqService = new HQService();
    //    private String currentUserId = "cbb418cc-8520-459f-ab02-ae3516388eb5";  //当前用户名Id，软件发布的时候把该字符内容删除掉
//    private  Logger logger = Logger.getLogger(HQAction.class);

    public HQAction() {
//        System.out.println("111");
    }

    /**
     * 方法序号：1_1 登录
     *
     * @throws NoSuchAlgorithmException
     */
    public void login() throws IOException, JSONException {
        String username = this.getRequest().getParameter("username");
        String password = DigestUtils.md5Hex(this.getRequest().getParameter("password"));//对密码进行加密
//		System.out.println("用户id1："+ this.getSession().getAttribute("userId"));
        String result = hqService.login(username, password);
        String flag;
        if (result != null) {
            flag = "1";//登录成功
            this.getSession().setAttribute("userId", result);//在session中保存用户的id
        } else {
            flag = "0";//登录失败
        }
//		System.out.println(this.getSession().getAttribute("userId"));
//		System.out.println("用户id2："+ this.getSession().getAttribute("userId"));
        JSONObject jo = new JSONObject();
        jo.put("jsonObject", flag);
        jo.put("username", username);
        this.getResponse().setContentType("text/html;charset=UTF-8");// 设置响应数据类型
        this.getResponse().getWriter().print(jo);// 向前台发送json数据
    }

    /**
     * 方法序号：1_2验证用户名是否存在
     */
    public void verifyUsername() throws IOException, JSONException {
        String id = this.getRequest().getParameter("username");
        String result = hqService.verifyUsername(id);// 0表示0条记录，1表示有1条记录
        returnJsonObject(result);//可能的返回值：-1，0,1
    }

    /**
     * 方法序号：1_3 注册
     */
    public void register() throws IOException, JSONException {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(this.getRequest().getParameter("username"));
        user.setPassword(DigestUtils.md5Hex(this.getRequest().getParameter("password")));//对密码进行加密
        user.setEmail(this.getRequest().getParameter("email"));
        user.setTel(this.getRequest().getParameter("tel"));
        user.setAddress(this.getRequest().getParameter("address"));
        user.setMachineType(this.getRequest().getParameter("machineType"));
        user.setMachineId(this.getRequest().getParameter("machineId"));
        user.setTimestamp(new Timestamp(System.currentTimeMillis()));
        String result = hqService.register(user);
        //注册成功后创建数据该用户的数据库
        if (result.equals("1")) {
            FileOperation.copyFolder(this.getServletContext().getRealPath("") + "\\database", "D:\\database\\" + user.getId());
        }
        returnJsonObject(result);//可能的返回值：-1，0,1
    }

    /**
     * 方法序号：1_4 找回密码
     */
    public void callbackPassword() throws IOException, JSONException {
        User user = new User();
        user.setUsername(this.getRequest().getParameter("username"));
        user.setEmail(this.getRequest().getParameter("email"));
        user.setTel(this.getRequest().getParameter("tel"));
        user.setMachineType(this.getRequest().getParameter("machineType"));
        user.setMachineId(this.getRequest().getParameter("machineId"));
//		System.out.println(user.toString());
        int result = hqService.verifyUsernameIsExist(user);
        String flag;
        if (result == 0) {//该用户不存在
            flag = "The user account does not exist or other information is incorrectly ！";//该用户账号不存在或其他信息填写不正确
        } else if (result == 1) {//该用户存在 TODO
            String newPassword = UtilsAll.getRandomString(8);//随机生成8位新密码
            user.setPassword(DigestUtils.md5Hex(newPassword));
            user.setNewPassword(newPassword);//这里用NewPassword储存用户的新密码
            try {
                SendEmail.sendOneEmail(user);//发送带新密码的邮件给用户
            } catch (Exception e) {
                e.printStackTrace();
            }
            boolean resultOfReset = hqService.resetPassord(user);
//            System.out.println(newPassword);
            if (resultOfReset) {
                //找回成功
                flag = "Your password has been reset successfully. Please go to your registered mailbox：" + user.getEmail() + " to view the new password！";
            } else {
                //找回失败
                flag = "Password reset failed！";
            }
        } else {
            flag = "There are multiple users in this account！";//该账号存在多个用户
        }
        JSONObject jo = new JSONObject();
        jo.put("jsonObject", flag);
        this.getResponse().setContentType("text/html;charset=UTF-8");// 设置响应数据类型
        this.getResponse().getWriter().print(jo);// 向前台发送json数据
    }

    /**
     * 方法序号：1_5 注销
     */
    public void loginOut() throws IOException, JSONException {
        HttpSession session = this.getRequest().getSession(false);//防止创建Session
        if (session == null) {
            this.getResponse().setContentType("text/html;charset=UTF-8");// 设置响应数据类型
            this.getResponse().getWriter().print("1");// 向前台发送json数据
        } else {
            session.removeAttribute("userId");
            session.invalidate();//清除session
            this.getResponse().setContentType("text/html;charset=UTF-8");// 设置响应数据类型
            this.getResponse().getWriter().print("1");// 向前台发送json数据
        }
    }

    /**
     * 方法序号：1_6  验证验证机器是否为正品
     */
    public void verifyMachine() throws IOException, JSONException {
        String machineType = this.getRequest().getParameter("machineType");
        String machineId = this.getRequest().getParameter("machineId");
        String result = hqService.verifyMachine(machineType, machineId);//1表示正品。0表示赝品
        returnJsonObject(result);//可能的返回值：-1，0,1
    }

    /**
     * 方法序号：5_1 查询用户个人信息
     */
    public void findUserInfo() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();
        }
        //登录未超时
        else {
            String userId = this.getSession().getAttribute("userId").toString();//获取用户UserId
            String result = hqService.findUserInfo(userId);
            returnJsonObject(result);//可能的返回值：-1，[],json数组字符串
        }
    }

    /**
     * 方法序号：5_2 保存修改后用户个人信息
     */
    public void saveUserInfo() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();
        }
        //登录未超时
        else {
            User user = new User();
            user.setEmail(this.getRequest().getParameter("email"));
            user.setTel(this.getRequest().getParameter("tel"));
            user.setAddress(this.getRequest().getParameter("address"));
            user.setId(this.getSession().getAttribute("userId").toString());//获取用户UserId
            String result = hqService.saveUserInfo(user);
            returnJsonObject(result);//可能的返回值：-1，0,1
        }
    }

    /**
     * 方法序号：5_3 保存修改后用户密码
     */
    public void saveNewPassword() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();
        }
        //登录未超时
        else {
            User user = new User();
            user.setPassword(DigestUtils.md5Hex(this.getRequest().getParameter("value1")));
            user.setNewPassword(DigestUtils.md5Hex(this.getRequest().getParameter("value2")));
            user.setId(this.getSession().getAttribute("userId").toString());//获取用户UserId
            String result = hqService.saveNewPassword(user);
            returnJsonObject(result);//可能的返回值：-1，0,1
        }
    }

    /**
     * 如果用户登录超时，则需要重新登录
     */
    public void connectionTimeOut() throws IOException, JSONException {
        JSONObject jo = new JSONObject();
        jo.put("jsonObject", -3);//-3为登录超时
        this.getResponse().setContentType("text/html;charset=UTF-8");// 设置响应数据类型
        this.getResponse().getWriter().print(jo);// 向前台发送json数据
    }

    /**
     * 统一向前台返回数据，返回的是jsonObject
     */
    public void returnJsonObject(String result) throws IOException, JSONException {
        JSONObject jo = new JSONObject();
        jo.put("jsonObject", result);
        this.getResponse().setContentType("text/html;charset=UTF-8");// 设置响应数据类型
        this.getResponse().getWriter().print(jo);// 向前台发送json数据
    }

//    /**后续功能，暂此不删
//     * 方法序号：3_1_1查询部类日报表
//     */
//    public String findDayDep() throws IOException, JSONException {
//        String result = hqService.findDayDep();
////		System.out.println(result);
//        JSONArray allJsonArray = new JSONArray(result);
//        if (result.equals(null)) {
//            result = "0";//失败
//        }
//        JSONObject jo = new JSONObject();
//        jo.put("allJsonArray", allJsonArray);
////		System.out.println(jo.toString());
//        this.getResponse().setContentType("text/html;charset=UTF-8");// 设置响应数据类型
//        this.getResponse().getWriter().print(jo);// 向前台发送json数据
//        return null;
//    }
//
//    /**
//     * 方法序号：3_1_2查询单品日报表
//     */
//    public String findDayPlu() throws IOException, JSONException {
//        String result = hqService.findDayPlu();
//        System.out.println(result);
//        JSONArray allJsonArray = new JSONArray(result);
//        if (result.equals(null)) {
//            result = "0";//失败
//        }
//        JSONObject jo = new JSONObject();
//        jo.put("allJsonArray", allJsonArray);
//        this.getResponse().setContentType("text/html;charset=UTF-8");// 设置响应数据类型
//        this.getResponse().getWriter().print(jo);// 向前台发送json数据
//        return null;
//    }
//
//    /**
//     * 方法序号：3_1_3查询时段日报表
//     */
//    public String findDayTime() throws IOException, JSONException {
//        String result = hqService.findDayTime();
//        System.out.println(result);
//        JSONArray allJsonArray = new JSONArray(result);
//        if (result.equals(null)) {
//            result = "0";//失败
//        }
//        JSONObject jo = new JSONObject();
//        jo.put("allJsonArray", allJsonArray);
//        this.getResponse().setContentType("text/html;charset=UTF-8");// 设置响应数据类型
//        this.getResponse().getWriter().print(jo);// 向前台发送json数据
//        return null;
//    }
//
//    /**
//     * 方法序号：3_1_4查询交易日报表
//     */
//    public String findDayTrade() throws IOException, JSONException {
//        String result = hqService.findDayTrade();
//        System.out.println(result);
//        JSONArray allJsonArray = new JSONArray(result);
//        if (result.equals(null)) {
//            result = "0";//失败
//        }
//        JSONObject jo = new JSONObject();
//        jo.put("allJsonArray", allJsonArray);
//        this.getResponse().setContentType("text/html;charset=UTF-8");// 设置响应数据类型
//        this.getResponse().getWriter().print(jo);// 向前台发送json数据
//        return null;
//    }
//    /**
//     * 方法序号：3_1_5查询单品日报表
//     */
////	public String findDayDep() throws IOException, JSONException {
////		String result = hqService.findDayDep();
////		System.out.println(result);
////		JSONArray allJsonArray =new JSONArray(result);
////		if (result.equals(null)) {
////			result = "0";//失败
////		}
////		JSONObject jo = new JSONObject();
////		jo.put("allJsonArray", allJsonArray);
////		this.getResponse().setContentType("text/html;charset=UTF-8");// 设置响应数据类型
////		this.getResponse().getWriter().print(jo);// 向前台发送json数据
////		return null;
////	}

}
