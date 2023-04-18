package com.bala.auto_lock_release;

import org.jbpm.services.api.service.ServiceRegistry;
import org.jbpm.services.api.UserTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskManager implements java.io.Serializable {

    static final long serialVersionUID = 1L;

    public TaskManager() {
    }

    private static final Logger logger = LoggerFactory
			.getLogger("com.bala.auto_lock_release");

	public static void unlock(final java.util.List assignedTasks) {
		final ServiceRegistry serviceRegistry = ServiceRegistry.get();
		final UserTaskService taskService = (UserTaskService) serviceRegistry
				.service(ServiceRegistry.USER_TASK_SERVICE);
				
		for (Object csvRecord : assignedTasks) {
			try {
				String[] taskInfo = csvRecord.toString().split(",");
				Long taskId = Long.parseLong(taskInfo[0]);
				String deploymentId = taskInfo[1];
				String taskOwner = taskInfo[2];
				logger.info("Releasing task " + taskId + " owned by "
						+ taskOwner);
				taskService.release(deploymentId, taskId, taskOwner);
			} catch (Exception e) {
				logger.error("Error while releasing task {} ", csvRecord, e);
			}
		}
	}


}