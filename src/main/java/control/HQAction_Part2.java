package control;

import entity.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import service.HQService;
import service.HQService_Part2;
import util.SendEmail;
import util.UtilsAll;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
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
            String abbreviation = hqService_part2.getAbbreviation(databaseUrl + userId + programmingDB);
            if (abbreviation != null) {
                plu.setCurrency(abbreviation);
            }else {
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
