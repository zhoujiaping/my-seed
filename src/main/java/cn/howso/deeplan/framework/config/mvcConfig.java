package cn.howso.deeplan.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

import cn.howso.deeplan.framework.exception.ExceptionResolver;

//@Configuration
public class mvcConfig {
	@Bean
	public HandlerExceptionResolver handlerExceptionResolver() {
		return new ExceptionResolver();
	}
}
