package control;

import entity.PLU;
import entity.Page;
import org.json.JSONException;
import org.json.JSONObject;
import service.HQService_Detail;

import java.io.IOException;

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
public class HQAction_Detail extends BaseAction {
    /**
     * 序列化
     */
//    private static final long serialVersionUID = 415712263988003225L;
    private HQService_Detail hqService_part1 = new HQService_Detail();
    private String databaseUrl = "jdbc:sqlite:D:/database/";  //sqlite数据库基础路径
    private String programmingDB = "/programmingDB.db";  //连接的是programmingDB.db
    private String systemDB = "/systemDB.db";  //连接的是systemDB.db
    private String buyerDB = "/buyerDB.db";  //连接的是buyerDB.db
    private String goodsDB = "/goodsDB.db";  //连接的是goodsDB.db

    /**
     * 无参构造函数
     * @throws IOException
     */
//    public HQAction_Detail() throws IOException {
//        //如果用户登录超时或者用户的身份不是管理员，则需要重新登录
//        if (this.getSession().getAttribute("userId") == null || this.getSession().getAttribute("userFlag") == null) {
//            JSONObject jo = new JSONObject();
//            jo.put("jsonObject", -3);//-3为登录超时
//            this.getResponse().setContentType("text/html;charset=UTF-8");// 设置响应数据类型
//            this.getResponse().getWriter().print(jo);// 向前台发送json数据
//        }
//    }

    /**
     * 方法序号：1_1 查询所有收银员
     */
    public void findAllCashiers() throws IOException, JSONException {
        //如果用户登录超时或者用户的身份不是管理员，则需要重新登录
        if (this.getSession().getAttribute("userId") == null || this.getSession().getAttribute("userFlag") == null) {
            connectionTimeOut();
        }
        //登录未超时
        else {
            String userId = this.getRequest().getParameter("value1");//获取用户Id
//            System.out.println(userId);
            String result = hqService_part1.findAllCashiers(databaseUrl + userId + programmingDB);
            returnJsonObject(result);//可能的返回值：-1，[],json数组字符串
        }
    }

    /**
     * 方法序号：2_1 查询所有税率
     */
    public void findAllFiscals() throws IOException, JSONException {
        //如果用户登录超时或者用户的身份不是管理员，则需要重新登录
        if (this.getSession().getAttribute("userId") == null || this.getSession().getAttribute("userFlag") == null) {
            connectionTimeOut();
        }
        //登录未超时
        else {
            String userId = this.getRequest().getParameter("value1");//获取用户Id
            String result = hqService_part1.findAllFiscals(databaseUrl + userId + systemDB);
            returnJsonObject(result);//可能的返回值：-1，[],json数组字符串
        }
    }

    /**
     * 方法序号：3_1 查询所有外汇
     */
    public void findAllCurrency() throws IOException, JSONException {
        //如果用户登录超时或者用户的身份不是管理员，则需要重新登录
        if (this.getSession().getAttribute("userId") == null || this.getSession().getAttribute("userFlag") == null) {
            connectionTimeOut();
        }
        //登录未超时
        else {
            String userId = this.getRequest().getParameter("value1");//获取用户Id
            String result = hqService_part1.findAllCurrency(databaseUrl + userId + programmingDB);
            returnJsonObject(result);//可能的返回值：-1，[],json数组字符串
        }
    }

    /**
     * 方法序号：4_1 查询所有商品
     */
    public void findAllGoods() throws IOException, JSONException {
        //如果用户登录超时或者用户的身份不是管理员，则需要重新登录
        if (this.getSession().getAttribute("userId") == null || this.getSession().getAttribute("userFlag") == null) {
            connectionTimeOut();
        }
        //登录未超时
        else {
            String userId = this.getRequest().getParameter("value1");//获取用户Id
            Page page = new Page();
            page.setPageIndex(Integer.parseInt(this.getRequest().getParameter("pageIndex")));//当前页码
            page.setPageSize(Integer.parseInt(this.getRequest().getParameter("pageSize")));//分页大小
            String result = hqService_part1.findAllGoods(databaseUrl + userId + goodsDB, databaseUrl + userId + systemDB, page);
            returnJsonObject(result);//可能的返回值：-1，[],json数组字符串
        }
    }

