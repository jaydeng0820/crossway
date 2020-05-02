package crossway.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContext implements InitializingBean, ApplicationContextAware {

    protected static ApplicationContext appContext;

    public static ApplicationContext getAppContext() {
        return appContext;
    }

    public static <T> T getBean(Class<T> requiredType) {
        return appContext.getBean(requiredType);
    }

    public static <T> T getBean(String beanName) {
        return (T) appContext.getBean(beanName);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return appContext.getBean(name, requiredType);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContext.appContext = applicationContext;
    }

}
