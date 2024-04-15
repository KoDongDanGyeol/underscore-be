package com.kodong.underscore.map.controller;

import com.kodong.underscore.map.service.ScoreApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class BatchJobController {

    private final JobLauncher jobLauncher;
    private final Job processDataInsertJob;
    private final ScoreApiService scoreApiService;

    public BatchJobController(JobLauncher jobLauncher,
                              @Qualifier("processDataInsertJob") Job processDataInsertJob,
                              ScoreApiService scoreApiService) {
        this.jobLauncher = jobLauncher;
        this.processDataInsertJob = processDataInsertJob;
        this.scoreApiService = scoreApiService;

    }

    @GetMapping("/run-batch-job")
    public String runBatchJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        log.info("ProcessDATAINSERTJOB started");
        jobLauncher.run(processDataInsertJob, jobParameters);
        return "Batch job has been invoked";
    }
}
