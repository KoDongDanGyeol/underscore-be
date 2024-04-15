package com.kodong.underscore.map.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class BatchConfig {

    // TaskExecutor Bean 설정
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(7); // 병렬로 실행할 Step의 수 설정
        executor.setMaxPoolSize(7);
        executor.setQueueCapacity(25);
        executor.initialize();
        return executor;
    }

    @Bean
    public Job processDataInsertJob(JobRepository jobRepository,
                                    Step administrativeDistrictStep, Step serviceIndustryStep,
                                    Step storeStep, Step floatingPopulationStep, Step incomeConsumptionStep,
                                    Step indexQuarterlyQuotientStep, Step residentPopulationStep, Step sellingStep) {


        // 먼저 실행할 Step의 Flow를 정의
        Flow dataInputFlow = new FlowBuilder<Flow>("dataInut")
                .split(taskExecutor())
                .add(new FlowBuilder<Flow>("administrativeDongFlow").start(administrativeDistrictStep).end(),
                        new FlowBuilder<Flow>("serviceIndustryFlow").start(serviceIndustryStep).end())
                .build();

        Flow parallelSteps = new FlowBuilder<Flow>("parallelSteps")
                .split(taskExecutor())
                .add(new FlowBuilder<Flow>("storeFlow").start(storeStep).end(),
                        new FlowBuilder<Flow>("floatingPopulationFlow").start(floatingPopulationStep).end(),
                        new FlowBuilder<Flow>("incomeConsumptionFlow").start(incomeConsumptionStep).end(),
                        new FlowBuilder<Flow>("indexQuarterlyQuotientFlow").start(indexQuarterlyQuotientStep).end(),
                        new FlowBuilder<Flow>("residentPopulationFlow").start(residentPopulationStep).end(),
                        new FlowBuilder<Flow>("sellingFlow").start(sellingStep).end())
                .build();

        // 전체 Job Flow를 구성, 먼저 실행할 Step을 시작으로 하여 병렬 Step들이 실행되도록 함
        Flow jobFlow = new FlowBuilder<Flow>("jobFlow")
                .start(dataInputFlow)
                .next(parallelSteps)
                .end();


        return new JobBuilder("processDataInsertJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(jobFlow)
                .end()// storeStep은 Stor 처리 관련 Step
                .build();
    }

}
