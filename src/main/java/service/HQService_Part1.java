package service;

import dao.HQDao_Part1;
import entity.*;

/**
 * return返回值规定：0表示受影响行数为0，数据库出错；1表示受影响行数为1，
 * -1表示程序运行出错，服务器出错，-2表示该条记录在数据库中已存在，null也表示程序运行出错，服务器出错
 */
public class HQService_Part1 extends BaseService {

    private HQDao_Part1 hqDao_part1 = new HQDao_Part1();

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
     * 方法序号： 1_2 查询收银员最大编号
     */
    public String getCashiersMaxNumber(String databaseUrl) {
        try {
            return this.hqDao_part1.getCashiersMaxNumber(databaseUrl);
        } catch (Exception e) {
            System.out.println("1_2 查询收银员最大编号时出错！");
            e.printStackTrace();
            return "-1";
        }
    }

    /**
     * 方法序号： 1_3 查询收银员最大code
     */
    public String getCashiersMaxCode(String databaseUrl) {
        try {
            return this.hqDao_part1.getCashiersMaxCode(databaseUrl);
        } catch (Exception e) {
            System.out.println("1_3 查询收银员最大code时出错！");
            e.printStackTrace();
            return "-1";
        }
    }

    /**
     * 方法序号： 1_4 验证收银员code是否存在
     */
    public String verifyCashierCode(String databaseUrl, String barcode) {
        try {
            return this.hqDao_part1.verifyCashierCode(databaseUrl, barcode);
        } catch (Exception e) {
            System.out.println("1_4 验证收银员code是否存在时出错！");
            e.printStackTrace();
        }
        return "-1";
    }

    /**
     * 方法序号： 1_5 保存收银员
     */
    public String saveCashier(String databaseUrl, Cashier cashier) {
        try {
            return this.hqDao_part1.saveCashier(databaseUrl, cashier);
        } catch (Exception e) {
            System.out.println("1_5 保存收银员时出错！");
            e.printStackTrace();
        }
        return "-1";
    }

    /**
     * 方法序号： 1_6 保存修改后的收银员
     */
    public String modifyCashier(String databaseUrl, Cashier cashier) {
        try {
            return this.hqDao_part1.modifyCashier(databaseUrl, cashier);
        } catch (Exception e) {
            System.out.println("1_6 保存修改后的收银员时出错！");
            e.printStackTrace();
        }
        return "-1";
    }

