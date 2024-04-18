package com.kodong.underscore.map.controller;

import com.kodong.underscore.map.service.ScoreApiService;
import com.kodong.underscore.map.util.AdministrativeDistrictLocationMaker;
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
    private final AdministrativeDistrictLocationMaker administrativeDistrictLocationMaker;

    private final Job administrativeLocationPutJob;

    public BatchJobController(JobLauncher jobLauncher,
                              @Qualifier("processDataInsertJob") Job processDataInsertJob,
                              @Qualifier("administrativeLocationPutJob") Job administrativeLocationPutJob,
                              ScoreApiService scoreApiService, AdministrativeDistrictLocationMaker administrativeDistrictLocationMaker) {
        this.jobLauncher = jobLauncher;
        this.processDataInsertJob = processDataInsertJob;
        this.scoreApiService = scoreApiService;
        this.administrativeDistrictLocationMaker = administrativeDistrictLocationMaker;
        this.administrativeLocationPutJob = administrativeLocationPutJob;
    }

    @GetMapping("/run-batch-job")
    public String runBatchJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        administrativeDistrictLocationMaker.refreshSGISAccessToken();
        log.info("ProcessDATAINSERTJOB started");
        jobLauncher.run(processDataInsertJob, jobParameters);
        return "Batch job has been invoked";
    }

    @GetMapping("/adjob")
    public String runDupJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        administrativeDistrictLocationMaker.refreshSGISAccessToken();
        jobLauncher.run(administrativeLocationPutJob, jobParameters);
        return "Batch job has been invoked";
    }
}
