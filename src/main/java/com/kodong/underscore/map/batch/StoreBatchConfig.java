package com.kodong.underscore.map.batch;

import com.kodong.underscore.map.data.stor.Stor;
import com.kodong.underscore.map.entity.AdministrativeDistrict;
import com.kodong.underscore.map.entity.ServiceIndustry;
import com.kodong.underscore.map.entity.Store;
import com.kodong.underscore.map.repository.AdministrativeDistrictRepository;
import com.kodong.underscore.map.repository.ServiceIndustryRepository;
import com.kodong.underscore.map.util.ServiceName;
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
public class StoreBatchConfig {

    @Bean
    public Step storeStep(JobRepository jobRepository,
                          PlatformTransactionManager transactionManager,
                          ItemReader<Stor> storeItemReader,
                          ItemProcessor<Stor, Store> storeProcessor,
                          ItemWriter<Store> storeItemWriter) {
        return new StepBuilder("storeStep", jobRepository)
                .<Stor, Store>chunk(10, transactionManager)
                .reader(storeItemReader)
                .processor(storeProcessor)
                .writer(storeItemWriter)
                // itemListener 추가하는 메서드(로깅용)
                //.listener(storeItemWriteListener())
                .build();
    }

    @Bean
    public FlatFileItemReader<Stor> storeItemReader() {
        FlatFileItemReader<Stor> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource(ServiceName.Store.getCsvFileName()));
        reader.setLinesToSkip(1); // 첫 번째 줄(헤더) 건너뛰기

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(",");
        tokenizer.setNames(ServiceName.Store.getDataNames());

        BeanWrapperFieldSetMapper<Stor> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Stor.class);

        DefaultLineMapper<Stor> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        reader.setLineMapper(lineMapper);
        return reader;
    }

    @Bean
    public JpaItemWriter<Store> storeItemWriter(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<Store> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    @Bean
    public ItemProcessor<Stor, Store> storeProcessor(AdministrativeDistrictRepository administrativeDistrictRepository,
                                                     ServiceIndustryRepository serviceIndustryRepository) {
        return stor -> {
            // 여기에서 AdministrativeDistrict 조회 로직을 구현
            // 예를 들면, stor 객체에서 adstrdCode(행정동 코드)를 이용하여 AdministrativeDistrict 객체를 찾는 로직
            AdministrativeDistrict dong = administrativeDistrictRepository
                    .findByAdministrativeCode(stor.getAdstrdCode())
                    .orElse(null);

            ServiceIndustry industry = serviceIndustryRepository
                    .findByServiceIndustryCode(stor.getServiceIndustryCode())
                    .orElse(null);

            // convertToStore 메서드를 호출하여 Stor 객체를 Store 엔티티로 변환
            return stor.convertToStore(dong,industry, stor);
        };
    }

}
