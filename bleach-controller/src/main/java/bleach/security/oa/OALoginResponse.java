package drr.security.oa;


import com.google.gson.annotations.SerializedName;

public class OALoginResponse {

	@SerializedName("infobj")
	private OASessionResponse oaSessionResponse;
	@SerializedName("status")
	private OAStatusResponse oaStatusResponse;

	public OASessionResponse getOaSessionResponse() {
		return oaSessionResponse;
	}

	public OAStatusResponse getOaStatusResponse() {
		return oaStatusResponse;
	}

	public void setOaSessionResponse(OASessionResponse oaSessionResponse) {
		this.oaSessionResponse = oaSessionResponse;
	}

	public void setOaStatusResponse(OAStatusResponse oaStatusResponse) {
		this.oaStatusResponse = oaStatusResponse;
	}

}
