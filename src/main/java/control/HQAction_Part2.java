package control;

import entity.*;
//import jxl.Sheet;
//import jxl.Workbook;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import service.HQService;
import service.HQService_Part2;
import util.SendEmail;
import util.UtilsAll;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.math.BigDecimal;

/**
 * 在intellij快捷键：ctrl+/:注释及反注释；alt+L是格式化代码，ctrl+X删除行，sout、psvm是全拼
 * return返回值规定：
 * 0表示受影响行数为0 数据库出错;
 * 1表示受影响行数为1或者是成功的标志;
 * -1表示程序运行出错，服务器出错;
 * -2表示该条记录在数据库中已存在;
 * null也表示程序运行出错，服务器出错
 * -3表示用户登录超时，需要重新登录;
 * 向前台值返回数字：如0,1,-1,-2等
 */
public class HQAction_Part2 extends BaseAction {
    /**
     * 序列化
     */
//    private static final long serialVersionUID = 415712263988003225L;
    private HQService_Part2 hqService_part2 = new HQService_Part2();
    //    private String currentUserId = "cbb418cc-8520-459f-ab02-ae3516388eb5";  //当前用户名Id，软件发布的时候把该字符内容删除掉
    private String databaseUrl = "jdbc:sqlite:D:/database/";  //sqlite数据库基础路径
    private String systemDB = "/systemDB.db";  //连接的是systemDB.db
    private String goodsDB = "/goodsDB.db";  //连接的是goodsDB.db
    private String programmingDB = "/programmingDB.db";  //连接的是programmingDB.db

