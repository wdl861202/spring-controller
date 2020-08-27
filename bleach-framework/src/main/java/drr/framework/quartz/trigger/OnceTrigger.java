
package drr.framework.quartz.trigger;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.ScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Component;

import drr.constant.common.PaddingConst;
import drr.constant.common.QuartzTriggerConst;
import drr.framework.quartz.entity.TaskContext;

@Component(QuartzTriggerConst.QUARTZ_ONCE_TRIGGER)
public class OnceTrigger extends AbstractCronTrigger {

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Trigger getResult(Date startTime, ScheduleBuilder scheduleBuilder) {
		/**
		 * 1、如果启动时间已过，则返回null<br>
		 * 2、如果启动时间没过，则在规定时间启动
		 */
		if (startTime.toInstant().isBefore(Instant.now())) {
			return null;
		} else {
			return TriggerBuilder.newTrigger().withSchedule(scheduleBuilder).startNow().build();
		}
	}

	@Override
	protected CronScheduleBuilder getScheduleBuilder(TaskContext taskContext, Integer hour, Integer minute) {
		Date effectiveDate = taskContext.getEffectiveDate();
		LocalDate localDate = effectiveDate.toInstant().atZone(ZoneId.of("CTT", ZoneId.SHORT_IDS)).toLocalDate();

		StringBuilder sb = new StringBuilder();
		// 0 1 1 1 1 ? 2020
		// year range 1970-2199
		sb.append("0 ").append(minute).append(PaddingConst.BLANK).append(hour).append(PaddingConst.BLANK).append(localDate.getDayOfMonth()).append(PaddingConst.BLANK).append(localDate.getMonthValue())
				.append(" ? ").append(localDate.getYear());
		return CronScheduleBuilder.cronSchedule(sb.toString());
	}
}
