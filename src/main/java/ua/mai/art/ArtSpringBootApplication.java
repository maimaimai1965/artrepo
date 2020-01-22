package ua.mai.art;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;


/**
 *
 * Содержит профили:
 * <ul>
 * <li>Определяющие БД:
 *     <ul>
 *     <li><b>db-h2-in-memory</b> - база данных H2, располагается в памяти. Настройки профиля находятся в файле
 *         <code>resources\application-db-h2-in-memory.properties</code>.<br>
 *         При старте приложения выполняются скрипты создания и наполнения БД, находящиеся в файлах
 *         <code>resources\scripts\sql92\schema.sql</code> и <code>main\resources\scripts\sql92\data.sql</code>.
 *     <li><b>db-h2-in-file</b> - база данных H2, располагаемая в памяти. Настройки профиля находятся в
 *         <code>resources\application-db-h2-in-file.properties</code>.<br>
 *         Должна быть создана схема art и в ней должны уже быть выполнены скрипты создания и наполнения БД, находящиеся
 *         <code>в файлах resources\scripts\sql92\schema.sql</code> и <code>main\resources\scripts\sql92\data.sql</code>.
 *     <li><b>db-oracle</b> - база данных Oracle. Настройки профиля находятся в
 *         <code>resources\application-db-oracle.properties</code>.<br>
 *         В нужной схеме должны уже быть выполнены скрипты создания и наполнения БД, находящиеся в файлах
 *         <code>resources\scripts\oracle\schema.sql</code> и <code>main\resources\scripts\oracle\data.sql</code>.
 *     <li><b>db-postgres-in-file</b> - база данных PostgreSQL, располагаемая в памяти. Настройки профиля находятся в
 *         <code>resources\application-db-postgres.properties</code>.<br>
 *         Должна быть создана схема art и в ней должны уже быть выполнены скрипты создания и наполнения БД, находящиеся
 *         в файлах <code>resources\scripts\sql92\schema.sql</code> и <code>main\resources\scripts\sql92\data.sql</code>.
 *     </ul>
 * </li>
 * <li>Определяющие тип задания DataSource через JNDI:
 *     <ul>
 *     <li><b>db-h2-jndi</b> - свойства JNDI для нахождения DataSource для БД H2. Настройки профиля находятся в
 *         <code>resources\application-db-h2-jndi.properties</code>.
 *     <li><b>db-oracle-jndi</b> - свойства JNDI для нахождения DataSource для БД Oracle. Настройки профиля находятся в
 *         <code>resources\application-oracle-h2-jndi.properties</code>.
 *     <li><b>db-postgres-jndi</b> - свойства JNDI для нахождения DataSource для БД PostgreSQL. Настройки профиля
 *         находятся в <code>resources\application-db-postgres-jndi.properties</code>.
 *     </ul>
 * </li>
 * <li>Для настройки DataSource и пула соединений:
 *     <ul>
 *     <li><b>ds-jndi-embedded</b> - используется пул соединений из embedded Tomcat (даже если приложение развернуто на
 *         внешнем Tomcat). С этим профилем работает {@link ua.mai.art.config.DbConfigJndiEmbeddedTomcat}.<br>
 *         Этот профиль можно использовать для разработки в Idea при задания DataSource через JNDI c запуском в
 *         "Run Dashboard" (но не в "Application Server").
 *     <li><b>ds-test</b> - используется для тестирования DataSource.
 *     </ul>
 * </li>
 * Сочетания этих профилей описано в файле <code>resources\application.properties</code>, где устанавливается дефолтный
 * профиль.
 * </ul>
 */
@ServletComponentScan
//Не используем Security
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
//@SpringBootApplication
public class ArtSpringBootApplication extends  SpringBootServletInitializer {
//public class ArtApplication  implements CommandLineRunner {

  private Logger logger = LoggerFactory.getLogger(this.getClass());


  public ArtSpringBootApplication() {
    super();
  }

  public static void main(String[] args) {

    SpringApplication app = new SpringApplication(ArtSpringBootApplication.class);
    ConfigurableApplicationContext context = app.run(args);

    int i = 5;
  }

//Для:
//public class ArtApplication  implements CommandLineRunner {
//  @Override
//  public void run(String... args) throws Exception {
//    logger.info("run ArtSpringBootApplication");
//  }

}

