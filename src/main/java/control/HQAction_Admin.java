package control;

import entity.UploadFile;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import service.HQService_Admin;
import util.FileOperation;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * return返回值规定：0表示受影响行数为0，数据库出错；1表示受影响行数为1，
 * -1表示程序运行出错，服务器出错，-2表示该条记录在数据库中已存在，null也表示程序运行出错，服务器出错
 */
public class HQAction_Admin extends BaseAction {
    /**
     * 序列化
     */
//	private static final long serialVersionUID = 5748890796885464703L;
    private HQService_Admin hqService_Admin = new HQService_Admin();

    /**
     * 方法序号：1_1 登录
     *
     * @throws NoSuchAlgorithmException
     */
    public void login() throws IOException, JSONException {
        String username = this.getRequest().getParameter("username");
        String password = this.getRequest().getParameter("password");// 对密码进行加密
        // String password =
        // DigestUtils.md5Hex(this.getRequest().getParameter("password"));//对密码进行加密
        // System.out.println(this.getSession().getAttribute("username"));
        String result = hqService_Admin.login(username, password);
        String flag;
        if (result != null) {
            flag = "1";// 登录成功
            this.getSession().setAttribute("userId", result);// 在session中保存用户的id
            this.getSession().setAttribute("userFlag", "admin");// 在session中保存用户的身份是管理员
        } else {
            flag = "0";// 登录失败
        }
        // System.out.println(this.getSession().getAttribute("id"));
        JSONObject jo = new JSONObject();
        jo.put("jsonObject", flag);
        jo.put("username", username);
        jo.put("userFlag", "1");
        this.getResponse().setContentType("text/html;charset=UTF-8");// 设置响应数据类型
        this.getResponse().getWriter().print(jo);// 向前台发送json数据
    }

    /**
     * 方法序号：1_1_1 注销
     */
    public void loginOut() throws IOException, JSONException {
        HttpSession session = this.getRequest().getSession(false);// 防止创建Session
        if (session == null) {
            this.getResponse().setContentType("text/html;charset=UTF-8");// 设置响应数据类型
            this.getResponse().getWriter().print("1");// 向前台发送json数据
        } else {
            session.removeAttribute("userId");
            session.removeAttribute("userFlag");
            session.invalidate();//清除session
            this.getResponse().setContentType("text/html;charset=UTF-8");// 设置响应数据类型
            this.getResponse().getWriter().print("1");// 向前台发送json数据
        }
    }

    /**
     * 方法序号：1_2 新增机器编号
     */
    public void addMachineId() throws IOException, JSONException {
        String machineType = this.getRequest().getParameter("machineType");
        String machineId = this.getRequest().getParameter("machineId");
        // System.out.println(machineType+machineId);
        int result = hqService_Admin.addMachineId(machineType, machineId,
                new Timestamp(System.currentTimeMillis()));// 1表示插入成功，0表示插入失败,-1表示已存在
        // System.out.println(result);
        JSONObject jo = new JSONObject();
        jo.put("jsonObject", result);
        this.getResponse().setContentType("text/html;charset=UTF-8");// 设置响应数据类型
        this.getResponse().getWriter().print(jo);// 向前台发送json数据
    }

