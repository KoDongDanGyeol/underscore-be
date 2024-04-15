package com.kodong.underscore.map.batch;

import com.kodong.underscore.map.data.ServiceIndustryDTO;
import com.kodong.underscore.map.entity.ServiceIndustry;
import com.kodong.underscore.map.repository.ServiceIndustryRepository;
import com.kodong.underscore.map.util.DataConfig;
import jakarta.persistence.EntityManagerFactory;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Configuration
public class ServiceIndustryBatchConfig {
    @Bean
    public Step serviceIndustryStep(JobRepository jobRepository,
                                           PlatformTransactionManager transactionManager,
                                           ItemReader<ServiceIndustryDTO> serviceIndustryDTOItemReader,
                                           ItemProcessor<ServiceIndustryDTO, ServiceIndustry> serviceIndustryProcessor,
                                           ItemWriter<ServiceIndustry> serviceIndustryItemWriter) {
        return new StepBuilder("serviceIndustryStep", jobRepository)
                .<ServiceIndustryDTO, ServiceIndustry>chunk(10, transactionManager)
                .reader(serviceIndustryDTOItemReader)
                .processor(serviceIndustryProcessor)
                .writer(serviceIndustryItemWriter)
                // itemListener 추가하는 메서드(로깅용)
                //.listener(storeItemWriteListener())
                .build();
    }

    @Bean
    public FlatFileItemReader<ServiceIndustryDTO> serviceIndustryDTOItemReader() {
        FlatFileItemReader<ServiceIndustryDTO> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource(DataConfig.ServiceIndustry.getCsvFileName()));
        reader.setLinesToSkip(1); // 첫 번째 줄(헤더) 건너뛰기

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(",");
        tokenizer.setNames(DataConfig.ServiceIndustry.getColumnNames());

        BeanWrapperFieldSetMapper<ServiceIndustryDTO> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(ServiceIndustryDTO.class);

        DefaultLineMapper<ServiceIndustryDTO> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        reader.setLineMapper(lineMapper);
        return reader;
    }

    @Bean
    public JpaItemWriter<ServiceIndustry> serviceIndustryItemWriter(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<ServiceIndustry> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    @Bean
    public ItemProcessor<ServiceIndustryDTO, ServiceIndustry> serviceIndustryProcessor(ServiceIndustryRepository serviceIndustryRepository) {
        return dto -> {
            // 여기서는 dto가 AdministrativeDistrictDTO 타입의 인스턴스입니다.
            // convertToAdministrativeDistrict 메서드를 호출하여 AdministrativeDistrict 객체로 변환
            Optional<ServiceIndustry> existing = serviceIndustryRepository.findByServiceIndustryCode(dto.getServiceIndustryCode());
            return existing.orElseGet(() -> dto.convertToServiceIndustry(dto));
        };
    }

    @Bean
    public RepositoryItemReader<ServiceIndustry> serviceIndustryRepositoryItemReader(ServiceIndustryRepository serviceIndustryRepository) {
        RepositoryItemReader<ServiceIndustry> reader = new RepositoryItemReader<>();

        reader.setRepository(serviceIndustryRepository);
        reader.setMethodName("findAll"); // ServiceIndustryRepository의 메소드 이름

        // 정렬 조건 설정
        Map<String, Sort.Direction> sortMap = new HashMap<>();
        sortMap.put("id", Sort.Direction.ASC); // 예: ID 기준 오름차순 정렬
        reader.setSort(sortMap);

        // 페이지 크기 설정 (예: Chunk 크기와 일치)
        reader.setPageSize(10);

        return reader;
    }
}
