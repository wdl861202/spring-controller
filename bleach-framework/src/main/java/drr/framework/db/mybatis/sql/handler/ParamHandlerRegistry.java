
package drr.framework.db.mybatis.sql.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParamHandlerRegistry {

	@Autowired
	private Map<String, ParamHandler> handlers;

	public Map<String, ParamHandler> handlers() {
		return handlers;
	}
}
