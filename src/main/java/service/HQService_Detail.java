package service;

import dao.HQDao_Detail;
import entity.*;
import org.json.JSONArray;

/**
 * return返回值规定：0表示受影响行数为0，数据库出错；1表示受影响行数为1，
 * -1表示程序运行出错，服务器出错，-2表示该条记录在数据库中已存在，null也表示程序运行出错，服务器出错
 */
public class HQService_Detail extends BaseService {

    private HQDao_Detail hqDao_part1 = new HQDao_Detail();

    /**
     * 方法序号：1_1 查询所有收银员
     *
     * @return json数组字符串
     */
    public String findAllCashiers(String databaseUrl) {
        try {
            return this.hqDao_part1.findAllCashiers(databaseUrl);
        } catch (Exception e) {
            System.out.println("1_1 查询所有收银员时出错！");
            e.printStackTrace();
            return "-1";//程序运行出错，服务器出错
        }
    }

    /**
     * 方法序号：2_1 查询所有税率
     */
    public String findAllFiscals(String databaseUrl) {
        try {
            return this.hqDao_part1.findAllFiscals(databaseUrl);
        } catch (Exception e) {
            System.out.println("2_1 查询所有税率时出错！");
            e.printStackTrace();
            return "-1";//程序运行出错，服务器出错
        }
    }

    /**
     * 方法序号：3_1 查询所有外汇
     */
    public String findAllCurrency(String databaseUrl) {
        try {
            return this.hqDao_part1.findAllCurrency(databaseUrl);
        } catch (Exception e) {
            System.out.println("3_1 查询所有外汇时出错！");
            e.printStackTrace();
            return "-1";//程序运行出错，服务器出错
        }
    }

    /**
     * 方法序号：4_1 查询所有商品 并将税目code、name、rate等信息添加进商品的每条记录中
     *
     * @return json数组字符串
     */
    public String findAllGoods(String databaseUrl_goods, String databaseUrl_Tax, Page page) {
        try {
            String goods = this.hqDao_part1.findAllGoods(databaseUrl_goods, page);
            String tax = this.hqDao_part1.getAllGoodsTaxTariff(databaseUrl_Tax);
            JSONArray goodsArray = new JSONArray(goods);
            JSONArray taxArray = new JSONArray(tax);
            for (int i = 0; i < goodsArray.length(); i++) {
                //插入Tax Code
                goodsArray.getJSONObject(i).put("value9", taxArray.getJSONObject(goodsArray.getJSONObject(i).getInt("value6") - 1).getString("value4"));
                //插入Tax Name
                goodsArray.getJSONObject(i).put("value10", taxArray.getJSONObject(goodsArray.getJSONObject(i).getInt("value6") - 1).getString("value5"));
                //插入Tax Rate
                goodsArray.getJSONObject(i).put("value11", taxArray.getJSONObject(goodsArray.getJSONObject(i).getInt("value6") - 1).getString("value6"));
            }
            return goodsArray.toString();
        } catch (Exception e) {
            System.out.println("4_1 查询所有商品时出错！");
            e.printStackTrace();
            return "-1";//程序运行出错，服务器出错
        }
    }

    /**
     * 方法序号： 4_2 查询商品总记录数
     */
    public String getGoodsCount(String databaseUrl) {
        try {
            return this.hqDao_part1.getGoodsCount(databaseUrl);
        } catch (Exception e) {
            System.out.println("4_2 查询商品总记录数时出错！");
            e.printStackTrace();
        }
        return "-1";
    }

    /**
     * 方法序号：4_9 按条件编号查询商品信息
     */
    public String findAllGoodsByOption(String databaseUrl_goods, String databaseUrl_Tax, String option, String key) {
        try {
            String goods = "";
//            查询类型：number、name、barcode
            if (option.equals("number")) {
                goods = this.hqDao_part1.findAllGoodsByOption_ByNumber(databaseUrl_goods, key);
            } else if (option.equals("name")) {
                goods = this.hqDao_part1.findAllGoodsByOption_ByName(databaseUrl_goods, key);
            } else if (option.equals("barcode")) {
                goods = this.hqDao_part1.findAllGoodsByOption_ByBarcode(databaseUrl_goods, key);
            }
            String tax = this.hqDao_part1.getAllGoodsTaxTariff(databaseUrl_Tax);
            JSONArray goodsArray = new JSONArray(goods);
            JSONArray taxArray = new JSONArray(tax);
            for (int i = 0; i < goodsArray.length(); i++) {
                //插入Tax Code
                goodsArray.getJSONObject(i).put("value9", taxArray.getJSONObject(goodsArray.getJSONObject(i).getInt("value6") - 1).getString("value4"));
                //插入Tax Name
                goodsArray.getJSONObject(i).put("value10", taxArray.getJSONObject(goodsArray.getJSONObject(i).getInt("value6") - 1).getString("value5"));
                //插入Tax Rate
                goodsArray.getJSONObject(i).put("value11", taxArray.getJSONObject(goodsArray.getJSONObject(i).getInt("value6") - 1).getString("value6"));
            }
            return goodsArray.toString();
        } catch (Exception e) {
            System.out.println("4_9 查询所有商品 按条件查询时出错！");
            e.printStackTrace();
            return "-1";//程序运行出错，服务器出错
        }
    }

    /**
     * 方法序号：5_1 查询所有部类关联信息
     *
     * @return json数组字符串
     */
    public String findAllDept(String databaseUrl) {
        try {
            return this.hqDao_part1.findAllDept(databaseUrl);
        } catch (Exception e) {
            System.out.println("5_1 查询所有部类关联信息时出错！");
            e.printStackTrace();
            return "-1";//程序运行出错，服务器出错
        }
    }

    /**
     * 方法序号：4_1 查询客户总记录数
     */
    public String getBuyersCount(String databaseUrl) {
        try {
            return this.hqDao_part1.getBuyersCount(databaseUrl);
        } catch (Exception e) {
            System.out.println("4_1 查询客户总记录数时出错！");
            e.printStackTrace();
            return "-1";//程序运行出错，服务器出错
        }
    }

    /**
     * 方法序号：4_2 查询所有客户
     */
    public String findAllBuyers(String databaseUrl, Page page) {
        try {
            return this.hqDao_part1.findAllBuyers(databaseUrl, page);
        } catch (Exception e) {
            System.out.println("4_2 查询所有客户时出错！");
            e.printStackTrace();
            return "-1";//程序运行出错，服务器出错
        }
    }

    /**
     * 方法序号：4_8 查询所有客户 按条件查询
     */
    public String findAllBuyersByOption(String databaseUrl, String option, String key) {
        try {
//            查询类型：number、name、tpin
            if (option.equals("number")) {
                return this.hqDao_part1.findAllBuyersByOption_ByNumber(databaseUrl, key);
            } else if (option.equals("name")) {
                return this.hqDao_part1.findAllBuyersByOption_ByName(databaseUrl, key);
            } else if (option.equals("tpin")) {
                return this.hqDao_part1.findAllBuyersByOption_ByTPIN(databaseUrl, key);
            }
        } catch (Exception e) {
            System.out.println("4_8 查询所有客户 按条件查询时出错！");
            e.printStackTrace();
            return "-1";//程序运行出错，服务器出错
        }
        return "[]";
    }


}
