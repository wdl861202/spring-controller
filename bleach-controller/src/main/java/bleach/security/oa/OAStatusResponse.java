package drr.security.oa;

public class OAStatusResponse {
	private String respCode;
	private String respMsg;
	private String totalRecord;

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}

	public String getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(String totalRecord) {
		this.totalRecord = totalRecord;
	}

	@Override
	public String toString() {
		return "StatusBean [respCode=" + respCode + ", respMsg=" + respMsg + ", totalRecord=" + totalRecord + "]";
	}
}