    /**
     * 方法序号：4_2 查询商品总记录数
     */
    public void getGoodsCount() throws IOException, JSONException {
        //如果用户登录超时或者用户的身份不是管理员，则需要重新登录
        if (this.getSession().getAttribute("userId") == null || this.getSession().getAttribute("userFlag") == null) {
            connectionTimeOut();
        }
        //登录未超时
        else {
            String userId = this.getRequest().getParameter("value1");//获取用户Id
            String result = hqService_part1.getGoodsCount(databaseUrl + userId + goodsDB);// 0表示0条记录，1表示有1条记录
            returnJsonObject(result);//可能的返回值：-1，0,5
        }
    }

    /**
     * 方法序号：4_9 按条件查询商品
     */
    public void findAllGoodsByOption() throws IOException, JSONException {
        //如果用户登录超时或者用户的身份不是管理员，则需要重新登录
        if (this.getSession().getAttribute("userId") == null || this.getSession().getAttribute("userFlag") == null) {
            connectionTimeOut();
        }
        // 登录未超时
        else {
            String option = this.getRequest().getParameter("value1");//查询类型：number、name、barcode
            String key = this.getRequest().getParameter("value2");//查询关键字
            String userId = this.getRequest().getParameter("value3");//获取用户Id
            String result = hqService_part1.findAllGoodsByOption(databaseUrl + userId + goodsDB, databaseUrl + userId + systemDB, option, key);// >=1表示成功，0表示失败
            returnJsonObject(result);//可能的返回值：-1，[],json数组字符串
        }
    }

    /**
     * 方法序号：5_1 查询所有部类关联信息
     */
    public void findAllDept() throws IOException, JSONException {
        //如果用户登录超时或者用户的身份不是管理员，则需要重新登录
        if (this.getSession().getAttribute("userId") == null || this.getSession().getAttribute("userFlag") == null) {
            connectionTimeOut();
        }
        // 登录未超时
        else {
            String userId = this.getRequest().getParameter("value1");//获取用户Id
            String result = hqService_part1.findAllDept(databaseUrl + userId + goodsDB);
            returnJsonObject(result);//可能的返回值：-1，[],json数组字符串
        }
    }


    /**
     * 方法序号：4_1 查询客户总记录数
     */
    public void getBuyersCount() throws IOException, JSONException {
        //如果用户登录超时或者用户的身份不是管理员，则需要重新登录
        if (this.getSession().getAttribute("userId") == null || this.getSession().getAttribute("userFlag") == null) {
            connectionTimeOut();
        }
        //登录未超时
        else {
            String userId = this.getRequest().getParameter("value1");//获取用户Id
            String result = hqService_part1.getBuyersCount(databaseUrl + userId + buyerDB);
            returnJsonObject(result);//可能的返回值：-1，0,具体数量
        }
    }

    /**
     * 方法序号：4_2 查询所有客户
     */
    public void findAllBuyers() throws IOException, JSONException {
        //如果用户登录超时或者用户的身份不是管理员，则需要重新登录
        if (this.getSession().getAttribute("userId") == null || this.getSession().getAttribute("userFlag") == null) {
            connectionTimeOut();
        }
        //登录未超时
        else {
            Page page = new Page();
            page.setPageIndex(Integer.parseInt(this.getRequest().getParameter("value1")));
            page.setPageSize(Integer.parseInt(this.getRequest().getParameter("value2")));
            String userId = this.getRequest().getParameter("value3");//获取用户Id
            String result = hqService_part1.findAllBuyers(databaseUrl + userId + buyerDB, page);
            returnJsonObject(result);//可能的返回值：-1，[],json数组字符串
        }
    }

    /**
     * 方法序号：4_8 查询所有客户 按条件查询
     */
    public void findAllBuyersByOption() throws IOException, JSONException {
        //如果用户登录超时或者用户的身份不是管理员，则需要重新登录
        if (this.getSession().getAttribute("userId") == null || this.getSession().getAttribute("userFlag") == null) {
            connectionTimeOut();
        }
        //登录未超时
        else {
            String option = this.getRequest().getParameter("value1");//查询类型：number、name、tpin
            String key = this.getRequest().getParameter("value2");//查询关键字
//            System.out.println(option+"  "+key);
            String userId = this.getRequest().getParameter("value3");//获取用户Id
            String result = hqService_part1.findAllBuyersByOption(databaseUrl + userId + buyerDB, option, key);
            returnJsonObject(result);//可能的返回值：-1，[],json数组字符串
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
