package com.xxl.job.admin.core.conf;

import com.xxl.job.admin.core.schedule.XxlJobDynamicScheduler;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * @Description:
 * @author: :MaYong
 * @Date: 2018/4/28 17:10
 */
@Configuration
public class QuartzSchedulerConfig {
    @Autowired
    DataSource dataSource;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(){
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setOverwriteExistingJobs(true);
        // 延时启动
        factory.setStartupDelay(20);
        // 加载quartz数据源配置
        factory.setDataSource(dataSource);
        factory.setQuartzProperties(quartzProperties());
        return factory;
    }
    @Bean
    public Scheduler scheduler() {
        return schedulerFactoryBean().getScheduler();
    }

    /**
     * 加载quartz数据源配置
     *
     * @return
     * @throws IOException
     */
    @Bean
    public Properties quartzProperties(){
        try {
            PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
            propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
            propertiesFactoryBean.afterPropertiesSet();
            return propertiesFactoryBean.getObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
