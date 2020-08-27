
package drr.framework.quartz.trigger;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.CronScheduleBuilder;
import org.springframework.stereotype.Component;

import drr.constant.common.PaddingConst;
import drr.constant.common.PunctuationConst;
import drr.constant.common.QuartzTriggerConst;
import drr.framework.quartz.entity.TaskContext;

@Component(QuartzTriggerConst.QUARTZ_WEEK_TRIGGER)
public class WeekTrigger extends AbstractCronTrigger {

	private String getWeeks(TaskContext taskContext) {
		List<String> taskCycleDetails = taskContext.getTaskCycleDetails();
		if (CollectionUtils.isNotEmpty(taskCycleDetails)) {
			return taskCycleDetails.parallelStream().map(i -> i.substring(i.length() - 1)).collect(Collectors.joining(","));
		}
		return PunctuationConst.QUESTRION;
	}

	@Override
	protected CronScheduleBuilder getScheduleBuilder(TaskContext taskContext, Integer hour, Integer minute) {
		StringBuilder sb = new StringBuilder();
		// 0 1 1 * * ?
		sb.append("0 ").append(minute).append(PaddingConst.BLANK).append(hour).append(" * * ").append(getWeeks(taskContext)).append(PaddingConst.BLANK);
		return CronScheduleBuilder.cronSchedule(sb.toString());
	}
}
