package cn.howso.deeplan.framework.config;

import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

import cn.howso.deeplan.framework.exception.ExceptionResolver;
import cn.howso.deeplan.perm.cache.RedisCache;
import cn.howso.deeplan.perm.cache.RedisCacheManager;
import cn.howso.deeplan.perm.filter.LoginFilter;
import cn.howso.deeplan.perm.filter.PermissionFilter;
import cn.howso.deeplan.perm.realm.MyRealm;
import cn.howso.deeplan.perm.service.AuthenService;
import cn.howso.deeplan.perm.service.AuthorService;
import cn.howso.deeplan.perm.service.UriPermService;
import cn.howso.deeplan.perm.session.dao.MyShiroSessionDao;
import cn.howso.deeplan.perm.session.dao.MyShiroSessionRespository;
import cn.howso.deeplan.perm.session.dao.RedisShiroSessionRespositoryImpl;
import redis.clients.jedis.JedisPool;

/**
 * 基于spring的javaConfig、xml、annotation方式对比
 * 1、javaConfig方式，更有利于编译期检查。能够更大程度的在源代码编译时发现配置错误，因为有编译器帮助。
 * 2、xml方式，在某些场景会实现annotation无法实现的功能。
 * 比如封装的公共库，有些类是可选的。 公共库只提供类而不提供配置，在需要配置的时候用户自己去配置。特别是第三方库例如shiro，它就是这样做的。
 * xml方式可读性更强，更方便从全局掌控。 
 * 3、annotation方式，非常方便，在某个类是可选的时候，不得不在需要的时候修改源码提供或者去掉相应的注解。
 *  在需要初始化两个实例，它们采用不同配置时，用annotation无法实现。
 * 4、总结。 为了在各种场景充分利用各种方式的优势，总结出最佳实践。 将xml、annotation、javaConfig三种方式结合起来用。
 * 在配置第三方库中的类时，用xml方式。
 * 一来第三方库中的类的路径几乎不会改变，用xml方式几乎不会遇到拷贝配置时需要手动修改类路径的情况。
 * 并且可以方便配置其他属性，例如DefaultWebSessionManager的globalSessionTimeout属性。
 *  二来我们无法向其添加注解，无法使用annotation方式。
 * 在配置项目中必选的自定义组件（如绝大部分service、controller、dao）时，用annotation方式。使项目中的配置更方便。
 * 在配置项目中同一个类的多个组件或者封装到公共包中的自定义组件（如HandlerExceptionResolver）时，用javaConfig方式。
 *  使拷贝配置时不需要手动修改类的路径。 这样做的优缺点：
 * 1、第三方bean的配置和自定义bean的配置分离，增强可读性。 
 * 2、修改包名时，不容易导致配置错误。这样的错误在编译器可以发现。
 * 3、三种方式结合，能够满足各种情况的需求。
 * 4、在将第三方库提供的类更改为自定义类时，需要将该bean的配置方式做改变。
 */
@Configuration
public class appConfig implements ApplicationContextAware {

    private ApplicationContext app;

    @Bean
    public LoginFilter loginFilter() {
        return new LoginFilter();
    }

    @Bean
    public PermissionFilter permFilter() {
        PermissionFilter permFilter = new PermissionFilter();
        permFilter.setUriPermService(app.getBean(UriPermService.class));
        permFilter.setDataCache(dataCache());
        return permFilter;
    }
    @Bean
    public MyRealm myRealm(){
        MyRealm realm = new MyRealm();
        AuthenService authenService = app.getBean(AuthenService.class);//authenService();
        realm.setAuthenService(authenService);
        AuthorService authorService = app.getBean(AuthorService.class);
        realm.setAuthorService(authorService);
        realm.setAuthenticationCachingEnabled(true);
        realm.setAuthorizationCachingEnabled(true);
        realm.setAuthenticationCacheName("authenCache");
        realm.setAuthorizationCacheName("authorCache");
        return realm;
    }
    @Bean
    public RedisCacheManager redisCacheManager() {
        RedisCacheManager cm = new RedisCacheManager();
        cm.setAuthenCache(authenCache());
        cm.setAuthorCache(authorCache());
        return cm;
    }

    @Bean
    public RedisCache authenCache() {
        RedisCache cache = new RedisCache();
        cache.setPrefix("authen.");
        cache.setJedisPool(app.getBean("jedisPool", JedisPool.class));
        return cache;
    }

    @Bean
    public RedisCache authorCache() {
        RedisCache cache = new RedisCache();
        cache.setPrefix("author.");
        cache.setJedisPool(app.getBean("jedisPool", JedisPool.class));
        return cache;
    }
    @Bean
    public RedisCache dataCache(){
        RedisCache cache = new RedisCache();
        cache.setPrefix("data.");
        cache.setJedisPool(app.getBean("jedisPool", JedisPool.class));
        return cache;
    }
    @Bean
    public MyShiroSessionRespository myShiroSessionRespository() {
        RedisShiroSessionRespositoryImpl rep = new RedisShiroSessionRespositoryImpl();
        rep.setPrefix("session.");
        rep.setJedisPool(app.getBean("jedisPool", JedisPool.class));
        return rep;
    }

    @Bean
    public AbstractSessionDAO mySessionDao() {
        MyShiroSessionDao dao = new MyShiroSessionDao();
        dao.setShiroSessionRespository(myShiroSessionRespository());
        return dao;
    }

    @Bean
    public HandlerExceptionResolver handlerExceptionResolver() {
        return new ExceptionResolver();
    }
    /*@Bean
    public AuthenService authenService(){
        return new AuthenService();
    }
    @Bean
    public AuthorService authorService(){
        return new AuthorService();
    }*/
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        app = applicationContext;
    }

}
