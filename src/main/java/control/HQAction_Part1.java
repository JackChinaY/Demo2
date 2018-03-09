package control;

import entity.*;
import org.json.JSONException;
import org.json.JSONObject;
import service.HQService_Part1;

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
public class HQAction_Part1 extends BaseAction {
    /**
     * 序列化
     */
//    private static final long serialVersionUID = 415712263988003225L;
    private HQService_Part1 hqService_part1 = new HQService_Part1();
    //    private String currentUserId = "cbb418cc-8520-459f-ab02-ae3516388eb5";  //当前用户名Id，软件发布的时候把该字符内容删除掉
    private String databaseUrl = "jdbc:sqlite:D:/database/";  //sqlite数据库基础路径
    private String programmingDB = "/programmingDB.db";  //连接的是programmingDB.db
    private String systemDB = "/systemDB.db";  //连接的是systemDB.db
    private String buyerDB = "/buyerDB.db";  //连接的是buyerDB.db
    private String currencylistDB = "/currencylistDB.db";  //连接的是currencylistDB.db
    private String goodsDB = "/goodsDB.db";  //连接的是goodsDB.db

    /**
     * 方法序号：1_1 查询所有收银员
     */
    public void findAllCashiers() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();
        }
        //登录未超时
        else {
            String userId = this.getSession().getAttribute("userId").toString();//获取用户UserId
            String result = hqService_part1.findAllCashiers(databaseUrl + userId + programmingDB);
            returnJsonObject(result);//可能的返回值：-1，[],json数组字符串
        }
    }

    /**
     * 方法序号：1_2 查询收银员最大编号
     */
    public void getCashiersMaxNumber() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();//返回值：-3
        }
        //登录未超时
        else {
            String userId = this.getSession().getAttribute("userId").toString();//获取用户userId
            String result = hqService_part1.getCashiersMaxNumber(databaseUrl + userId + programmingDB);// 0表示0条记录，1表示有1条记录
            if (result == null) {
                result = "null";
            }
//            System.out.println("输出1：" + result);
            returnJsonObject(result);//可能的返回值：-1,null
        }
    }

    /**
     * 方法序号：1_3 查询收银员最大code
     */
    public void getCashiersMaxCode() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();//返回值：-3
        }
        //登录未超时
        else {
            String userId = this.getSession().getAttribute("userId").toString();//获取用户userId
            String result = hqService_part1.getCashiersMaxCode(databaseUrl + userId + programmingDB);// 0表示0条记录，1表示有1条记录
            if (result == null) {
                result = "null";
            }
            returnJsonObject(result);//可能的返回值：-1,null
        }
    }

    /**
     * 方法序号：1_4 验证收银员code是否存在
     */
    public void verifyCashierCode() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();//返回值：-3
        }
        //登录未超时
        else {
            String barcode = this.getRequest().getParameter("value1");
            String userId = this.getSession().getAttribute("userId").toString();//获取用户userId
            String result = hqService_part1.verifyCashierCode(databaseUrl + userId + programmingDB, barcode);// 0表示0条记录，1表示有1条记录
            returnJsonObject(result);//可能的返回值：-1,0,1
        }
    }

    /**
     * 方法序号：1_5 保存收银员
     */
    public void saveCashier() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();//返回值：-3
        }
        //登录未超时
        else {
            Cashier cashier = new Cashier();
            cashier.setNumber(this.getRequest().getParameter("value1"));
            cashier.setCode(this.getRequest().getParameter("value2"));
            cashier.setName(this.getRequest().getParameter("value3"));
            cashier.setPassword(this.getRequest().getParameter("value4"));
            String userId = this.getSession().getAttribute("userId").toString();//获取用户userId
            String result = hqService_part1.saveCashier(databaseUrl + userId + programmingDB, cashier);// 0表示0条记录，1表示有1条记录
            returnJsonObject(result);//可能的返回值：-1,0,1
        }
    }

    /**
     * 方法序号：1_6 保存修改后的收银员
     */
    public void modifyCashier() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();//返回值：-3
        }
        //登录未超时
        else {
            Cashier cashier = new Cashier();
            cashier.setNumber(this.getRequest().getParameter("value1"));
            cashier.setCode(this.getRequest().getParameter("value2"));
            cashier.setName(this.getRequest().getParameter("value3"));
            cashier.setPassword(this.getRequest().getParameter("value4"));
            String userId = this.getSession().getAttribute("userId").toString();//获取用户userId
            String result = hqService_part1.modifyCashier(databaseUrl + userId + programmingDB, cashier);// 0表示0条记录，1表示有1条记录
            returnJsonObject(result);//可能的返回值：-1,0,1
        }
    }

    /**
     * 方法序号：1_7 删除收银员
     */
    public void deleteOneCashier() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();//返回值：-3
        }
        //登录未超时
        else {
            Cashier cashier = new Cashier();
            cashier.setNumber(this.getRequest().getParameter("value1"));
            String userId = this.getSession().getAttribute("userId").toString();//获取用户userId
            String result = hqService_part1.deleteOneCashier(databaseUrl + userId + programmingDB, cashier);// 0表示0条记录，1表示有1条记录
            returnJsonObject(result);//可能的返回值：-1,0,1
        }
    }

    /**
     * 方法序号：2_1 查询所有税率
     */
    public void findAllFiscals() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();
        }
        //登录未超时
        else {
            String userId = this.getSession().getAttribute("userId").toString();//获取用户UserId
            String result = hqService_part1.findAllFiscals(databaseUrl + userId + systemDB);
            returnJsonObject(result);//可能的返回值：-1，[],json数组字符串
        }
    }

    /**
     * 方法序号：2_2 查询税率最大编号
     */
    public void getFiscalsMaxNumber() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();//返回值：-3
        }
        //登录未超时
        else {
            String userId = this.getSession().getAttribute("userId").toString();//获取用户userId
            String result = hqService_part1.getFiscalsMaxNumber(databaseUrl + userId + systemDB);// 0表示0条记录，1表示有1条记录
            if (result == null) {
                result = "null";
            }
//            System.out.println("输出1：" + result);
            returnJsonObject(result);//可能的返回值：-1,null
        }
    }

    /**
     * 方法序号：2_3 验证税率code是否存在
     */
    public void verifyFiscalCode() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();//返回值：-3
        }
        //登录未超时
        else {
            String code = this.getRequest().getParameter("value1");
            String userId = this.getSession().getAttribute("userId").toString();//获取用户userId
            String result = hqService_part1.verifyFiscalCode(databaseUrl + userId + systemDB, code);// 0表示0条记录，1表示有1条记录
            returnJsonObject(result);//可能的返回值：-1,0,1
        }
    }

    /**
     * 方法序号：2_4 保存税率
     */
    public void saveFiscal() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();//返回值：-3
        }
        //登录未超时
        else {
            Tax tax = new Tax();
            tax.setNumber(this.getRequest().getParameter("value1"));
            tax.setCode(this.getRequest().getParameter("value2"));
            tax.setName(this.getRequest().getParameter("value3"));
            tax.setRate(this.getRequest().getParameter("value4"));
//            System.out.println(tax.toString());
            String userId = this.getSession().getAttribute("userId").toString();//获取用户userId
            String result = hqService_part1.saveFiscal(databaseUrl + userId + systemDB, tax);// 0表示0条记录，1表示有1条记录
            returnJsonObject(result);//可能的返回值：-1,0,1
        }
    }

    /**
     * 方法序号：2_5 保存修改后的税率
     */
    public void modifyFiscal() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();//返回值：-3
        }
        //登录未超时
        else {
            Tax tax = new Tax();
            tax.setNumber(this.getRequest().getParameter("value1"));
            tax.setCode(this.getRequest().getParameter("value2"));
            tax.setName(this.getRequest().getParameter("value3"));
            tax.setRate(this.getRequest().getParameter("value4"));
            String userId = this.getSession().getAttribute("userId").toString();//获取用户userId
            String result = hqService_part1.Fiscal(databaseUrl + userId + systemDB, tax);// 0表示0条记录，1表示有1条记录
            returnJsonObject(result);//可能的返回值：-1,0,1
        }
    }

    /**
     * 方法序号：2_6 删除一条税率
     */
    public void deleteOneFiscal() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();//返回值：-3
        }
        //登录未超时
        else {
            Tax tax = new Tax();
            tax.setNumber(this.getRequest().getParameter("value1"));
            String userId = this.getSession().getAttribute("userId").toString();//获取用户userId
            String result = hqService_part1.deleteOneFiscal(databaseUrl + userId + goodsDB,databaseUrl + userId + systemDB, tax);// 0表示0条记录，1表示有1条记录
            returnJsonObject(result);//可能的返回值：-1,0,1
        }
    }

    /**
     * 方法序号：3_1 查询所有外汇
     */
    public void findAllCurrency() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();
        }
        //登录未超时
        else {
            String userId = this.getSession().getAttribute("userId").toString();//获取用户UserId
            String result = hqService_part1.findAllCurrency(databaseUrl + userId + programmingDB);
            returnJsonObject(result);//可能的返回值：-1，[],json数组字符串
        }
    }

    /**
     * 方法序号：3_2 保存修改后的外汇
     */
    public void modifyAbbreviation() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();//返回值：-3
        }
        //登录未超时
        else {
            ForeignCurrency currency = new ForeignCurrency();
            currency.setNumber(this.getRequest().getParameter("value1"));
            currency.setAbbreviation(this.getRequest().getParameter("value2"));
            currency.setExchangeRate(this.getRequest().getParameter("value3"));
            currency.setCurrent(this.getRequest().getParameter("value4"));
            currency.setName(this.getRequest().getParameter("value5"));
            currency.setSymbol(this.getRequest().getParameter("value6"));
            currency.setFlag("1");
//            System.out.println(currency.toString());
            String userId = this.getSession().getAttribute("userId").toString();//获取用户userId
            String result = hqService_part1.modifyAbbreviation(databaseUrl + userId + programmingDB, currency);// 0表示0条记录，1表示有1条记录
            returnJsonObject(result);//可能的返回值：-1,0,1
        }
    }

    /**
     * 方法序号：3_3 查询外汇列表，供用户选择用
     */
    public void findAllAbbreviationList() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();
        }
        //登录未超时
        else {
            String userId = this.getSession().getAttribute("userId").toString();//获取用户UserId
            String result = hqService_part1.findAllAbbreviationList(databaseUrl + userId + currencylistDB);
            returnJsonObject(result);//可能的返回值：-1，[],json数组字符串
        }
    }

    /**
     * 方法序号：3_4 将所有外币的Current设置为0
     */
    public void setAllAbbreviation0() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();//返回值：-3
        }
        //登录未超时
        else {
            String userId = this.getSession().getAttribute("userId").toString();//获取用户userId
            String result = hqService_part1.setAllAbbreviation0(databaseUrl + userId + programmingDB);// 0表示0条记录，1表示有1条记录
            returnJsonObject(result);//可能的返回值：-1,0,1
        }
    }

    /**
     * 方法序号：3_5 将商品库中的所有商品的Currency设置为当前的外币的缩写，当且仅当商品的Used=1，而Used=0的商品的Currency不修改，继续为空
     */
    public void setAllGoodsCurrency() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();//返回值：-3
        }
        //登录未超时
        else {
            ForeignCurrency currency = new ForeignCurrency();
            currency.setCurrent(this.getRequest().getParameter("value1"));
            String userId = this.getSession().getAttribute("userId").toString();//获取用户userId
            String result = hqService_part1.setAllGoodsCurrency(databaseUrl + userId + goodsDB, currency);// 0表示0条记录，1表示有1条记录
            returnJsonObject(result);//可能的返回值：-1,0,1
        }
    }

    /**
     * 方法序号：3_6 验证外币是否已被设置过
     */
    public void verifyAbbreviation() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();//返回值：-3
        }
        //登录未超时
        else {
            String abbreviation = this.getRequest().getParameter("value1");
            String userId = this.getSession().getAttribute("userId").toString();//获取用户userId
            String result = hqService_part1.verifyAbbreviation(databaseUrl + userId + programmingDB, abbreviation);// 0表示0条记录，1表示有1条记录
            returnJsonObject(result);//可能的返回值：-1,0,1
        }
    }

    /**
     * 方法序号：4_1 查询客户总记录数
     */
    public void getBuyersCount() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();
        }
        //登录未超时
        else {

            String userId = this.getSession().getAttribute("userId").toString();//获取用户UserId
            String result = hqService_part1.getBuyersCount(databaseUrl + userId + buyerDB);
            returnJsonObject(result);//可能的返回值：-1，0,具体数量
        }
    }

    /**
     * 方法序号：4_2 查询所有客户
     */
    public void findAllBuyers() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();
        }
        //登录未超时
        else {
            Page page = new Page();
//            System.out.println(this.getRequest().getParameter("value1"));
            page.setPageIndex(Integer.parseInt(this.getRequest().getParameter("value1")));
            page.setPageSize(Integer.parseInt(this.getRequest().getParameter("value2")));
            String userId = this.getSession().getAttribute("userId").toString();//获取用户UserId
            String result = hqService_part1.findAllBuyers(databaseUrl + userId + buyerDB, page);
            returnJsonObject(result);//可能的返回值：-1，[],json数组字符串
        }
    }

    /**
     * 方法序号：4_3 查询客户最大编号
     */
    public void getBuyersMaxNumber() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();//返回值：-3
        }
        //登录未超时
        else {
            String userId = this.getSession().getAttribute("userId").toString();//获取用户userId
            String result = hqService_part1.getBuyersMaxNumber(databaseUrl + userId + buyerDB);// 0表示0条记录，1表示有1条记录
            if (result == null) {
                result = "null";
            }
            returnJsonObject(result);//可能的返回值：-1,null
        }
    }

    /**
     * 方法序号：4_4 验证客户TPIN是否存在
     */
    public void verifyBuyerTPIN() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();//返回值：-3
        }
        //登录未超时
        else {
            String tpin = this.getRequest().getParameter("value1");
            String userId = this.getSession().getAttribute("userId").toString();//获取用户userId
            String result = hqService_part1.verifyBuyerTPIN(databaseUrl + userId + buyerDB, tpin);// 0表示0条记录，1表示有1条记录
            returnJsonObject(result);//可能的返回值：-1,0,1
        }
    }

    /**
     * 方法序号：4_5 保存客户
     */
    public void saveBuyer() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();//返回值：-3
        }
        //登录未超时
        else {
            Buyer buyer = new Buyer();
            buyer.setNumber(this.getRequest().getParameter("value1"));
            buyer.setName(this.getRequest().getParameter("value2"));
            buyer.setTpin(this.getRequest().getParameter("value3"));
            buyer.setVat(this.getRequest().getParameter("value4"));
            buyer.setTel(this.getRequest().getParameter("value5"));
            buyer.setAddress(this.getRequest().getParameter("value6"));
            String userId = this.getSession().getAttribute("userId").toString();//获取用户userId
            String result = hqService_part1.saveBuyer(databaseUrl + userId + buyerDB, buyer);// 0表示0条记录，1表示有1条记录
            returnJsonObject(result);//可能的返回值：-1,0,1
        }
    }

    /**
     * 方法序号：4_6 保存修改后的客户
     */
    public void modifyBuyer() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();//返回值：-3
        }
        //登录未超时
        else {
            Buyer buyer = new Buyer();
            buyer.setNumber(this.getRequest().getParameter("value1"));
            buyer.setName(this.getRequest().getParameter("value2"));
            buyer.setTpin(this.getRequest().getParameter("value3"));
            buyer.setVat(this.getRequest().getParameter("value4"));
            buyer.setTel(this.getRequest().getParameter("value5"));
            buyer.setAddress(this.getRequest().getParameter("value6"));
            String userId = this.getSession().getAttribute("userId").toString();//获取用户userId
            String result = hqService_part1.modifyBuyer(databaseUrl + userId + buyerDB, buyer);// 0表示0条记录，1表示有1条记录
            returnJsonObject(result);//可能的返回值：-1,0,1
        }
    }

    /**
     * 方法序号：4_7 删除客户
     */
    public void deleteOneBuyer() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();//返回值：-3
        }
        //登录未超时
        else {
            Buyer buyer = new Buyer();
            buyer.setNumber(this.getRequest().getParameter("value1"));
            String userId = this.getSession().getAttribute("userId").toString();//获取用户userId
            String result = hqService_part1.deleteOneBuyer(databaseUrl + userId + buyerDB, buyer);// 0表示0条记录，1表示有1条记录
            returnJsonObject(result);//可能的返回值：-1,0,1
        }
    }

    /**
     * 方法序号：4_8 查询所有客户 按条件查询
     */
    public void findAllBuyersByOption() throws IOException, JSONException {
        //如果用户登录超时，则需要重新登录
        if (this.getSession().getAttribute("userId") == null) {
            connectionTimeOut();
        }
        //登录未超时
        else {
            String option = this.getRequest().getParameter("value1");//查询类型：number、name、tpin
            String key = this.getRequest().getParameter("value2");//查询关键字
//            System.out.println(option+"  "+key);
            String userId = this.getSession().getAttribute("userId").toString();//获取用户UserId
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