    /**
     * 方法序号： 1_7 删除收银员
     */
    public String deleteOneCashier(String databaseUrl, Cashier cashier) {
        try {
            return this.hqDao_part1.deleteOneCashier(databaseUrl, cashier);
        } catch (Exception e) {
            System.out.println("1_7 删除收银员时出错！");
            e.printStackTrace();
        }
        return "-1";
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
     * 方法序号： 2_2 查询税率最大编号
     */
    public String getFiscalsMaxNumber(String databaseUrl) {
        try {
            return this.hqDao_part1.getFiscalsMaxNumber(databaseUrl);
        } catch (Exception e) {
            System.out.println("2_2 查询税率最大编号时出错！");
            e.printStackTrace();
            return "-1";
        }
    }

    /**
     * 方法序号： 2_3 验证税率code是否存在
     */
    public String verifyFiscalCode(String databaseUrl, String code) {
        try {
            return this.hqDao_part1.verifyFiscalCode(databaseUrl, code);
        } catch (Exception e) {
            System.out.println("2_3 验证税率code是否存在时出错！");
            e.printStackTrace();
        }
        return "-1";
    }

    /**
     * 方法序号： 2_4 保存税率
     */
    public String saveFiscal(String databaseUrl, Tax tax) {
        try {
            return this.hqDao_part1.saveFiscal(databaseUrl, tax);
        } catch (Exception e) {
            System.out.println("2_4 保存税率时出错！");
            e.printStackTrace();
        }
        return "-1";
    }

    /**
     * 方法序号： 2_5 保存修改后的税率
     */
    public String Fiscal(String databaseUrl, Tax tax) {
        try {
            return this.hqDao_part1.Fiscal(databaseUrl, tax);
        } catch (Exception e) {
            System.out.println("2_5 保存修改后的税率时出错！");
            e.printStackTrace();
        }
        return "-1";
    }

    /**
     * 方法序号： 2_6 删除一条税率
     */
    public String deleteOneFiscal(String databaseUrl, Tax tax) {
        try {
            return this.hqDao_part1.deleteOneFiscal(databaseUrl, tax);
        } catch (Exception e) {
            System.out.println("2_6 删除一条税率时出错！");
            e.printStackTrace();
        }
        return "-1";
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
     * 方法序号： 3_2 保存修改后的外汇
     */
    public String modifyAbbreviation(String databaseUrl, ForeignCurrency currency) {
        try {
            return this.hqDao_part1.modifyAbbreviation(databaseUrl, currency);
        } catch (Exception e) {
            System.out.println("3_2 保存修改后的外汇时出错！");
            e.printStackTrace();
        }
        return "-1";
    }

    /**
     * 方法序号：3_3 查询外汇列表，供用户选择用
     */
    public String findAllAbbreviationList(String databaseUrl) {
        try {
            return this.hqDao_part1.findAllAbbreviationList(databaseUrl);
        } catch (Exception e) {
            System.out.println("3_3 查询外汇列表，供用户选择用时出错！");
            e.printStackTrace();
            return "-1";//程序运行出错，服务器出错
        }
    }

    /**
     * 方法序号： 3_4 将所有外币的Current设置为0
     */
    public String setAllAbbreviation0(String databaseUrl) {
        try {
            return this.hqDao_part1.setAllAbbreviation0(databaseUrl);
        } catch (Exception e) {
            System.out.println("3_4 将所有外币的Current设置为0时出错！");
            e.printStackTrace();
        }
        return "-1";
    }

    /**
     * 方法序号： 3_5 将商品库中的所有商品的Currency设置为当前的外币的缩写
     */
    public String setAllGoodsCurrency(String databaseUrl, ForeignCurrency currency) {
        try {
            return this.hqDao_part1.setAllGoodsCurrency(databaseUrl, currency);
        } catch (Exception e) {
            System.out.println("3_5 将商品库中的所有商品的Currency设置为当前的外币的缩写时出错！");
            e.printStackTrace();
        }
        return "-1";
    }

    /**
     * 方法序号： 3_6 验证外币是否已被设置过
     */
    public String verifyAbbreviation(String databaseUrl, String abbreviation) {
        try {
            return this.hqDao_part1.verifyAbbreviation(databaseUrl, abbreviation);
        } catch (Exception e) {
            System.out.println("3_6 验证外币是否已被设置过时出错！");
            e.printStackTrace();
        }
        return "-1";
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
     * 方法序号： 4_3 查询客户最大编号
     */
    public String getBuyersMaxNumber(String databaseUrl) {
        try {
            return this.hqDao_part1.getBuyersMaxNumber(databaseUrl);
        } catch (Exception e) {
            System.out.println("4_3 查询客户最大编号时出错！");
            e.printStackTrace();
        }
        return "-1";
    }

    /**
     * 方法序号： 4_4 验证客户TPIN是否存在
     */
    public String verifyBuyerTPIN(String databaseUrl, String tpin) {
        try {
            return this.hqDao_part1.verifyBuyerTPIN(databaseUrl, tpin);
        } catch (Exception e) {
            System.out.println("4_4 验证客户TPIN是否存在时出错！");
            e.printStackTrace();
        }
        return "-1";
    }

    /**
     * 方法序号： 4_5 保存客户
     */
    public String saveBuyer(String databaseUrl, Buyer buyer) {
        try {
            return this.hqDao_part1.saveBuyer(databaseUrl, buyer);
        } catch (Exception e) {
            System.out.println("4_5 保存客户时出错！");
            e.printStackTrace();
        }
        return "-1";
    }

    /**
     * 方法序号： 4_6 保存修改后的客户
     */
    public String modifyBuyer(String databaseUrl, Buyer buyer) {
        try {
            return this.hqDao_part1.modifyBuyer(databaseUrl, buyer);
        } catch (Exception e) {
            System.out.println("4_6 保存修改后的客户时出错！");
            e.printStackTrace();
        }
        return "-1";
    }

    /**
     * 方法序号： 4_7 删除客户
     */
    public String deleteOneBuyer(String databaseUrl, Buyer buyer) {
        try {
            return this.hqDao_part1.deleteOneBuyer(databaseUrl, buyer);
        } catch (Exception e) {
            System.out.println("4_7 删除客户时出错！");
            e.printStackTrace();
        }
        return "-1";
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
