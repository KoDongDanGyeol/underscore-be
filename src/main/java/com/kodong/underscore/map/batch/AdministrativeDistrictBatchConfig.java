package com.kodong.underscore.map.batch;


import com.kodong.underscore.map.data.AdministrativeDistrictDTO;
import com.kodong.underscore.map.entity.AdministrativeDistrict;
import com.kodong.underscore.map.repository.AdministrativeDistrictRepository;
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
public class AdministrativeDistrictBatchConfig {

    @Bean
    public Step administrativeDistrictStep(JobRepository jobRepository,
                                           PlatformTransactionManager transactionManager,
                                           ItemReader<AdministrativeDistrictDTO> administrativeDistrictDTOItemReader,
                                           ItemProcessor<AdministrativeDistrictDTO, AdministrativeDistrict> administrativeDistrictProcessor,
                                           ItemWriter<AdministrativeDistrict> administrativeDistrictItemWriter) {
        return new StepBuilder("administrativeDistrictStep", jobRepository)
                .<AdministrativeDistrictDTO, AdministrativeDistrict>chunk(10, transactionManager)
                .reader(administrativeDistrictDTOItemReader)
                .processor(administrativeDistrictProcessor)
                .writer(administrativeDistrictItemWriter)
                // itemListener 추가하는 메서드(로깅용)
                //.listener(storeItemWriteListener())
                .build();
    }

    @Bean
    public FlatFileItemReader<AdministrativeDistrictDTO> administrativeDistrictDTOItemReader() {
        FlatFileItemReader<AdministrativeDistrictDTO> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource(DataConfig.DistrictData.getCsvFileName()));
        reader.setLinesToSkip(1); // 첫 번째 줄(헤더) 건너뛰기

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(",");
        tokenizer.setNames(DataConfig.DistrictData.getColumnNames());

        BeanWrapperFieldSetMapper<AdministrativeDistrictDTO> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(AdministrativeDistrictDTO.class);

        DefaultLineMapper<AdministrativeDistrictDTO> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        reader.setLineMapper(lineMapper);
        return reader;
    }

    @Bean
    public JpaItemWriter<AdministrativeDistrict> administrativeDistrictItemWriter(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<AdministrativeDistrict> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    @Bean
    public ItemProcessor<AdministrativeDistrictDTO, AdministrativeDistrict> administrativeDistrictProcessor(AdministrativeDistrictRepository administrativeDistrictRepository) {
        return dto -> {
            // 여기서는 dto가 AdministrativeDistrictDTO 타입의 인스턴스입니다.
            // 이미 db에 해당 데이터가 존재하는지 확인
            Optional<AdministrativeDistrict> existing = administrativeDistrictRepository.findByAdministrativeCode(dto.getAdministrativeCode());
            // 있으면 그대로 넘겨주고, 없으면 convert해서 넘겨줌
            return existing.orElseGet(() -> dto.convertToAdministrativeDistrict(dto));
        };
    }

    @Bean
    public RepositoryItemReader<AdministrativeDistrict> administrativeDistrictRepositoryItemReader(AdministrativeDistrictRepository administrativeDistrictRepository) {
        RepositoryItemReader<AdministrativeDistrict> reader = new RepositoryItemReader<>();

        reader.setRepository(administrativeDistrictRepository);
        reader.setMethodName("findAll"); // Repository의 메소드 이름

        // 정렬 조건 설정
        Map<String, Sort.Direction> sortMap = new HashMap<>();
        sortMap.put("id", Sort.Direction.ASC); // 예: ID 기준 오름차순 정렬
        reader.setSort(sortMap);

        // 페이지 크기 설정 (예: Chunk 크기와 일치)
        reader.setPageSize(10);

        return reader;
    }


}
