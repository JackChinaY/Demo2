package dao;

import entity.*;

public class HQDao_Part2 extends BaseDAO_Sqlite {

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
        String sql = " SELECT COUNT(*) as COUNTS from Goods_Info";
        return Integer.toString(this.getCount(sql, databaseUrl));
    }

    /**
     * 方法序号：4_3 查询商品最大编号
     */
    public String getGoodsMaxNumber(String databaseUrl) {
        String sql = " SELECT MAX(Number) as MAXNUM from Goods_Info";
        return this.getOneRecard(sql, databaseUrl);
    }

    /**
     * 方法序号：4_4 验证商品条形码是否存在
     */
    public String verifyGoodsbarcode(String databaseUrl, String barcode) {
        String sql = " SELECT COUNT(*) as COUNTS from Goods_Info WHERE Barcode=?";
        return Integer.toString(this.getCount(sql, databaseUrl, barcode));
    }

    /**
     * 方法序号：4_5_0 获取当前外币
     */
    public String getAbbreviation(String databaseUrl) {
        String sql = "SELECT Abbreviation FROM Currency_Table WHERE Current=1 ";
        return this.getOneRecard(sql, databaseUrl);
    }

    /**
     * 方法序号：4_5 保存单品
     */
    public String saveGoods(String databaseUrl, PLU plu) {
        String sql = "INSERT INTO Goods_Info (Number,Name,Barcode,Price,RRP,Tax_Index,Stock_Control,Stock_Amount,Currency,Used) VALUES (?,?,?,?,?,?,?,?,?,?)";
        return Integer.toString(this.saveOrUpdateOrDelete(sql, databaseUrl, plu.getNumber(),
                plu.getName(), plu.getBarcode(),
                plu.getPrice(), plu.getRrp(), plu.getTax_Index(),
                plu.getStock_Control(), plu.getStock_Amount(), plu.getCurrency(), plu.getUsed()));
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
     * 方法序号：4_7_0 删除商品前做验证，验证该商品是否设置了部类关联
     */
    public String verifyDepartmentAssociate(String databaseUrl, String goodsNumber) {
        String sql = "SELECT COUNT(*) AS COUNTS FROM Department_Associate WHERE PLU_No=?";
        return Integer.toString(this.getCount(sql, databaseUrl, goodsNumber));
    }

//    /**
//     * 方法序号：4_7 删除一条商品
//     */
//    public String deleteOneGoods(String databaseUrl, String goodsNumber) {
//        String sql = "DELETE FROM Goods_Info where Number=?";
//        return Integer.toString(this.saveOrUpdateOrDelete(sql, databaseUrl, goodsNumber));
//    }

    /**
     * 方法序号：4_7 删除一条商品
     */
    public String deleteOneGoods(String databaseUrl, String goodsNumber) {
        String sql = "UPDATE Goods_Info SET Name='',Barcode='',Price=0,Tax_Index=0,RRP=0,Stock_Control=0,Stock_Amount=0,Currency='',Used=0 WHERE Number=? ";
        return Integer.toString(this.saveOrUpdateOrDelete(sql, databaseUrl, goodsNumber));
    }

    /**
     * 方法序号：4_8 修改一个商品
     */
    public String updateOneGoods(String databaseUrl, PLU plu) {
        String sql = "UPDATE Goods_Info SET Name=?,Barcode=?,Price=?,Tax_Index=?,RRP=?,Stock_Control=?,Stock_Amount=?,Currency=?,Used=? WHERE Number=? ";
        return Integer.toString(this.saveOrUpdateOrDelete(sql, databaseUrl, plu.getName(),
                plu.getBarcode(), plu.getPrice(),
                plu.getTax_Index(), plu.getRrp(), plu.getStock_Control(),
                plu.getStock_Amount(), plu.getCurrency(), plu.getUsed(), plu.getNumber()));
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
     * 方法序号： 5_2 简易查询所有商品信息
     *
     * @return json数组
     */
    public String findAllGoodsInfo(String databaseUrl) {
        String sql = "SELECT Number AS value1, Name AS value2, Barcode AS value3, Price AS value4 from Goods_Info ORDER BY Number ASC";
        return this.getForJson(sql, databaseUrl);
    }

    /**
     * 方法序号：5_3 修改一个部门关联信息
     */
    public String updateOneDept(String databaseUrl, String Dept_No, String PLU_No) {
        String sql = "UPDATE Department_Associate SET PLU_No=? WHERE Dept_No=? ";
        return Integer.toString(this.saveOrUpdateOrDelete(sql, databaseUrl, PLU_No, Dept_No));
    }

    /**
     * 方法序号：5_4 将一个部门关联的商品编号置0
     */
    public String setOneDeptGoodsNumber0(String databaseUrl, String PLU_No) {
        String sql = "UPDATE Department_Associate SET PLU_No=0 WHERE PLU_No=? ";
        return Integer.toString(this.saveOrUpdateOrDelete(sql, databaseUrl, PLU_No));
    }
}
