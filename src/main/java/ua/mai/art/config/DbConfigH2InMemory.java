package ua.mai.art.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/*
 *
 */
@Configuration
@EnableTransactionManagement
@Profile("db-h2-in-memory")
public class DbConfigH2InMemory {
  //have infrastructure related beans like DataSource, JNDI, etc.
//  @Bean
//  public DataSource dataSource(){
//    EmbeddedDatabaseBuilder builder =
//        new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2);//in-memory
//    builder.addScript("schema.scripts");
//    builder.addScript("data.scripts");
//   return builder.build();
//  }

//  @Bean //implementation
//  public PlatformTransactionManager transactionManager(){
//    return new DataSourceTransactionManager(dataSource());
//  }
}
