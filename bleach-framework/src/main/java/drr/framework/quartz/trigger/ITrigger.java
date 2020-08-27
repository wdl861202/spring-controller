
package drr.framework.quartz.trigger;

import org.quartz.Trigger;

import drr.framework.quartz.entity.TaskContext;

public interface ITrigger {

	Trigger getTrigger(TaskContext taskContext);
}
