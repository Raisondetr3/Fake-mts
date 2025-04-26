package ru.itmo.node_a_core.config;

import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import ru.itmo.node_a_core.job.CodeDeletionJob;
import ru.itmo.node_a_core.job.TariffContinuationJob;

@Configuration
public class QuartzConfig {
    @Bean
    public JobDetailFactoryBean tariffContinuationJobDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(TariffContinuationJob.class);
        factoryBean.setDurability(true);
        return factoryBean;
    }

    @Bean
    public JobDetailFactoryBean codeDeletionJobJobDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(CodeDeletionJob.class);
        factoryBean.setDurability(true);
        return factoryBean;
    }

    @Bean
    public CronTriggerFactoryBean tariffContinuationTrigger(JobDetail tariffContinuationJobDetail) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(tariffContinuationJobDetail);
        factoryBean.setStartDelay(0);
        // At 00:00:00am, on the 1st day, every month
        factoryBean.setCronExpression("0 0 0 1 * ? *");
        return factoryBean;
    }

    @Bean
    public CronTriggerFactoryBean codeDeletionJobTrigger(JobDetail codeDeletionJobJobDetail) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(codeDeletionJobJobDetail);
        factoryBean.setStartDelay(0);
//        At second :00 of every minute
        factoryBean.setCronExpression("0 * * ? * * *");
        return factoryBean;
    }
}
