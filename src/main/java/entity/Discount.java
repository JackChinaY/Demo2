package entity;

public class Discount {
	/**
	 * 折扣加成及发票限额
	 * IncreaseRate折扣+；DecreaseRate折扣-；ReceiptUpperPriceLimit发票限额；id；
	 * ReportPassword；
	 * ProgramPassword；ServicePassword
	 */
		private String id;
		private String IncreaseRate;
		private String DecreaseRate;
		private String ReportPassword;
		private String ProgramPassword;
		private String ServicePassword;
		private String ReceiptUpperPriceLimit;
		public Discount() {
			this.id = "";
			this.IncreaseRate = "";
			this.DecreaseRate = "";
			this.ReportPassword = "";
			this.ProgramPassword = "";
			this.ServicePassword = "";
			this.ReceiptUpperPriceLimit = "";
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getIncreaseRate() {
			return IncreaseRate;
		}
		public void setIncreaseRate(String increaseRate) {
			IncreaseRate = increaseRate;
		}
		public String getDecreaseRate() {
			return DecreaseRate;
		}
		public void setDecreaseRate(String decreaseRate) {
			DecreaseRate = decreaseRate;
		}
		public String getReportPassword() {
			return ReportPassword;
		}
		public void setReportPassword(String reportPassword) {
			ReportPassword = reportPassword;
		}
		public String getProgramPassword() {
			return ProgramPassword;
		}
		public void setProgramPassword(String programPassword) {
			ProgramPassword = programPassword;
		}
		public String getServicePassword() {
			return ServicePassword;
		}
		public void setServicePassword(String servicePassword) {
			ServicePassword = servicePassword;
		}
		public String getReceiptUpperPriceLimit() {
			return ReceiptUpperPriceLimit;
		}
		public void setReceiptUpperPriceLimit(String receiptUpperPriceLimit) {
			ReceiptUpperPriceLimit = receiptUpperPriceLimit;
		}
		@Override
		public String toString() {
			return "Discount [id=" + id + ", IncreaseRate=" + IncreaseRate
					+ ", DecreaseRate=" + DecreaseRate + ", ReportPassword="
					+ ReportPassword + ", ProgramPassword=" + ProgramPassword
					+ ", ServicePassword=" + ServicePassword
					+ ", ReceiptUpperPriceLimit=" + ReceiptUpperPriceLimit
					+ "]";
		}
		
}
