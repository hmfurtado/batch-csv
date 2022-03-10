package com.hmfurtado.csv;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Slf4j
@EnableBatchProcessing
@Configuration
public class CopyTsvBatch {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<TitleDTO> reader() {

        return new FlatFileItemReaderBuilder<TitleDTO>()
                .name("personItemReader")
                .linesToSkip(1)
                .resource(new ClassPathResource("data.tsv"))
                .delimited()
                .quoteCharacter('\t')
                .delimiter("\t")
                .names(new String[]{"tconst", "titletype", "primarytitle", "originaltitle", "isadult", "startyear", "endyear", "runtimeminutes", "genres"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<TitleDTO>() {{
                    setTargetType(TitleDTO.class);
                }})
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<TitleDTO> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<TitleDTO>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO title_db " +
                        "(tconst, titletype, primarytitle, originaltitle, isadult, startyear, endyear, runtimeminutes, genres) " +
                        "VALUES " +
                        "(:tconst, :titleType, :primaryTitle, :originalTitle, :isAdult, :startYear, :endYear, :runtimeMinutes, :genres)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Job importTitlesIMDB(Step step1) {
        return jobBuilderFactory.get("importTitlesIMDB")
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<TitleDTO> writer) {
        return stepBuilderFactory.get("step1")
                .<TitleDTO, TitleDTO>chunk(1000)
                .reader(reader())
                .writer(writer)
                .build();
    }

}
