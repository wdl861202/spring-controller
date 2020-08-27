package drr.security.oa;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;



public class OAResponseTransformer {

	private static Logger log = LoggerFactory.getLogger(OAResponseTransformer.class);

	public OALoginResponse transformLogin(String response) throws Exception {
		log.info("收到登录返回报文：" + response);
		Gson gson = new Gson();
		OALoginResponse loginResponse = gson.fromJson(response, OALoginResponse.class);
		return loginResponse;
	}
}
