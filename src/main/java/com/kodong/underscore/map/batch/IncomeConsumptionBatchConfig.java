package com.kodong.underscore.map.batch;

import com.kodong.underscore.map.data.GlobalData;
import com.kodong.underscore.map.data.ncmcnsmp.NcmCnsmp;
import com.kodong.underscore.map.entity.*;
import com.kodong.underscore.map.repository.AdministrativeDistrictRepository;
import com.kodong.underscore.map.repository.BusinessAttractionRepository;
import com.kodong.underscore.map.repository.IncomeConsumptionRepository;
import com.kodong.underscore.map.util.DataCheck;
import com.kodong.underscore.map.util.ServiceName;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Configuration
@Slf4j
public class IncomeConsumptionBatchConfig {

    @Bean
    public Step incomeConsumptionStep(JobRepository jobRepository,
                        PlatformTransactionManager transactionManager,
                        ItemReader<NcmCnsmp> incomeConsumptionReader,
                        ItemProcessor<NcmCnsmp, IncomeConsumption> incomeConsumptionProcessor,
                        ItemWriter<IncomeConsumption> incomeConsumptionItemWriter) {
        return new StepBuilder("incomeConsumptionStep", jobRepository)
                .<NcmCnsmp, IncomeConsumption>chunk(10, transactionManager)
                .reader(incomeConsumptionReader)
                .processor(incomeConsumptionProcessor)
                .writer(incomeConsumptionItemWriter)
                // itemListener 추가하는 메서드(로깅용)
                //.listener(storeItemWriteListener())
                .build();
    }

    @Bean
    public FlatFileItemReader<NcmCnsmp> incomeConsumptionReader() {
        FlatFileItemReader<NcmCnsmp> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource(ServiceName.IncomeConsumption.getCsvFileName()));
        reader.setLinesToSkip(1); // 첫 번째 줄(헤더) 건너뛰기

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(",");
        tokenizer.setNames(ServiceName.IncomeConsumption.getDataNames());

        BeanWrapperFieldSetMapper<NcmCnsmp> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(NcmCnsmp.class);

        DefaultLineMapper<NcmCnsmp> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        reader.setLineMapper(lineMapper);
        return reader;
    }

    @Bean
    public JpaItemWriter<IncomeConsumption> incomeConsumptionItemWriter(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<IncomeConsumption> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    @Bean
    public ItemProcessor<NcmCnsmp, IncomeConsumption> incomeConsumptionProcessor(AdministrativeDistrictRepository administrativeDistrictRepository, IncomeConsumptionRepository incomeConsumptionRepository, DataCheck dataCheck) {
        return ncmCnsmp -> {
            // 여기에서 AdministrativeDistrict 조회 로직을 구현
            // 예를 들면, stor 객체에서 adstrdCode(행정동 코드)를 이용하여 AdministrativeDistrict 객체를 찾는 로직

            // 행정동 변경이 csv에 적용이 안되어 있을 경우를 대비해 변경확인 후 변경 적용하는 부분
            NcmCnsmp checkedNcmCnsmp = dataCheck.updateNcmCnsmp(ncmCnsmp);
            AdministrativeDistrict dong = administrativeDistrictRepository
                    .findByAdministrativeCode(checkedNcmCnsmp.getAdstrdCode())
                    .orElse(null);

            // 행정동이 null일 경우 확인용 로그
            if(dong == null){
                log.info("AdministrativeCode : {}    AdministrativeName : {}",checkedNcmCnsmp.getAdstrdCode(),checkedNcmCnsmp.getAdstrdCodeName());
            }

            Optional<IncomeConsumption> existing = incomeConsumptionRepository.findByStandardYearQuarterCodeAndAdministrativeDistrict(
                    checkedNcmCnsmp.getStandardYearQuarterCode(), dong
            );

            // convertToStore 메서드를 호출하여 Stor 객체를 Store 엔티티로 변환
            return existing.orElseGet(()->checkedNcmCnsmp.convertToIncomeConsumption(dong, checkedNcmCnsmp));
        };
    }

    @Bean
    public RepositoryItemReader<IncomeConsumption> incomeConsumptionItemReader(
            IncomeConsumptionRepository repository) {

        // RepositoryItemReader 설정
        RepositoryItemReader<IncomeConsumption> reader = new RepositoryItemReader<>();
        reader.setRepository(repository);
        reader.setMethodName("findAll");
        reader.setPageSize(100); // 페이지 크기 설정
        reader.setSort(Collections.singletonMap("id", Sort.Direction.ASC)); // 정렬 기준 설정

        return reader;
    }

    @Bean
    public ItemProcessor<IncomeConsumption, BusinessAttraction> incomeConsumptionItemProcessor(
            BusinessAttractionRepository businessAttractionRepository, GlobalData globalData) {
        return incomeConsumption -> {
            // GlobalData에서 임계값 가져오기
            BusinessAttraction attraction = null;
            List<Long> thresholds = globalData.getIncomeConsumptionThresholds();
            int score = calculateScore(incomeConsumption.getFoodExpenditureAmount(), thresholds);
            for(ServiceIndustry serviceIndustry : globalData.getServiceIndustryList()) {

                // BusinessAttraction 엔티티 조회
                // 여기서는 예시로 administrativeDistrict와 serviceIndustry의 ID를 사용합니다.
                // 실제 구현 시에는 이를 통해 BusinessAttraction 인스턴스를 식별할 수 있어야 합니다.
                BusinessAttractionId id = BusinessAttractionId.builder()
                        .administrativeDistrictId(incomeConsumption.getAdministrativeDistrict())
                        .serviceIndustryId(serviceIndustry)
                        .standardYearQuarterCode(globalData.getStandardYearQuarterCode())
                        .build();

                // Repository에서 BusinessAttraction 엔티티 조회
                Optional<BusinessAttraction> optionalAttraction = businessAttractionRepository.findById(id);

                // Optional이 비어있으면 continue를 사용하여 루프의 다음 반복으로 넘어갑니다.
                if (optionalAttraction.isEmpty()) {
                    continue;
                }

                attraction = optionalAttraction.get();

                attraction.updateIncomeConsumptionScore(score);

            }

            return attraction;
        };
    }

    private int calculateScore(long foodExpenditureAmount, List<Long> thresholds) {
        if (foodExpenditureAmount <= thresholds.get(0)) {
            return 5;
        } else if (foodExpenditureAmount <= thresholds.get(1)) {
            return 10;
        } else {
            return 15;
        }
    }
}
