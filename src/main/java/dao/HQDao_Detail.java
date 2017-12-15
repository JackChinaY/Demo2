package dao;

import entity.Page;

public class HQDao_Detail extends BaseDAO_Sqlite {

    /**
     * 方法序号： 1_1 查询所有收银员
     *
     * @return json数组
     */
    public String findAllCashiers(String databaseUrl) {
        String sql = "SELECT Number AS value1, Name AS value2, Code AS value3, Password AS value4 From Cashier_Table ORDER BY Number ASC";
        return this.getForJson(sql, databaseUrl);
    }

    /**
     * 方法序号： 2_1 查询所有税率
     */
    public String findAllFiscals(String databaseUrl) {
        String sql = "SELECT Number AS value1,Invoice_Code AS value2,Invoice_Name AS value3,Tax_Code AS value4,Tax_Name AS value5,Tax_Rate AS value6," +
                "Exempt_Flag AS value7,CRC32 AS value8 FROM Tax_Tariff ORDER BY Number ASC";
        return this.getForJson(sql, databaseUrl);
    }

    /**
     * 方法序号： 3_1 查询所有外汇
     */
    public String findAllCurrency(String databaseUrl) {
        String sql = "SELECT Number AS value1,Abbreviation AS value2,Exchange_Rate AS value3,Current AS value4,Name AS value5,Symbol AS value6 FROM Currency_Table ORDER BY Number ASC";
        return this.getForJson(sql, databaseUrl);
    }

    /**
     * 方法序号： 4_1 查询所有商品
     * Id,Number,Name,Barcode,Price,Tax_Index,Stock_Control,Stock_Amount
     */
    public String findAllGoods(String databaseUrl, Page page) {
        String sql = "SELECT Number AS value1, Name AS value2, Barcode AS value3, Price AS value4, RRP AS value5,Tax_Index AS value6, Stock_Control AS value7, Stock_Amount AS value8, Currency AS value12 "
                + "FROM (SELECT * FROM goods_info  ORDER BY Number ASC LIMIT ?)  LIMIT ? offset ?";
        return this.getForJson(sql, databaseUrl, page.getPageSize() * page.getPageIndex(), page.getPageSize(), page.getPageSize() * (page.getPageIndex() - 1));
    }

    /**
     * 方法序号：4_2 查询商品总记录数
     */
    public String getGoodsCount(String databaseUrl) {
        String sql = " SELECT count(*) as COUNTS from goods_info";
        return Integer.toString(this.getCount(sql, databaseUrl));
    }

    /**
     * 方法序号： 4_6 查询所有税种税目索引
     *
     * @return json数组
     */
    public String getAllGoodsTaxTariff(String databaseUrl) {
        String sql = "SELECT Number AS value1,Invoice_Code AS value2,Invoice_Name AS value3,Tax_Code AS value4,Tax_Name AS value5,Tax_Rate AS value6,Exempt_Flag AS value7,CRC32 AS value8 from Tax_Tariff  ORDER BY Number ASC";
        return this.getForJson(sql, databaseUrl);
    }

    /**
     * 方法序号： 4_9 按商品编号查询商品信息
     * Id,Number,Name,Barcode,Price,Tax_Index,Stock_Control,Stock_Amount
     *
     * @return json数组
     */
    public String findAllGoodsByOption_ByNumber(String databaseUrl, String key) {
        String sql = "SELECT Number AS value1, Name AS value2, Barcode AS value3, Price AS value4, RRP AS value5, Tax_Index AS value6, Stock_Control AS value7, Stock_Amount AS value8, Currency AS value12 FROM Goods_Info WHERE Number LIKE ?";
        return this.getForJson(sql, databaseUrl, "%" + key + "%");
    }

    /**
     * 方法序号： 4_10 按商品名称查询商品信息
     * Id,Number,Name,Barcode,Price,Tax_Index,Stock_Control,Stock_Amount
     *
     * @return json数组
     */
    public String findAllGoodsByOption_ByName(String databaseUrl, String key) {
        String sql = "SELECT Number AS value1, Name AS value2, Barcode AS value3, Price AS value4,RRP AS value5, Tax_Index AS value6, Stock_Control AS value7, Stock_Amount AS value8, Currency AS value12 FROM Goods_Info WHERE Name LIKE ?";
        return this.getForJson(sql, databaseUrl, "%" + key + "%");
    }

    /**
     * 方法序号： 4_11 按商品条形码查询商品信息
     * Id,Number,Name,Barcode,Price,Tax_Index,Stock_Control,Stock_Amount
     *
     * @return json数组
     */
    public String findAllGoodsByOption_ByBarcode(String databaseUrl, String key) {
        String sql = "SELECT Number AS value1, Name AS value2, Barcode AS value3, Price AS value4,RRP AS value5, Tax_Index AS value6, Stock_Control AS value7, Stock_Amount AS value8, Currency AS value12 FROM Goods_Info WHERE Barcode LIKE ?";
        return this.getForJson(sql, databaseUrl, "%" + key + "%");
    }

    /**
     * 方法序号： 5_1 查询所有部类关联信息
     *
     * @return json数组
     */
    public String findAllDept(String databaseUrl) {
        String sql = "SELECT id AS value1, Dept_No AS value2, PLU_No AS value3 from Department_Associate ORDER BY id ASC";
        return this.getForJson(sql, databaseUrl);
    }

    /**
     * 方法序号： 4_1 查询客户总记录数
     */
    public String getBuyersCount(String databaseUrl) {
        String sql = "SELECT COUNT(*) AS COUNTS FROM Buyer_Info";
        return Integer.toString(this.getCount(sql, databaseUrl));
    }

    /**
     * 方法序号： 4_2 查询所有客户
     */
    public String findAllBuyers(String databaseUrl, Page page) {
        String sql = "SELECT Number AS value1,Name AS value2,BPN AS value3,VAT AS value4,Address AS value5,Tel AS value6 FROM " +
                "(SELECT * FROM Buyer_Info ORDER BY Number ASC LIMIT ?) LIMIT ? offset ?;";
        return this.getForJson(sql, databaseUrl, page.getPageSize() * page.getPageIndex(), page.getPageSize(), page.getPageSize() * (page.getPageIndex() - 1));
    }

    /**
     * 方法序号： 4_8 查询所有客户 按条件查询 按number
     */
    public String findAllBuyersByOption_ByNumber(String databaseUrl, String key) {
        String sql = "SELECT Number AS value1,Name AS value2,BPN AS value3,VAT AS value4,Address AS value5,Tel AS value6 FROM Buyer_Info WHERE Number LIKE ?";
        return this.getForJson(sql, databaseUrl, "%" + key + "%");
    }

    /**
     * 方法序号： 4_8 查询所有客户 按条件查询 按Name
     */
    public String findAllBuyersByOption_ByName(String databaseUrl, String key) {
        String sql = "SELECT Number AS value1,Name AS value2,BPN AS value3,VAT AS value4,Address AS value5,Tel AS value6 FROM Buyer_Info WHERE Name LIKE ?";
        return this.getForJson(sql, databaseUrl, "%" + key + "%");
    }

    /**
     * 方法序号： 4_8 查询所有客户 按条件查询 按TPIN
     */
    public String findAllBuyersByOption_ByTPIN(String databaseUrl, String key) {
        String sql = "SELECT Number AS value1,Name AS value2,BPN AS value3,VAT AS value4,Address AS value5,Tel AS value6 FROM Buyer_Info WHERE BPN LIKE ?";
        return this.getForJson(sql, databaseUrl, "%" + key + "%");
    }


}
