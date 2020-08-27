
package drr.framework.quartz.entity;

import java.util.Date;
import java.util.List;

import drr.constant.task.TaskCycleTypeEnum;

public class TaskContext {

	private Date effectiveDate;

	private String triggerTime;

	private TaskCycleTypeEnum taskCycle;

	private List<String> taskCycleDetails;

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getTriggerTime() {
		return triggerTime;
	}

	public void setTriggerTime(String triggerTime) {
		this.triggerTime = triggerTime;
	}

	public TaskCycleTypeEnum getTaskCycle() {
		return taskCycle;
	}

	public void setTaskCycle(TaskCycleTypeEnum taskCycle) {
		this.taskCycle = taskCycle;
	}

	public List<String> getTaskCycleDetails() {
		return taskCycleDetails;
	}

	public void setTaskCycleDetails(List<String> taskCycleDetails) {
		this.taskCycleDetails = taskCycleDetails;
	}

}
