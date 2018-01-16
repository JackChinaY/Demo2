package service;

import dao.HQDao;
import dao.HQDao_Part2;
import entity.*;
import org.json.JSONArray;

/**
 * return返回值规定：0表示受影响行数为0，数据库出错；1表示受影响行数为1，
 * -1表示程序运行出错，服务器出错，-2表示该条记录在数据库中已存在，null也表示程序运行出错，服务器出错
 */
public class HQService_Part2 extends BaseService {

    private HQDao_Part2 hqDao_part2 = new HQDao_Part2();

    /**
     * 方法序号：4_1 查询所有商品 并将税目code、name、rate等信息添加进商品的每条记录中
     *
     * @return json数组字符串
     */
    public String findAllGoods(String databaseUrl_goods, String databaseUrl_Tax, Page page) {
        try {
            String goods = this.hqDao_part2.findAllGoods(databaseUrl_goods, page);
            String tax = this.hqDao_part2.getAllGoodsTaxTariff(databaseUrl_Tax);
            JSONArray goodsArray = new JSONArray(goods);
            JSONArray taxArray = new JSONArray(tax);
            //对商品进行遍历
            for (int i = 0; i < goodsArray.length(); i++) {
                //对税率进行遍历
                for (int j = 0; j < taxArray.length(); j++) {
                    if (goodsArray.getJSONObject(i).getString("value6").equals(taxArray.getJSONObject(j).getString("value1"))) {
                        //插入Tax Code
                        goodsArray.getJSONObject(i).put("value9", taxArray.getJSONObject(j).getString("value4"));
                        //插入Tax Name
                        goodsArray.getJSONObject(i).put("value10", taxArray.getJSONObject(j).getString("value5"));
                        //插入Tax Rate
                        goodsArray.getJSONObject(i).put("value11", taxArray.getJSONObject(j).getString("value6"));
                        break;
                    }
                    //如果都没找到就将信息置空和0
                    else if (j == taxArray.length() - 1) {
                        //插入Tax Code
                        goodsArray.getJSONObject(i).put("value9", "");
                        //插入Tax Name
                        goodsArray.getJSONObject(i).put("value10", "");
                        //插入Tax Rate
                        goodsArray.getJSONObject(i).put("value11", "0");
                    }
                }
//                //插入Tax Code
//                goodsArray.getJSONObject(i).put("value9", taxArray.getJSONObject(goodsArray.getJSONObject(i).getInt("value6") - 1).getString("value4"));
//                //插入Tax Name
//                goodsArray.getJSONObject(i).put("value10", taxArray.getJSONObject(goodsArray.getJSONObject(i).getInt("value6") - 1).getString("value5"));
//                //插入Tax Rate
//                goodsArray.getJSONObject(i).put("value11", taxArray.getJSONObject(goodsArray.getJSONObject(i).getInt("value6") - 1).getString("value6"));
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
            return this.hqDao_part2.getGoodsCount(databaseUrl);
        } catch (Exception e) {
            System.out.println("4_2 查询商品总记录数时出错！");
            e.printStackTrace();
        }
        return "-1";
    }

    /**
     * 方法序号： 4_3 查询商品最大编号
     */
    public String getGoodsMaxNumber(String databaseUrl) {
        try {
            return this.hqDao_part2.getGoodsMaxNumber(databaseUrl);
        } catch (Exception e) {
            System.out.println("4_3 查询商品最大编号时出错！");
            e.printStackTrace();
        }
        return "-1";
    }

    /**
     * 方法序号： 4_4 验证商品条形码是否存在
     */
    public String verifyGoodsbarcode(String databaseUrl, String barcode) {
        try {
            return this.hqDao_part2.verifyGoodsbarcode(databaseUrl, barcode);
        } catch (Exception e) {
            System.out.println("4_4 验证商品条形码是否存在时出错！");
            e.printStackTrace();
        }
        return "-1";
    }

    /**
     * 方法序号：4_5_0 获取当前外币
     */
    public String getAbbreviation(String databaseUrl) {
        try {
            return this.hqDao_part2.getAbbreviation(databaseUrl);// 添加
        } catch (Exception e) {
            System.out.println("4_5_0 获取当前外币时出错！");
            e.printStackTrace();
        }
        return "-1";
    }

    /**
     * 方法序号：4_5 保存单品
     */
    public String saveGoods(String databaseUrl, PLU plu) {
        try {
            return this.hqDao_part2.saveGoods(databaseUrl, plu);// 添加
        } catch (Exception e) {
            System.out.println("4_5 保存单品时出错！");
            e.printStackTrace();
        }
        return "-1";
    }

    /**
     * 方法序号：4_6 查询所有税种税目索引
     *
     * @return json数组字符串
     */
    public String getAllGoodsTaxTariff(String databaseUrl) {
        try {
            return this.hqDao_part2.getAllGoodsTaxTariff(databaseUrl);
        } catch (Exception e) {
            System.out.println("4_6_1 查询所有税种税记录时出错！");
            e.printStackTrace();
            return "-1";//程序运行出错，服务器出错
        }
    }

    /**
     * 方法序号：4_7_0 删除商品前做验证，验证该商品是否设置了部类关联
     */
    public String verifyDepartmentAssociate(String databaseUrl, String goodsNumber) {
        try {
            return this.hqDao_part2.verifyDepartmentAssociate(databaseUrl, goodsNumber);
        } catch (Exception e) {
            System.out.println("4_7 删除一条商品时出错！");
            e.printStackTrace();
            return "-1";//程序运行出错，服务器出错
        }
    }

    /**
     * 方法序号：4_7 删除一条商品
     */
    public String deleteOneGoods(String databaseUrl, String goodsNumber, String goodsFlag) {
        try {
            if (goodsFlag.equals("0")) {
                return this.hqDao_part2.deleteOneGoods(databaseUrl, goodsNumber);
            } else {
                this.hqDao_part2.setOneDeptGoodsNumber0(databaseUrl, goodsNumber);
                return this.hqDao_part2.deleteOneGoods(databaseUrl, goodsNumber);
            }
        } catch (Exception e) {
            System.out.println("4_7 删除一条商品时出错！");
            e.printStackTrace();
            return "-1";//程序运行出错，服务器出错
        }
    }

    /**
     * 方法序号：4_8 修改一个商品
     */
    public String updateOneGoods(String databaseUrl, PLU plu) {
        try {
            return this.hqDao_part2.updateOneGoods(databaseUrl, plu);// 添加
        } catch (Exception e) {
            System.out.println("4_8 修改一个商品时出错！");
            e.printStackTrace();
            return "-1";//程序运行出错，服务器出错
        }
    }

    /**
     * 方法序号：4_9 按条件编号查询商品信息
     */
    public String findAllGoodsByOption(String databaseUrl_goods, String databaseUrl_Tax, String option, String key) {
        try {
            String goods = null;
//            查询类型：number、name、barcode
            if (option.equals("number")) {
                goods = this.hqDao_part2.findAllGoodsByOption_ByNumber(databaseUrl_goods, key);
            } else if (option.equals("name")) {
                goods = this.hqDao_part2.findAllGoodsByOption_ByName(databaseUrl_goods, key);
            } else if (option.equals("barcode")) {
                goods = this.hqDao_part2.findAllGoodsByOption_ByBarcode(databaseUrl_goods, key);
            }
            String tax = this.hqDao_part2.getAllGoodsTaxTariff(databaseUrl_Tax);
            JSONArray goodsArray = new JSONArray(goods);
            JSONArray taxArray = new JSONArray(tax);
            //对商品进行遍历
            for (int i = 0; i < goodsArray.length(); i++) {
                //对税率进行遍历
                for (int j = 0; j < taxArray.length(); j++) {
                    if (goodsArray.getJSONObject(i).getString("value6").equals(taxArray.getJSONObject(j).getString("value1"))) {
                        //插入Tax Code
                        goodsArray.getJSONObject(i).put("value9", taxArray.getJSONObject(j).getString("value4"));
                        //插入Tax Name
                        goodsArray.getJSONObject(i).put("value10", taxArray.getJSONObject(j).getString("value5"));
                        //插入Tax Rate
                        goodsArray.getJSONObject(i).put("value11", taxArray.getJSONObject(j).getString("value6"));
                        break;
                    }
                    //如果都没找到就将信息置空和0
                    else if (j == taxArray.length() - 1) {
                        //插入Tax Code
                        goodsArray.getJSONObject(i).put("value9", "");
                        //插入Tax Name
                        goodsArray.getJSONObject(i).put("value10", "");
                        //插入Tax Rate
                        goodsArray.getJSONObject(i).put("value11", "0");
                    }
                }
//                //插入Tax Code
//                goodsArray.getJSONObject(i).put("value9", taxArray.getJSONObject(goodsArray.getJSONObject(i).getInt("value6") - 1).getString("value4"));
//                //插入Tax Name
//                goodsArray.getJSONObject(i).put("value10", taxArray.getJSONObject(goodsArray.getJSONObject(i).getInt("value6") - 1).getString("value5"));
//                //插入Tax Rate
//                goodsArray.getJSONObject(i).put("value11", taxArray.getJSONObject(goodsArray.getJSONObject(i).getInt("value6") - 1).getString("value6"));
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
            return this.hqDao_part2.findAllDept(databaseUrl);
        } catch (Exception e) {
            System.out.println("5_1 查询所有部类关联信息时出错！");
            e.printStackTrace();
            return "-1";//程序运行出错，服务器出错
        }
    }

    /**
     * 方法序号：5_2 简易查询所有商品信息
     *
     * @return json数组字符串
     */
    public String findAllGoodsInfo(String databaseUrl) {
        try {
            return this.hqDao_part2.findAllGoodsInfo(databaseUrl);
        } catch (Exception e) {
            System.out.println("5_2 简易查询所有商品信息时出错！");
            e.printStackTrace();
            return "-1";//程序运行出错，服务器出错
        }
    }

    /**
     * 方法序号：5_3 更新部门关联信息
     *
     * @return json数组字符串
     */
    public String updateOneDept(String databaseUrl, String Dept_No, String PLU_No) {
        try {
            return this.hqDao_part2.updateOneDept(databaseUrl, Dept_No, PLU_No);
        } catch (Exception e) {
            System.out.println("5_3 更新部门关联信息时出错！");
            e.printStackTrace();
            return "-1";//程序运行出错，服务器出错
        }
    }

}
