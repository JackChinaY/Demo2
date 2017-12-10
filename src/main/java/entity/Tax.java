package entity;

public class Tax {
	/**
	 * 税率信息 number税种编号；vatrate税率值；name名字；upperlimit上限；flag标志位
	 */
	private String id;
	private String number;	
	private String name;
	private String vatrate;
	private String upperlimit;
	private String flag;
	
	public Tax() {
		this.id = "";
		this.number = "";
		this.name = "";
		this.vatrate = "";
		this.upperlimit = "";
		this.flag = "";
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
	
	public String getVatrate() {
		return vatrate;
	}

	public void setVatrate(String vatrate) {
		this.vatrate = vatrate;
	}

	public String getUpperlimit() {
		return upperlimit;
	}

	public void setUpperlimit(String upperlimit) {
		this.upperlimit = upperlimit;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "Tax [id=" + id + ", number=" + number + ", name=" + name
				+ ", vatrate=" + vatrate + ", upperlimit=" + upperlimit
				+ ", flag=" + flag + "]";
	}
}
