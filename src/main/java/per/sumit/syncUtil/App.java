package per.sumit.syncUtil;

import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Hello world!
 *
 */
public class App {
	private static final Pattern PTTRN_REPLACE = Pattern.compile("#.*\\n");
	public static void main(String[] args) throws IOException, InterruptedException {
		ApplicationContext appCtx = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	
	private static void printList(List<String> list){
		list.forEach(System.out::println);
	}
	public void myJob(){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Invoked");
	}
}
