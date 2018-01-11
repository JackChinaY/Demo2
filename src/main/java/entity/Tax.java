package entity;

public class Tax {
    /**
     * 税率信息 number税种编号；vatrate税率值；name名字；upperlimit上限；flag标志位
     */
    private String id;
    private String number;
    private String invoice_Code;
    private String invoice_Name;
    private String code;
    private String name;
    private String rate;
    private String exempt_Flag;
    private String CRC32;

    public Tax() {
        this.id = "";
        this.number = "";
        this.invoice_Code = "";
        this.invoice_Name = "";
        this.code = "";
        this.name = "";
        this.rate = "";
        this.exempt_Flag = "";
        this.CRC32 = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getInvoice_Code() {
        return invoice_Code;
    }

    public void setInvoice_Code(String invoice_Code) {
        this.invoice_Code = invoice_Code;
    }

    public String getInvoice_Name() {
        return invoice_Name;
    }

    public void setInvoice_Name(String invoice_Name) {
        this.invoice_Name = invoice_Name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getExempt_Flag() {
        return exempt_Flag;
    }

    public void setExempt_Flag(String exempt_Flag) {
        this.exempt_Flag = exempt_Flag;
    }

    public String getCRC32() {
        return CRC32;
    }

    public void setCRC32(String CRC32) {
        this.CRC32 = CRC32;
    }

    @Override
    public String toString() {
        return "Tax{" +
                "id='" + id + '\'' +
                ", number='" + number + '\'' +
                ", invoice_Code='" + invoice_Code + '\'' +
                ", invoice_Name='" + invoice_Name + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", rate='" + rate + '\'' +
                ", exempt_Flag='" + exempt_Flag + '\'' +
                ", CRC32='" + CRC32 + '\'' +
                '}';
    }
}