    /**
     * 方法序号：4_1 查询所有商品
     */
    public void findAllGoods() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();
        }
        //登录未超时
        else {
            String userId = this.getSession().getAttribute("userId").toString();//获取用户UserId
            Page page = new Page();
            page.setPageIndex(Integer.parseInt(this.getRequest().getParameter("pageIndex")));//当前页码
            page.setPageSize(Integer.parseInt(this.getRequest().getParameter("pageSize")));//分页大小
            String result = hqService_part2.findAllGoods(databaseUrl + userId + goodsDB, databaseUrl + userId + systemDB, page);
            returnJsonObject(result);//可能的返回值：-1，[],json数组字符串
        }
    }

    /**
     * 方法序号：4_2 查询商品总记录数
     */
    public void getGoodsCount() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();
        }
        //登录未超时
        else {
            String userId = this.getSession().getAttribute("userId").toString();//获取用户userId
            String result = hqService_part2.getGoodsCount(databaseUrl + userId + goodsDB);// 0表示0条记录，1表示有1条记录
            returnJsonObject(result);//可能的返回值：-1，0,5
        }
    }

    /**
     * 方法序号：4_3 查询商品最大编号
     */
    public void getGoodsMaxNumber() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();
        }
        //登录未超时
        else {
            String userId = this.getSession().getAttribute("userId").toString();//获取用户userId
            String result = hqService_part2.getGoodsMaxNumber(databaseUrl + userId + goodsDB);// 0表示0条记录，1表示有1条记录
            if (result == null) {
                result = "null";
            }
            returnJsonObject(result);//可能的返回值：-1，0,5
        }
    }

    /**
     * 方法序号：4_4 验证商品条形码是否存在
     */
    public void verifyGoodsbarcode() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();
        }
        // 登录未超时
        else {
            String userId = this.getSession().getAttribute("userId").toString();//获取用户userId
            String barcode = this.getRequest().getParameter("value1");
            String result = hqService_part2.verifyGoodsbarcode(databaseUrl + userId + goodsDB, barcode);// 0表示0条记录，1表示有1条记录
            returnJsonObject(result);//可能的返回值：-1，0,5
        }
    }

    /**
     * 方法序号：4_5 保存商品 新增一个商品
     */
    public void saveGoods() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();
        }
        // 登录未超时
        else {
            String userId = this.getSession().getAttribute("userId").toString();//获取用户userId
            PLU plu = new PLU();
            plu.setId(null);
            plu.setNumber(this.getRequest().getParameter("value1"));
            plu.setName(this.getRequest().getParameter("value2"));
            plu.setBarcode(this.getRequest().getParameter("value3"));
            plu.setPrice(this.getRequest().getParameter("value4"));
            plu.setRrp(this.getRequest().getParameter("value5"));
            plu.setTax_Index(this.getRequest().getParameter("value6"));
            plu.setStock_Control(this.getRequest().getParameter("value7"));
            plu.setStock_Amount(this.getRequest().getParameter("value8"));
            plu.setUsed("1");
            //获取当前使用的外币
            String abbreviation = hqService_part2.getAbbreviation(databaseUrl + userId + programmingDB);
            if (abbreviation != null) {
                plu.setCurrency(abbreviation);
            } else {
                plu.setCurrency("");
            }
            String result = hqService_part2.saveGoods(databaseUrl + userId + goodsDB, plu);
            returnJsonObject(result);//可能的返回值：-1，0,1
        }

    }

    /**
     * 方法序号：4_6 查询所有税种税目索引
     */
    public void getAllGoodsTaxTariff() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();
        }
        // 登录未超时
        else {
            String userId = this.getSession().getAttribute("userId").toString();//获取用户UserId
            String result = hqService_part2.getAllGoodsTaxTariff(databaseUrl + userId + systemDB);
            returnJsonObject(result);//可能的返回值：-1，[],json数组字符串
        }
    }

    /**
     * 方法序号：4_7_0 删除商品前做验证，验证该商品是否设置了部类关联
     */
    public void verifyDepartmentAssociate() throws IOException, JSONException {
        // 如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();
        }
        // 登录未超时
        else {
            String goodsNumber = this.getRequest().getParameter("value1");
            String userId = this.getSession().getAttribute("userId").toString();//获取用户UserId
            String result = hqService_part2.verifyDepartmentAssociate(databaseUrl + userId + goodsDB, goodsNumber);// >=1表示成功，0表示失败
            returnJsonObject(result);//可能的返回值：-1，0,1
        }
    }

    /**
     * 方法序号：4_7 删除一条商品
     */
    public void deleteOneGoods() throws IOException, JSONException {
        // 如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();
        }
        // 登录未超时
        else {
            String goodsNumber = this.getRequest().getParameter("value1");
            String goodsFlag = this.getRequest().getParameter("value2");//用于判断此商品是否有部类关联
            String userId = this.getSession().getAttribute("userId").toString();//获取用户UserId
            String result = hqService_part2.deleteOneGoods(databaseUrl + userId + goodsDB, goodsNumber, goodsFlag);// >=1表示成功，0表示失败
            returnJsonObject(result);//可能的返回值：-1，0,1
        }
    }

    /**
     * 方法序号：4_8 修改一个商品 Id,Number,Name,Barcode,Price,Tax_Index,Stock_Control,Stock_Amount
     */
    public void updateOneGoods() throws IOException, JSONException {
        // 如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();
        }
        // 登录未超时
        else {
            String userId = this.getSession().getAttribute("userId").toString();//获取用户UserId
            PLU plu = new PLU();
            plu.setId(null);
            //plu.setId(Integer.parseInt(this.getRequest().getParameter("Id")));
            plu.setNumber(this.getRequest().getParameter("value1"));
            plu.setName(this.getRequest().getParameter("value2"));
            plu.setBarcode(this.getRequest().getParameter("value3"));
            plu.setPrice(this.getRequest().getParameter("value4"));
            plu.setRrp(this.getRequest().getParameter("value5"));
            plu.setTax_Index(this.getRequest().getParameter("value6"));
            plu.setStock_Control(this.getRequest().getParameter("value7"));
            plu.setStock_Amount(this.getRequest().getParameter("value8"));
            plu.setUsed("1");
            //获取当前使用的外币
            String abbreviation = hqService_part2.getAbbreviation(databaseUrl + userId + programmingDB);
            if (abbreviation != null) {
                plu.setCurrency(abbreviation);
            } else {
                plu.setCurrency("");
            }
            String result = hqService_part2.updateOneGoods(databaseUrl + userId + goodsDB, plu);
            returnJsonObject(result);//可能的返回值：-1，0,1
        }
    }

    /**
     * 方法序号：4_9 按条件查询商品
     */
    public void findAllGoodsByOption() throws IOException, JSONException {
        // 如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();
        }
        // 登录未超时
        else {
            String option = this.getRequest().getParameter("value1");//查询类型：number、name、barcode
            String key = this.getRequest().getParameter("value2");//查询关键字
            String userId = this.getSession().getAttribute("userId").toString();//获取用户UserId
            String result = hqService_part2.findAllGoodsByOption(databaseUrl + userId + goodsDB, databaseUrl + userId + systemDB, option, key);// >=1表示成功，0表示失败
            returnJsonObject(result);//可能的返回值：-1，[],json数组字符串
        }
    }

    /**
     * 方法序号：5_1 查询所有部类关联信息
     */
    public void findAllDept() throws IOException, JSONException {
        // 如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();
        }
        // 登录未超时
        else {
            String userId = this.getSession().getAttribute("userId").toString();//获取用户UserId
            String result = hqService_part2.findAllDept(databaseUrl + userId + goodsDB);
            returnJsonObject(result);//可能的返回值：-1，[],json数组字符串
        }
    }

    /**
     * 方法序号：5_2 简易查询所有商品信息
     */
    public void findAllGoodsInfo() throws IOException, JSONException {
        // 如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();
        }
        // 登录未超时
        else {
            String userId = this.getSession().getAttribute("userId").toString();//获取用户UserId
            String result = hqService_part2.findAllGoodsInfo(databaseUrl + userId + goodsDB);
            returnJsonObject(result);//可能的返回值：-1，[],json数组字符串
        }
    }

    /**
     * 方法序号：5_3 修改一个部门关联信息
     */
    public void updateOneDept() throws IOException, JSONException {
        // 如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();
        }
        // 登录未超时
        else {
            String Dept_No = this.getRequest().getParameter("Dept_No");
            String PLU_No = this.getRequest().getParameter("PLU_No");
            String userId = this.getSession().getAttribute("userId").toString();//获取用户UserId
            String result = hqService_part2.updateOneDept(databaseUrl + userId + goodsDB, Dept_No, PLU_No);
            returnJsonObject(result);//可能的返回值：-1，0,1
        }
    }

    /**
     * 方法序号：1_7 文件 上传
     *
     * @throws Exception
     */
    public void uploaderFile() throws Exception {
        String userId = this.getSession().getAttribute("userId").toString();//获取用户UserId
        //获取商品最大编号
        int goodsMaxNumber = Integer.parseInt(hqService_part2.getGoodsMaxNumber(databaseUrl + userId + goodsDB));// 0表示0条记录，1表示有1条记录
        goodsMaxNumber++;
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
        System.out.println("已经进入上传步骤1234567！");
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
                    fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);//如：1.xls
