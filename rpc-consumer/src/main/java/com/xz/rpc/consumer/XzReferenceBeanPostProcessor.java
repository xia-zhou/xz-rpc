package com.xz.rpc.consumer;

import com.xz.rpc.consumer.annotation.XzReference;
import com.xz.rpc.core.constant.XzConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangsong
 */
@Slf4j
public class XzReferenceBeanPostProcessor implements ApplicationContextAware, BeanClassLoaderAware, BeanFactoryPostProcessor {

    private ApplicationContext applicationContext;

    private ClassLoader classLoader;

    private final Map<String, BeanDefinition> xkReferenceBeanDefinition = new HashMap<>();

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            String beanClassName = beanDefinition.getBeanClassName();
            if (beanClassName == null) {
                continue;
            }
            Class<?> clazz = ClassUtils.resolveClassName(beanClassName, classLoader);
            ReflectionUtils.doWithFields(clazz, (field) -> {
                XzReference xzReference = field.getAnnotation(XzReference.class);
                if (xzReference == null) {
                    return;
                }
                BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(
                        XzReferenceBean.class);
                beanDefinitionBuilder.setInitMethodName(XzConstants.INIT_METHOD_NAME);
                beanDefinitionBuilder.addPropertyValue("interfaceType", field.getType());
                beanDefinitionBuilder.addPropertyValue("serviceName", field.getName());
                BeanDefinition referenceBeanDefinition = beanDefinitionBuilder.getBeanDefinition();
                xkReferenceBeanDefinition.put(field.getName(), referenceBeanDefinition);
            });
        }
        BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) beanFactory;
        xkReferenceBeanDefinition.forEach((beanName, referenceBeanDefinition) -> {
            if (applicationContext.containsBean(beanName)) {
                throw new IllegalArgumentException("spring context already has a bean name :" + beanName);
            }
            beanDefinitionRegistry.registerBeanDefinition(beanName, referenceBeanDefinition);
            log.info("register reference bean to spring context:{}", beanName);
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
