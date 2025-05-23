package ru.itmo.node_a_core.config;

import org.quartz.CalendarIntervalScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
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
    public Trigger tariffContinuationTrigger(JobDetail tariffContinuationJobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(tariffContinuationJobDetail)
                .withIdentity("tariffContinuationTrigger", "billing")
                .startNow()
                .withSchedule(
                        CalendarIntervalScheduleBuilder
                                .calendarIntervalSchedule()
                                .withIntervalInMonths(1)
                )
                .build();
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
