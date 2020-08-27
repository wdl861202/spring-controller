
package drr.framework.quartz.trigger;

import org.quartz.CronScheduleBuilder;
import org.springframework.stereotype.Component;

import drr.constant.common.PaddingConst;
import drr.constant.common.QuartzTriggerConst;
import drr.framework.quartz.entity.TaskContext;

@Component(QuartzTriggerConst.QUARTZ_DAY_TRIGGER)
public class DayTrigger extends AbstractCronTrigger {

	@Override
	protected CronScheduleBuilder getScheduleBuilder(TaskContext taskContext, Integer hour, Integer minute) {
		StringBuilder sb = new StringBuilder();
		// 0 1 1 * * ?
		sb.append("0 ").append(minute).append(PaddingConst.BLANK).append(hour).append(" * * ").append(" ? ");
		return CronScheduleBuilder.cronSchedule(sb.toString());
	}

}
