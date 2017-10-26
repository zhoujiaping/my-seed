package cn.howso.deeplan.framework.config;

import javax.annotation.PostConstruct;

import org.apache.shiro.util.Assert;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class Conf implements ApplicationContextAware{
	private static int initCount;
	private ApplicationContext app;
	public Conf(){
		initCount++;
		Assert.isTrue(initCount==1,"配置有问题，Component被创建了多次");
	}
	@Value("${app.name}")
	private String appname;
	@Value("${app.author}")
	private String appauthor;
	@PostConstruct
	public void init(){
		System.out.println("init");
		System.out.println(this);
	}
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		app = context;
	}

}
