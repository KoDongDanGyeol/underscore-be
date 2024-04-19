package com.kodong.underscore.map.batch;

import com.kodong.underscore.map.service.ScoreApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@Slf4j
public class SequentialJobListener implements JobExecutionListener {

    private final JobLauncher jobLauncher;
    private final Job secondJob;
    private final Job thirdJob;
    private final ScoreApiService scoreApiService;

    public SequentialJobListener(JobLauncher jobLauncher,@Lazy @Qualifier("businessAttractionInitJob") Job secondJob,
                                 @Qualifier("businessAttractionUpdateJob") Job thirdJob, ScoreApiService scoreApiService) {
        this.jobLauncher = jobLauncher;
        this.secondJob = secondJob;
        this.thirdJob = thirdJob;
        this.scoreApiService = scoreApiService;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("Job execution: {}", jobExecution.getJobInstance().getJobName());
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("Job execution completed");
            try {
                log.info("jobExecution.getJobInstance().getJobName(): {}", jobExecution.getJobInstance().getJobName());
                // 첫 번째 작업이 완료되면, 서비스 업데이트와 두 번째 작업 실행
                if (jobExecution.getJobInstance().getJobName().equals("processDataInsertJob")) {
                    log.info("BUSINESSATTRACTION INIT JOB started");
                    scoreApiService.putAllServiceIndustryDataInGlobalData();
                    runJob(secondJob, jobExecution.getJobParameters());
                }
                // 두 번째 작업이 완료되면, 추가 서비스 메서드 호출 후 세 번째 작업 실행
                if (jobExecution.getJobInstance().getJobName().equals("businessAttractionInitJob")) {
                    log.info("BUSINESSATTRACTION UPDATE JOB started");
                    scoreApiService.updateThresholds();
                    runJob(thirdJob, jobExecution.getJobParameters());
                }
                // 일시적으로 Store 값 넣는 부분 추가했을 때 필요한 로직
                if (jobExecution.getJobInstance().getJobName().equals("businessAttractionInitJob")) {
                    log.info("BUSINESSATTRACTION UPDATE JOB started");
                    scoreApiService.updateThresholds();
                    runJob(thirdJob, jobExecution.getJobParameters());
                }

                if (jobExecution.getJobInstance().getJobName().equals("temporaryStoreDataInputJob")) {
                    log.info("BUSINESSATTRACTION UPDATE JOB started");
                    scoreApiService.updateThresholds();
                    runJob(thirdJob, jobExecution.getJobParameters());
                }

            } catch (Exception e) {
                System.out.println("Error during job execution: " + e.getMessage());
            }
        }
    }

    private void runJob(Job job, JobParameters oldParameters) throws Exception {
        JobParameters newJobParameters = new JobParametersBuilder(oldParameters)
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(job, newJobParameters);
    }

}
