package cn.howso.deeplan.framework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Conf{
	@Value("${app.name}")
	private String appname;
	@Value("${app.author}")
	private String appauthor;
}
