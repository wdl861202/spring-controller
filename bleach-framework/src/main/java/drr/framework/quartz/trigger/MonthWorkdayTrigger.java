
package drr.framework.quartz.trigger;

import org.quartz.Trigger;
import org.springframework.stereotype.Component;

import drr.constant.common.QuartzTriggerConst;
import drr.framework.quartz.entity.TaskContext;

@Component(QuartzTriggerConst.QUARTZ_MONTH_WOKRDAY_TRIGGER)
public class MonthWorkdayTrigger extends DayTrigger {

	@Override
	public Trigger getTrigger(TaskContext taskContext) {

		return super.getTrigger(taskContext);
	}
}
