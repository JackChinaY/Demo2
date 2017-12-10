package entity;

public class HeaderOfInvoice {
	/**
	 * 发票抬头 抬头、税号、地址、电话、开户行、银行账号、其他、标志位
	 */
	private String id;
	private String taitou;
	private String shuihao;
	private String dizhi;
	private String dianhua;
	private String kaihuhang;
	private String yinhangzhanghao;
	private String qita;
	private String flag;

	public HeaderOfInvoice() {
		this.id = "";
		this.taitou = "";
		this.shuihao = "";
		this.dizhi = "";
		this.dianhua = "";
		this.kaihuhang = "";
		this.yinhangzhanghao = "";
		this.qita = "";
		this.flag = "";
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTaitou() {
		return taitou;
	}

	public void setTaitou(String taitou) {
		this.taitou = taitou;
	}

	public String getShuihao() {
		return shuihao;
	}

	public void setShuihao(String shuihao) {
		this.shuihao = shuihao;
	}

	public String getDizhi() {
		return dizhi;
	}

	public void setDizhi(String dizhi) {
		this.dizhi = dizhi;
	}

	public String getDianhua() {
		return dianhua;
	}

	public void setDianhua(String dianhua) {
		this.dianhua = dianhua;
	}

	public String getKaihuhang() {
		return kaihuhang;
	}

	public void setKaihuhang(String kaihuhang) {
		this.kaihuhang = kaihuhang;
	}

	public String getYinhangzhanghao() {
		return yinhangzhanghao;
	}

	public void setYinhangzhanghao(String yinhangzhanghao) {
		this.yinhangzhanghao = yinhangzhanghao;
	}

	public String getQita() {
		return qita;
	}

	public void setQita(String qita) {
		this.qita = qita;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "HeaderOfInvoice [id=" + id + ", taitou=" + taitou
				+ ", shuihao=" + shuihao + ", dizhi=" + dizhi + ", dianhua="
				+ dianhua + ", kaihuhang=" + kaihuhang + ", yinhangzhanghao="
				+ yinhangzhanghao + ", qita=" + qita + ", flag=" + flag + "]";
	}
	
	

}
