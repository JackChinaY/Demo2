package entity;

public class Buyer {
    /**
     * 客户信息
     */
    private String id;
    private String number;
    private String name;
    private String tpin;
    private String vat;
    private String tel;
    private String address;
    private String bankAccount;
    private String remark;
    private String reserved;

    public Buyer() {
        this.id = "";
        this.number = "";
        this.name = "";
        this.tpin = "";
        this.vat = "";
        this.tel = "";
        this.address = "";
        this.bankAccount = "";
        this.remark = "";
        this.reserved = "";
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTpin() {
        return tpin;
    }

    public void setTpin(String tpin) {
        this.tpin = tpin;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    @Override
    public String toString() {
        return "Buyer{" +
                "id='" + id + '\'' +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", tpin='" + tpin + '\'' +
                ", vat='" + vat + '\'' +
                ", tel='" + tel + '\'' +
                ", address='" + address + '\'' +
                ", bankAccount='" + bankAccount + '\'' +
                ", remark='" + remark + '\'' +
                ", reserved='" + reserved + '\'' +
                '}';
    }
}
