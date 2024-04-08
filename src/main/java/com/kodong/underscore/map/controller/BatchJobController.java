package com.kodong.underscore.map.controller;


import com.kodong.underscore.map.service.ScoreApiService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchJobController {

    private final JobLauncher jobLauncher;
    private final Job job;

    @Autowired
    public BatchJobController(ScoreApiService scoreApiService, JobLauncher jobLauncher, @Qualifier("processDataInsertJob") Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    @GetMapping("/run-batch-job")
    public String runBatchJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(job, jobParameters);
        return "Batch job has been invoked";
    }
}
