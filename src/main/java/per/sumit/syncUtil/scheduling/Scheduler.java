package per.sumit.syncUtil.scheduling;

/**
 * Schedules execution of a passed command
 * 
 * @author samurai
 *
 */
public interface Scheduler {

	/**
	 * Schedules execution of command
	 * 
	 * @param commandToSchedule
	 *            Command to schedule
	 * @param fixedDelay
	 *            Delay with which to execute after last execution finishing
	 */
	public void scheduleExecution(Command commandToSchedule, long fixedDelay);

	/**
	 * Shuts down scheduler
	 */
	public void shutDown();

}