//					saveFileName=fileName;
//					System.out.println("上传过来的文件的文件名："+fileName);
                    if (fileName != null) {
//						File fullFile = new File(new String(fileItem.getName().getBytes(),
//								"utf-8")); // 解决文件名乱码问题
//                        File savedFile = new File(savePath, fileName);
//                        fileItem.write(savedFile);//保存
                        // 获得工作簿
                        Workbook workbook = null;
                        //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
                        if (fileName.endsWith("xls")) {
                            //2003版
                            workbook = new HSSFWorkbook(fileItem.getInputStream());
                        } else if (fileName.endsWith("xlsx")) {
                            //2007版
                            workbook = new XSSFWorkbook(fileItem.getInputStream());
                        }
                        ArrayList<PLU> pluArrayList = new ArrayList<>();
                        //工作表
                        Sheet sheet = null;
                        // 遍历所有工作表
                        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {// 获取每个Sheet表
                            sheet = workbook.getSheetAt(i);
                            // getLastRowNum，获取最后一行的行标，从0开始，但第一行是标题，所以此处从1开始
                            for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {
                                Row row = sheet.getRow(j);
                                PLU plu = new PLU();
                                if (row != null) {
                                    //获取单元格数据
                                    //Number
                                    if (row.getCell(0) != null) {
                                        plu.setNumber(String.valueOf(goodsMaxNumber++));
                                    }
                                    //Name
                                    if (row.getCell(1) != null) {
                                        plu.setNumber(row.getCell(1).toString());
                                    }
                                    //Barcode
                                    if (row.getCell(2) != null) {
                                        plu.setNumber(row.getCell(2).toString());
                                    }
                                    //Price
                                    if (row.getCell(3) != null) {
                                        plu.setNumber(row.getCell(3).toString());
                                    }
                                    //RRP
                                    if (row.getCell(4) != null) {
                                        plu.setNumber(row.getCell(4).toString());
                                    }
                                    //Tax_Index
                                    if (row.getCell(5) != null) {
                                        plu.setNumber(row.getCell(5).toString());
                                    }
                                    //Stock_Control
                                    if (row.getCell(6) != null) {
                                        plu.setNumber(row.getCell(6).toString());
                                    }
                                    //Stock_Amount
                                    if (row.getCell(7) != null) {
                                        plu.setNumber(row.getCell(7).toString());
                                    }
                                    //Currency
                                    if (row.getCell(8) != null) {
                                        plu.setNumber(row.getCell(8).toString());
                                    }
                                    //Used
                                    if (row.getCell(9) != null) {
                                        plu.setNumber(row.getCell(9).toString());
                                    }
                                }
                                pluArrayList.add(plu);
                                System.out.println(plu.toString());
                            }
                        }
                        workbook.close();
                        String result = hqService_part2.saveGoodsList(databaseUrl + userId + goodsDB, pluArrayList);// 1表示插入成功，0表示插入失败,-1表示已存在

                    }
                }
            }
            System.out.println("文件上传成功！");
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
