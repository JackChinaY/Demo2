package entity;

public class Department {
	/**
	 * 部类信息
	 * number部类编号；price部类价格；name部类名称；vaxindex税种索引；upperlimit上限；
	 * pluno；subgroup；flag标志位
	 * 
	 */
		private String id;
		private String deptNo;
		private String pluNo;

		public Department() {
			this.deptNo = "";
			this.pluNo = "";
		}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public String getPluNo() {
		return pluNo;
	}

	public void setPluNo(String pluNo) {
		this.pluNo = pluNo;
	}
}
