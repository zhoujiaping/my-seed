package cn.howso.deeplan.framework.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cn.howso.deeplan.util.LogUtil;

@Component
public class Conf{
	@Value("${app.name}")
	private String appname;
	@Value("${app.author}")
	private String appauthor;
	@Value("${upload.dir}")
	private String uploadDir;
	
	private Logger logger = LogUtil.getLogger();
	
	@PostConstruct
	public void init(){
		logger.info("init Conf");
	}
	
    public String getUploadDir() {
        return uploadDir;
    }
}