    /**
     * 方法序号：1_3 查询所有机器编号
     */
    public void findAllMachine() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();
        }
        //登录未超时
        else {
            String result = hqService_Admin.findAllMachine();
            returnJsonObject(result);//可能的返回值：-1，[],json数组字符串
        }
    }

    /**
     * 方法序号：1_4 删除机器编号
     */
    public void deleteOneMachine() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();
        }
        //登录未超时
        else {
            String machineType = this.getRequest().getParameter("machineType");
            String machineId = this.getRequest().getParameter("machineId");
            String result = hqService_Admin.deleteOneMachine(machineType, machineId);// >=1表示插入成功，0表示插入失败
            returnJsonObject(result);//可能的返回值：-1，0,1
        }
    }

    /**
     * 方法序号：1_5 修改机器编号
     */
    public void updateOneMachine() throws IOException, JSONException {
        String id = this.getRequest().getParameter("Id");
        String machineType = this.getRequest().getParameter("machineType");
        String machineId = this.getRequest().getParameter("machineId");
        // System.out.println(machineType+machineId);
        int result = hqService_Admin.updateOneMachine(machineType, machineId,
                new Timestamp(System.currentTimeMillis()), id);// 1表示插入成功，0表示插入失败,-1表示已存在
        // System.out.println(result);
        JSONObject jo = new JSONObject();
        jo.put("jsonObject", result);
        this.getResponse().setContentType("text/html;charset=UTF-8");// 设置响应数据类型
        this.getResponse().getWriter().print(jo);// 向前台发送json数据
    }

    /**
     * 方法序号：2_1 查询所有用户
     */
    public void findAllUsers() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();
        }
        //登录未超时
        else {
            String result = hqService_Admin.findAllUsers();
            returnJsonObject(result);//可能的返回值：-1，[],json数组字符串
        }
    }

    /**
     * 方法序号：2_2 根据用户的id查询用户名
     */
    public void findUsername() throws IOException, JSONException {
        String id = this.getRequest().getParameter("value1");
        String result = hqService_Admin.findUsername(id);
        String flag;
        if (result != null) {
            flag = "1";//登录成功
//            this.getSession().setAttribute("userId", result);//在session中保存用户的id
        } else {
            flag = "0";//登录失败
        }
//		System.out.println(this.getSession().getAttribute("userId"));
        JSONObject jo = new JSONObject();
        jo.put("jsonObject", flag);
        jo.put("username", result);
        this.getResponse().setContentType("text/html;charset=UTF-8");// 设置响应数据类型
        this.getResponse().getWriter().print(jo);// 向前台发送json数据
    }

    /**
     * 方法序号：1_7 文件 上传
     *
     * @throws Exception
     */
    public void uploaderFile() throws Exception {
        // String result = hqService_Admin.findAllUser();
//		System.out.println("开始上传");
        //文件保存路径 比如：D:\programfile\Tomcat7.0\webapps\CashMachine\download\
        String savePath = this.getServletContext().getRealPath("") + "\\download\\";
        //请求路径 比如：http://localhost:8080/CashMachine/ActionAdmin_uploaderFile.action
        String URL = this.getRequest().getRequestURL().toString();//带ip地址的路径
        //下载地址 比如：http://localhost:8080/CashMachine/download/jquery.js
        String downloadPath = null;
//		String saveFileName=null;
//		System.out.println(savePath);
        File tempPathFile = new File(savePath);
        //1、创建一个DiskFileItemFactory工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //设置工厂的缓冲区的大小，当上传的文件大小超过缓冲区的大小时，就会生成一个临时文件存放到指定的临时目录当中。
        factory.setSizeThreshold(1024 * 1024); // 设置缓冲区大小，这里是1Mb,设置缓冲区的大小为100KB，如果不指定，那么缓冲区的大小默认是10KB
        factory.setRepository(tempPathFile);//设置上传时生成的临时文件的保存目录
        //2、创建一个文件上传解析器
        ServletFileUpload upload = new ServletFileUpload(factory);
        //设置上传单个文件的大小的最大值，目前是设置为1024*1024字节，也就是1MB
        upload.setFileSizeMax(1024 * 1024);
        //设置上传文件总量的最大值，最大值=同时上传的多个文件的大小的最大值的和，目前设置为10MB
        upload.setSizeMax(1024 * 1024 * 10);
        //解决上传文件名的中文乱码
        upload.setHeaderEncoding("utf-8");// 设置编码为utf-8
        //监听文件上传进度
//        upload.setProgressListener(new ProgressListener(){
//            public void update(long pBytesRead, long pContentLength, int arg2) {
//                System.out.println("文件大小为：" + pContentLength + ",当前已处理：" + pBytesRead);
//            }
//        });
        //3、判断提交上来的数据是否是上传表单的数据
//        if(!ServletFileUpload.isMultipartContent(this.getRequest())){
//            System.out.println("提交上来的数据是传统表单的数据");
//            return;
//        }
        //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
        List<FileItem> fileItemList = upload.parseRequest(this.getRequest());// 得到所有的文件
        Iterator<FileItem> iterator = fileItemList.iterator();// 迭代器,搜索前端发送过来的文件
        try {
            while (iterator.hasNext()) {
                FileItem fileItem = (FileItem) iterator.next();
                //如果fileitem是普通输入项的数据
                if (fileItem.isFormField()) {
//					String name = fileItem.getFieldName();
//					//解决普通输入项的数据的中文乱码问题
//	                String value = fileItem.getString("UTF-8");
//	                System.out.println("普通输入项:"+name + "=" + value);
                }
                //如果fileitem中封装的是上传文件
                else {
                    String fileName = fileItem.getName();
                    //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
                    //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                    fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);//如：1.txt
//					saveFileName=fileName;
//					System.out.println("上传过来的文件的文件名："+fileName);
                    if (fileName != null) {
//						File fullFile = new File(new String(fileItem.getName().getBytes(),
//								"utf-8")); // 解决文件名乱码问题
                        File savedFile = new File(savePath, fileName);
                        fileItem.write(savedFile);//保存
                    }
                    UploadFile uploadFile = new UploadFile();
                    uploadFile.setName(fileName);
                    uploadFile.setSize(fileItem.getSize());
                    uploadFile.setVersion(new SimpleDateFormat("yyyyMMdd").format(new Date()));//根据当天日期产生版本号
                    downloadPath = URL.substring(0, URL.lastIndexOf("/")) + "/download/" + fileName;
                    uploadFile.setSavepath(savePath + fileName);
                    uploadFile.setDownloadpath(downloadPath);
                    uploadFile.setDatetime(new Timestamp(System.currentTimeMillis()));//插入的时间
                    int result = hqService_Admin.addUploadFile(uploadFile);// 1表示插入成功，0表示插入失败,-1表示已存在
                }
            }
//			System.out.println("文件上传成功！");
            JSONObject jo = new JSONObject();
            jo.put("result", "ok");
            jo.put("id", "10001");
            jo.put("url", downloadPath);
//			 System.out.println(downloadPath);
            this.getResponse().setContentType("text/html;charset=UTF-8");//设置响应数据类型
            this.getResponse().getWriter().print(jo);// 向前台发送json数据
        } catch (Exception e) {
            System.out.println("上传失败！");
            e.printStackTrace();
            JSONObject jo = new JSONObject();
            jo.put("result", "failed");
            jo.put("message", e.getMessage());

            this.getResponse().setContentType("text/html;charset=UTF-8");//
//			 设置响应数据类型
            this.getResponse().getWriter().print(jo);// 向前台发送json数据
        }


    }

    /**
     * 方法序号：1_8 查询所有上传的文件
     */
    public void findAllFile() throws IOException, JSONException {
        String result = hqService_Admin.findAllFile();
        // System.out.println(result);
        JSONArray allJsonArray = new JSONArray(result);
        if (result.equals(null)) {
            result = "0";// 失败
        }
        JSONObject jo = new JSONObject();
        jo.put("allJsonArray", allJsonArray);
        this.getResponse().setContentType("text/html;charset=UTF-8");// 设置响应数据类型
        this.getResponse().getWriter().print(jo);// 向前台发送json数据
    }

    /**
     * 方法序号：1_9 删除一个文件
     */
    public void deleteOneFile() throws IOException, JSONException {
        String id = this.getRequest().getParameter("fileId");
        //根据id查找文件的保存路径
        String fileSavePath = hqService_Admin.findFileSavePath(id);
        //删除硬盘上的文件
        FileOperation fileOperation = new FileOperation();
        int result = 0;
        if (fileOperation.delFile(fileSavePath)) {
            //删除数据库中该条记录
            result = hqService_Admin.deleteOneFile(id);// >=1表示成功，0 表示失败
        }
        // System.out.println(result);
        if (result > 0) {
            result = 1;
        }
        JSONObject jo = new JSONObject();
        jo.put("jsonObject", result);
        this.getResponse().setContentType("text/html;charset=UTF-8");// 设置响应数据类型
        this.getResponse().getWriter().print(jo);// 向前台发送json数据
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

}
