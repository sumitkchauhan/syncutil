package per.sumit.syncUtil;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
	/*	if(args.length==0){
			throw new IllegalArgumentException("Provide Configuration file path.");
		}*/
		ApplicationContext appCtx = new ClassPathXmlApplicationContext("applicationContext.xml");
		LocationBasedCopier locBaseCopier = (LocationBasedCopier) appCtx.getBean("locationBasedCopier");
		
	}

}
