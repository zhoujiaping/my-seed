package seed.spring.beans;

import org.junit.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import cn.howso.deeplan.perm.model.User;
import cn.howso.deeplan.perm.service.AuthenService;

public class BeanWrapperTest {
    @Test
    public void test() throws ClassNotFoundException, InstantiationException, IllegalAccessException{
        Class<?> clazz = Class.forName("cn.howso.deeplan.server.account.model.User");
        Object obj = clazz.newInstance();
        BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(obj);
        wrapper.setPropertyValue("name", "eminem");
        wrapper.setPropertyValue("password","...");
        Assert.isTrue(wrapper.getPropertyValue("name").equals("eminem"));
    }
    @Test
    public void test2(){
        ClassPathXmlApplicationContext cxt = new ClassPathXmlApplicationContext("application-context.xml");
        AuthenService userService = cxt.getBean(AuthenService.class);
        User user = new User();
        user.setName("zhou");
        user.setPassword("123456");
        User authenUser = userService.authen(user );
        Assert.isTrue(authenUser.getName().equals("zhou"));
        cxt.close();
    }
}
