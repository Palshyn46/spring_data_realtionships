package com.example.spring_data_relationships.junit5;

        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.ComponentScan;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
        import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

        import javax.sql.DataSource;

//@Configuration
//@ComponentScan("com.example.spring_data_relationships")
public class SpringJdbcConfig {

//    @Bean
//    public DataSource dataSource() {
//        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).addScript("classpath:jdbc/schema.sql").addScript("classpath:jdbc/test-data.sql").build();
//    }
}