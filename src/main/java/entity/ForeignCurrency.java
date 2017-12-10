package entity;

public class ForeignCurrency {
    /**
     * 外币
     * number外币序号；abbreviation外币名称；symbol外币标识符号；SymbolDirection符号方向；
     * ExchangeRate汇率；ThousandSeparator ASCII码值逗号；CentSeparator ASCII码值点；
     * DecimalPlaces小数点后的位数；flag 标志位；code
     */
    private String id;
    private String number;
    private String code;
    private String name;
    private String abbreviation;
    private String symbol;
    private String symbolDirection;
    private String thousandSeparator;
    private String centSeparator;
    private String decimalPlaces;
    private String exchangeRate;
    private String flag;
    private String Current;

    public ForeignCurrency() {
        this.id = "";
        this.number = "";
        this.code = "";
        this.name = "";
        this.abbreviation = "";
        this.symbol = "";
        this.symbolDirection = "";
        this.thousandSeparator = "";
        this.centSeparator = "";
        this.decimalPlaces = "";
        this.exchangeRate = "";
        this.flag = "";
        this.Current = "";
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbolDirection() {
        return symbolDirection;
    }

    public void setSymbolDirection(String symbolDirection) {
        this.symbolDirection = symbolDirection;
    }

    public String getThousandSeparator() {
        return thousandSeparator;
    }

    public void setThousandSeparator(String thousandSeparator) {
        this.thousandSeparator = thousandSeparator;
    }

    public String getCentSeparator() {
        return centSeparator;
    }

    public void setCentSeparator(String centSeparator) {
        this.centSeparator = centSeparator;
    }

    public String getDecimalPlaces() {
        return decimalPlaces;
    }

    public void setDecimalPlaces(String decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCurrent() {
        return Current;
    }

    public void setCurrent(String current) {
        Current = current;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ForeignCurrency{" +
                "id='" + id + '\'' +
                ", number='" + number + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", abbreviation='" + abbreviation + '\'' +
                ", symbol='" + symbol + '\'' +
                ", symbolDirection='" + symbolDirection + '\'' +
                ", thousandSeparator='" + thousandSeparator + '\'' +
                ", centSeparator='" + centSeparator + '\'' +
                ", decimalPlaces='" + decimalPlaces + '\'' +
                ", exchangeRate='" + exchangeRate + '\'' +
                ", flag='" + flag + '\'' +
                ", Current='" + Current + '\'' +
                '}';
    }
}
