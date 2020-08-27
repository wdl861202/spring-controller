
package drr.framework.quartz.trigger;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TriggerRegistry {

	@Autowired
	private Map<String, ITrigger> triggers = new HashMap<>();

	public ITrigger getTrigger(String key) {
		return triggers.get(key);
	}
}
