package com.xxl.job.admin.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
/**
 * @Description:
 * @author: :MaYong
 * @Date: 2018/4/28 17:36
 */
/*@Configuration*/
public class MyBeanDefinitionRegistryPostProcessor  implements BeanDefinitionRegistryPostProcessor {
    private ScopeMetadataResolver scopeMetadataResolver = new AnnotationScopeMetadataResolver();
    private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        //registerBean(registry, "myJobFactory", MyJobFactory.class);
        registerBean(registry, "quartzScheduler", SchedulerFactoryBean.class);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition myJobFactory = beanFactory.getBeanDefinition("myJobFactory");
        BeanDefinition dataSource = beanFactory.getBeanDefinition("druidDataSource");
        // 这里可以设置属性，例如
        BeanDefinition bd = beanFactory.getBeanDefinition("quartzScheduler");
        MutablePropertyValues mpv =  bd.getPropertyValues();
        mpv.add("jobFactory" ,myJobFactory);
        mpv.add("dataSource",dataSource);
        mpv.addPropertyValue("applicationContextSchedulerContextKey",
                "applicationContextKey");
    }

    private void registerBean(BeanDefinitionRegistry registry, String name, Class<?> beanClass){
        AnnotatedGenericBeanDefinition abd = new AnnotatedGenericBeanDefinition(beanClass);
        ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(abd);
        abd.setScope(scopeMetadata.getScopeName());
        // 可以自动生成name
        String beanName = (name != null ? name : this.beanNameGenerator.generateBeanName(abd, registry));
        AnnotationConfigUtils.processCommonDefinitionAnnotations(abd);
        BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(abd, beanName);
        BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, registry);
    }
}
