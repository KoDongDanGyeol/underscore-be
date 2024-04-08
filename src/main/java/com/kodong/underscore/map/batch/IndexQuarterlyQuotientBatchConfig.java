package com.kodong.underscore.map.batch;

import com.kodong.underscore.map.data.ixqq.Ixqq;
import com.kodong.underscore.map.entity.AdministrativeDistrict;
import com.kodong.underscore.map.entity.IndexQuarterlyQuotient;
import com.kodong.underscore.map.repository.AdministrativeDistrictRepository;
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
public class IndexQuarterlyQuotientBatchConfig {
    @Bean
    public Step indexQuarterlyQuotientStep(JobRepository jobRepository,
                        PlatformTransactionManager transactionManager,
                        ItemReader<Ixqq> indexQuarterlyQuotientItemReader,
                        ItemProcessor<Ixqq, IndexQuarterlyQuotient> indexQuarterlyQuotientProcessor,
                        ItemWriter<IndexQuarterlyQuotient> indexQuarterlyQuotientItemWriter) {
        return new StepBuilder("indexQuarterlyQuotientStep", jobRepository)
                .<Ixqq, IndexQuarterlyQuotient>chunk(10, transactionManager)
                .reader(indexQuarterlyQuotientItemReader)
                .processor(indexQuarterlyQuotientProcessor)
                .writer(indexQuarterlyQuotientItemWriter)
                // itemListener 추가하는 메서드(로깅용)
                //.listener(storeItemWriteListener())
                .build();
    }

    @Bean
    public FlatFileItemReader<Ixqq> indexQuarterlyQuotientItemReader() {
        FlatFileItemReader<Ixqq> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource(ServiceName.IndexQuarterlyQuotient.getCsvFileName()));
        reader.setLinesToSkip(1); // 첫 번째 줄(헤더) 건너뛰기

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(",");
        tokenizer.setNames(ServiceName.IndexQuarterlyQuotient.getDataNames());

        BeanWrapperFieldSetMapper<Ixqq> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Ixqq.class);

        DefaultLineMapper<Ixqq> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        reader.setLineMapper(lineMapper);
        return reader;
    }

    @Bean
    public JpaItemWriter<IndexQuarterlyQuotient> indexQuarterlyQuotientItemWriter(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<IndexQuarterlyQuotient> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    @Bean
    public ItemProcessor<Ixqq, IndexQuarterlyQuotient> indexQuarterlyQuotientProcessor(AdministrativeDistrictRepository administrativeDistrictRepository) {
        return ixqq -> {
            // 여기에서 AdministrativeDistrict 조회 로직을 구현
            // 예를 들면, stor 객체에서 adstrdCode(행정동 코드)를 이용하여 AdministrativeDistrict 객체를 찾는 로직
            AdministrativeDistrict dong = administrativeDistrictRepository
                    .findByAdministrativeCode(ixqq.getAdstrdCode())
                    .orElse(null);

            // convertToStore 메서드를 호출하여 Stor 객체를 Store 엔티티로 변환
            return ixqq.convertToIndxQuarterlyQuotient(dong, ixqq);
        };
    }
}
