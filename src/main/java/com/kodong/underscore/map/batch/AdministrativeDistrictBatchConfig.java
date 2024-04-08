package com.kodong.underscore.map.batch;


import com.kodong.underscore.map.data.AdministrativeDistrictDTO;
import com.kodong.underscore.map.entity.AdministrativeDistrict;
import com.kodong.underscore.map.util.DataConfig;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

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
    public ItemProcessor<AdministrativeDistrictDTO, AdministrativeDistrict> administrativeDistrictProcessor() {
        return dto -> {
            // 여기서는 dto가 AdministrativeDistrictDTO 타입의 인스턴스입니다.
            // convertToAdministrativeDistrict 메서드를 호출하여 AdministrativeDistrict 객체로 변환
            return dto.convertToAdministrativeDistrict(dto);
        };
    }


}
