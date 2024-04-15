package com.kodong.underscore.map.batch;

import com.kodong.underscore.map.data.GlobalData;
import com.kodong.underscore.map.entity.*;
import com.kodong.underscore.map.repository.AdministrativeDistrictRepository;
import com.kodong.underscore.map.repository.BusinessAttractionRepository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.*;

@Configuration
public class BusinessAttractionBatchConfig {

    @Bean
    public Step businessAttractionInitStep(JobRepository jobRepository,
                                           PlatformTransactionManager transactionManager,
                                           ItemReader<AdministrativeDistrict> administrativeDistrictRepositoryItemReader,
                                           ItemProcessor<AdministrativeDistrict, List<BusinessAttraction>> businessAttractionProcessor,
                                           ItemWriter<List<BusinessAttraction>> businessAttractionItemListWriter) {
        return new StepBuilder("businessAttractionInitStep",jobRepository)
                .<AdministrativeDistrict, List<BusinessAttraction>>chunk(10,transactionManager)
                .reader(administrativeDistrictRepositoryItemReader)
                .processor(businessAttractionProcessor)
                .writer(businessAttractionItemListWriter)
                .build();

    }

    @Bean
    public Step businessAttractionUpdateFlpopScoreStep(JobRepository jobRepository,
                                                       PlatformTransactionManager transactionManager,
                                                       ItemReader<FloatingPopulation> floatingPopulationItemReader,
                                                       ItemProcessor<FloatingPopulation,BusinessAttraction> floatingPopulationItemProcessor,
                                                       ItemWriter<BusinessAttraction> businessAttractionItemWriter) {
        return new StepBuilder("businessAttractionUpdateFlpopScoreStep",jobRepository)
                .<FloatingPopulation,BusinessAttraction>chunk(10,transactionManager)
                .reader(floatingPopulationItemReader)
                .processor(floatingPopulationItemProcessor)
                .writer(businessAttractionItemWriter)
                .build();
    }

    @Bean
    public Step businessAttractionUpdateIncomeConsumptionScoreStep(JobRepository jobRepository,
                                                       PlatformTransactionManager transactionManager,
                                                       ItemReader<IncomeConsumption> incomeConsumptionItemReader,
                                                       ItemProcessor<IncomeConsumption,BusinessAttraction> incomeConsumptionItemProcessor,
                                                       ItemWriter<BusinessAttraction> businessAttractionItemWriter) {
        return new StepBuilder("businessAttractionUpdateIncomeConsumptionScoreStep",jobRepository)
                .<IncomeConsumption,BusinessAttraction>chunk(10,transactionManager)
                .reader(incomeConsumptionItemReader)
                .processor(incomeConsumptionItemProcessor)
                .writer(businessAttractionItemWriter)
                .build();
    }

    @Bean
    public Step businessAttractionUpdateIndexQuarterlyQuotientScoreStep(JobRepository jobRepository,
                                                          PlatformTransactionManager transactionManager,
                                                          ItemReader<IndexQuarterlyQuotient> indexQuarterlyQuotientItemReader,
                                                          ItemProcessor<IndexQuarterlyQuotient,BusinessAttraction> indexQuarterlyQuotientItemProcessor,
                                                          ItemWriter<BusinessAttraction> businessAttractionItemWriter) {
        return new StepBuilder("businessAttractionUpdateIndexQuarterlyQuotientScoreStep",jobRepository)
                .<IndexQuarterlyQuotient,BusinessAttraction>chunk(10,transactionManager)
                .reader(indexQuarterlyQuotientItemReader)
                .processor(indexQuarterlyQuotientItemProcessor)
                .writer(businessAttractionItemWriter)
                .build();
    }

