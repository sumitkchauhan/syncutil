package per.sumit.syncUtil.scheduling;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SchedulerImplTest {

	@Test
	public void testSingleCommandScheduled() {
		SchedulerImpl schedulerImpl = new SchedulerImpl(1);
		CommandImpl cmd = new CommandImpl();
		schedulerImpl.scheduleExecution(cmd, 100);
		try {
			Thread.sleep(1000);
			schedulerImpl.shutDown();
			Thread.sleep(500);
			assertTrue("Message called unexpected no of times="+cmd.getNoOfTimesCalled(),cmd.getNoOfTimesCalled()<=12 && cmd.getNoOfTimesCalled() >=8);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testMultipleCommandScheduledExecutingWithProperInterval() {
		SchedulerImpl schedulerImpl = new SchedulerImpl(1);
		CommandImpl cmd = new CommandImpl();
		CommandImpl cmd1 = new CommandImpl();
		schedulerImpl.scheduleExecution(cmd, 100);
		schedulerImpl.scheduleExecution(cmd1, 100);
		try {
			Thread.sleep(1000);
			schedulerImpl.shutDown();
			Thread.sleep(500);
			assertTrue("Message called unexpected no of times="+cmd.getNoOfTimesCalled(),cmd.getNoOfTimesCalled()<=12 && cmd.getNoOfTimesCalled() >=8);
			assertTrue("Message called unexpected no of times="+cmd1.getNoOfTimesCalled(),cmd1.getNoOfTimesCalled()<=12 && cmd1.getNoOfTimesCalled() >=8);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testMultipleCommandScheduledExecutingWithProperIntervalLongThreads() {
		SchedulerImpl schedulerImpl = new SchedulerImpl(1);
		CommandImpl cmd = new CommandImpl(100);
		CommandImpl cmd1 = new CommandImpl(100);
		schedulerImpl.scheduleExecution(cmd, 1);
		schedulerImpl.scheduleExecution(cmd1, 1);
		try {
			Thread.sleep(1000);
			schedulerImpl.shutDown();
			Thread.sleep(500);
			assertTrue("Message called unexpected no of times="+cmd.getNoOfTimesCalled(),cmd.getNoOfTimesCalled()<=7 && cmd.getNoOfTimesCalled() >=3);
			assertTrue("Message called unexpected no of times="+cmd1.getNoOfTimesCalled(),cmd1.getNoOfTimesCalled()<=7 && cmd1.getNoOfTimesCalled() >=3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testMultipleCommandScheduledExecutingWithProperIntervalLongThreadsMultiThreads() {
		SchedulerImpl schedulerImpl = new SchedulerImpl(2);
		CommandImpl cmd = new CommandImpl(100);
		CommandImpl cmd1 = new CommandImpl(100);
		schedulerImpl.scheduleExecution(cmd, 1);
		schedulerImpl.scheduleExecution(cmd1, 1);
		try {
			Thread.sleep(1000);
			schedulerImpl.shutDown();
			Thread.sleep(500);
			assertTrue("Message called unexpected no of times="+cmd.getNoOfTimesCalled(),cmd.getNoOfTimesCalled()<=12 && cmd.getNoOfTimesCalled() >=8);
			assertTrue("Message called unexpected no of times="+cmd1.getNoOfTimesCalled(),cmd1.getNoOfTimesCalled()<=12 && cmd1.getNoOfTimesCalled() >=8);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private static class CommandImpl implements Command {
		private final long sleepTime;
		private List<Long> intervals= new ArrayList<>();
		private Long lastExecutionTime = System.currentTimeMillis();
		public CommandImpl() {
			this(0);
		}
		public CommandImpl(long executionTime) {
			this.sleepTime = executionTime;
		}
		@Override
		public void execute() {
			long currentTime = System.currentTimeMillis();
			intervals.add(currentTime - lastExecutionTime);
			lastExecutionTime = currentTime;
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public int getNoOfTimesCalled(){
			return intervals.size();
		}
	}

}
