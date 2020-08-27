
package drr.framework.quartz.trigger;

import java.time.Duration;
import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.ScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import drr.constant.common.PunctuationConst;
import drr.framework.quartz.entity.TaskContext;

public abstract class AbstractCronTrigger implements ITrigger {

	@SuppressWarnings("rawtypes")
	@Override
	public Trigger getTrigger(TaskContext taskContext) {
		String triggerTime = taskContext.getTriggerTime();
		String[] split = triggerTime.split(PunctuationConst.COLON);
		Integer hour = Integer.valueOf(split[0]);
		Integer minute = Integer.valueOf(split[1]);

		Date effectiveDate = taskContext.getEffectiveDate();
		Date startTime = Date.from(effectiveDate.toInstant().plus(Duration.ofHours(hour)).plus(Duration.ofMinutes(minute)));

		ScheduleBuilder scheduleBuilder = getScheduleBuilder(taskContext, hour, minute);

		return getResult(startTime, scheduleBuilder);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Trigger getResult(Date startTime, ScheduleBuilder scheduleBuilder) {
		return TriggerBuilder.newTrigger().withSchedule(scheduleBuilder).startNow().build();
	}

	protected abstract CronScheduleBuilder getScheduleBuilder(TaskContext taskContext, Integer hour, Integer minute);

}