    @Bean
    public Step businessAttractionUpdateSellingScoreStep(JobRepository jobRepository,
                                                          PlatformTransactionManager transactionManager,
                                                          ItemReader<Selling> sellingItemReader,
                                                          ItemProcessor<Selling,BusinessAttraction> sellingItemProcessor,
                                                          ItemWriter<BusinessAttraction> businessAttractionItemWriter) {
        return new StepBuilder("businessAttractionUpdateSellingScoreStep",jobRepository)
                .<Selling,BusinessAttraction>chunk(10,transactionManager)
                .reader(sellingItemReader)
                .processor(sellingItemProcessor)
                .writer(businessAttractionItemWriter)
                .build();
    }

    @Bean
    public Step businessAttractionUpdateResidentPopulationScoreStep(JobRepository jobRepository,
                                                          PlatformTransactionManager transactionManager,
                                                          ItemReader<ResidentPopulation> residentPopulationItemReader,
                                                          ItemProcessor<ResidentPopulation,BusinessAttraction> residentPopulationItemProcessor,
                                                          ItemWriter<BusinessAttraction> businessAttractionItemWriter) {
        return new StepBuilder("businessAttractionUpdateResidentPopulationScoreStep",jobRepository)
                .<ResidentPopulation,BusinessAttraction>chunk(10,transactionManager)
                .reader(residentPopulationItemReader)
                .processor(residentPopulationItemProcessor)
                .writer(businessAttractionItemWriter)
                .build();
    }

    @Bean
    public Step businessAttractionUpdateStoreScoreStep(JobRepository jobRepository,
                                                          PlatformTransactionManager transactionManager,
                                                          ItemReader<Store> storeItemReader,
                                                          ItemProcessor<Store,BusinessAttraction> storeItemProcessor,
                                                          ItemWriter<BusinessAttraction> businessAttractionItemWriter) {
        return new StepBuilder("businessAttractionUpdateStoreScoreStep",jobRepository)
                .<Store,BusinessAttraction>chunk(10,transactionManager)
                .reader(storeItemReader)
                .processor(storeItemProcessor)
                .writer(businessAttractionItemWriter)
                .build();
    }

    @Bean
    public ItemProcessor<AdministrativeDistrict, List<BusinessAttraction>> businessAttractionProcessor(GlobalData globalData, BusinessAttractionRepository businessAttractionRepository) {
        return administrativeDistrict -> {
            // 모든 ServiceIndustry 정보를 데이터베이스에서 불러옵니다.
            List<ServiceIndustry> serviceIndustries = globalData.getServiceIndustryList();

            // 결과 BusinessAttraction 객체 리스트를 초기화합니다.
            List<BusinessAttraction> businessAttractions = new ArrayList<>();

            // 기준 연도/분기 코드를 설정합니다. 실제 사용 사례에 맞게 조정하세요.
            String standardYearQuarterCode = globalData.getStandardYearQuarterCode();

            // 각 AdministrativeDistrict에 대해 모든 ServiceIndustry와 조합하여 BusinessAttraction 객체를 생성합니다.
            for (ServiceIndustry serviceIndustry : serviceIndustries) {
                BusinessAttraction businessAttraction = new BusinessAttraction(
                        administrativeDistrict,
                        serviceIndustry,
                        standardYearQuarterCode
                );

                // 만약 이 로직으로 생성시 중복이 확인되면 이걸 활성화 하자
                /*
                BusinessAttractionId id = BusinessAttractionId.builder()
                        .serviceIndustryId(serviceIndustry)
                        .administrativeDistrictId(administrativeDistrict)
                        .standardYearQuarterCode(standardYearQuarterCode).build();
                Optional<BusinessAttraction> existing = businessAttractionRepository.findById(id);
                if (existing.isPresent()) {}
                 */


                businessAttractions.add(businessAttraction);
            }

            return businessAttractions;
        };
    }

    @Bean
    public ItemWriter<List<BusinessAttraction>> businessAttractionItemListWriter(BusinessAttractionRepository repository) {
        return items -> {
            for (List<BusinessAttraction> chunk : items) {
                repository.saveAll(chunk);
            }
        };
    }

    @Bean
    public JpaItemWriter<BusinessAttraction> businessAttractionItemWriter(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<BusinessAttraction> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

}
