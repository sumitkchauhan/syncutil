package per.sumit.syncUtil.scheduling;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import per.sumit.syncUtil.Configuration;

public class ExecutionRegistry {

	private static final Map<Configuration, Long> lastSuccessfulExecutionRegistry = new ConcurrentHashMap<>();

	private ExecutionRegistry() {
		throw new RuntimeException("Illegal access");
	}

	public static void registerSuccessfulConfigExecution(Configuration configuration) {
		lastSuccessfulExecutionRegistry.put(configuration, System.currentTimeMillis());
	}

	public static long getTimeSinceLastExecution(Configuration configuration) {
		Long lastExecTime = lastSuccessfulExecutionRegistry.get(configuration);
		if (lastExecTime == null) {
			// program just started
			lastExecTime = 0l;
		}
		return System.currentTimeMillis() - lastExecTime;
	}
}
