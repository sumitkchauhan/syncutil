package per.sumit.syncUtil.scheduling;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SchedulerImpl implements Scheduler {
	private final ScheduledExecutorService scheduledExecutorService;

	public SchedulerImpl(int noOfThreads) {
		scheduledExecutorService = Executors.newScheduledThreadPool(noOfThreads);
	}

	@Override
	public void scheduleExecution(Command commandToSchedule, long fixedDelay) {
		// Can't schedule for 0 interval, hence changing to 1
		scheduledExecutorService.scheduleAtFixedRate(new CommandRunnable(commandToSchedule), 0,
				(fixedDelay != 0) ? fixedDelay : 1, TimeUnit.MILLISECONDS);
	}

	@Override
	public void shutDown() {
		scheduledExecutorService.shutdown();
	}

	private static class CommandRunnable implements Runnable {

		private final Command commandToRun;

		public CommandRunnable(Command commandToRun) {
			this.commandToRun = commandToRun;
		}

		@Override
		public void run() {
			commandToRun.execute();
		}

	}

}
