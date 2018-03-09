package entity;

import java.math.BigDecimal;

public class PLU {
    /**
     * 单品
     * number PLU编号；Barcode PLU条码；Name PLU名称；Price PLU单价；
     * Department PLU部类编号；Sub_Group_Code PLU子部类；Stock_Amount PLU库存总量；
     * Stock_Control库存控制标志；flag 标志位；Total_Sales_Qty；Total_Sales_Amount
     * Id,Number,Name,Barcode,Price,Tax_Index,Stock_Control,Stock_Amount
     */
    private Integer id;
    private String number;
    private String name;
    private String barcode;
    private String price;
    private String rrp;
    private String tax_Index;
    private String stock_Control;
    private String stock_Amount;
    private String Currency;
    private String Used;

    public PLU() {
        this.number = "";
        this.name = "";
        this.barcode = "";
        this.price = "";
        this.rrp = "";
        this.tax_Index = "";
        this.stock_Control = "";
        this.stock_Amount = "";
        this.Currency = "";
        this.Used = "";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTax_Index() {
        return tax_Index;
    }

    public void setTax_Index(String tax_Index) {
        this.tax_Index = tax_Index;
    }

    public String getStock_Control() {
        return stock_Control;
    }

    public void setStock_Control(String stock_Control) {
        this.stock_Control = stock_Control;
    }

    public String getStock_Amount() {
        return stock_Amount;
    }

    public void setStock_Amount(String stock_Amount) {
        this.stock_Amount = stock_Amount;
    }

    public String getRrp() {
        return rrp;
    }

    public void setRrp(String rrp) {
        this.rrp = rrp;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getUsed() {
        return Used;
    }

    public void setUsed(String used) {
        Used = used;
    }

    @Override
    public String toString() {
        return "PLU{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", barcode='" + barcode + '\'' +
                ", price='" + price + '\'' +
                ", rrp='" + rrp + '\'' +
                ", tax_Index='" + tax_Index + '\'' +
                ", stock_Control='" + stock_Control + '\'' +
                ", stock_Amount='" + stock_Amount + '\'' +
                ", Currency='" + Currency + '\'' +
                ", Used='" + Used + '\'' +
                '}';
    }
}
