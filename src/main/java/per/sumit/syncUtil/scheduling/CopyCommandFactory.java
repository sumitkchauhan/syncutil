package per.sumit.syncUtil.scheduling;

import per.sumit.syncUtil.Configuration;

public interface CopyCommandFactory {

	public CopyCommand createCopyCommandForConfig(Configuration configuration);
}
