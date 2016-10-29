package per.sumit.syncUtil;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext("applicationContext.xml");
		LocationBasedCopier locBaseCopier = (LocationBasedCopier) appCtx.getBean("locationBasedCopier");
		locBaseCopier.copy();
	}

}
