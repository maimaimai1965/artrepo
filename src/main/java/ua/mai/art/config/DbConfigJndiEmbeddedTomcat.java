package ua.mai.art.config;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.catalina.Context;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ua.telesens.plu.log.StepLogRoot;


/**
 * Конфигурация для создания DataSource, который ищется по JNDI имени и использует Spring properties.
 * <p>
 * Используется если нужно найти DataSource по его JNDI имени (задан один из профилей <code>db-<i>имя_БД</i>-jndi</code>)
 * и задан профиль <code>ds-jndi-embedded/code>.<br>
 * Подробнее см. {@link ua.mai.art.ArtSpringBootApplication}.
 */
@Configuration
@EnableTransactionManagement
@Profile("ds-jndi-embedded")
public class DbConfigJndiEmbeddedTomcat {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public TomcatServletWebServerFactory tomcatFactory(
              @Value("${spring.datasource.jndi-name}")
              String jndiName,
              @Value("${spring.datasource.driver-class-name}")
              String driverClassName,
              @Value("${spring.datasource.url}")
              String url,
              @Value("${spring.datasource.username}")
              String user,
              @Value("${spring.datasource.password}")
              String password) {

        return new TomcatServletWebServerFactory() {

            @Override
            protected TomcatWebServer getTomcatWebServer(org.apache.catalina.startup.Tomcat tomcat) {
                tomcat.enableNaming();
                return super.getTomcatWebServer(tomcat);
            }

            /**
             *
             * @param context
             */
            @Override
            protected void postProcessContext(Context context) {
                StepLogRoot stepLogRoot =
                    new StepLogRoot(DbConfigJndiEmbeddedTomcat.this.logger, "ArtCheckDataSource.checkAfterAppStarted()");
                stepLogRoot.info("'ds-jndi-embedded' profile activated: jndiName = {}; driverClassName = {} url = {}; username = {}  []",
                                 jndiName, driverClassName, url, user);
                ContextResource resource = new ContextResource();
                resource.setProperty("factory", "org.apache.tomcat.jdbc.pool.DataSourceFactory");
                resource.setName(jndiName);
                resource.setType(DataSource.class.getName());
                resource.setProperty("driverClassName", driverClassName);
                resource.setProperty("url", url);
                resource.setProperty("username", user);
                resource.setProperty("password", password);
                context.getNamingResources().addResource(resource);
            }
        };
    }

    @Bean
    public DataSource dataSource(
                          @Value("${spring.datasource.jndi-name}")
                          String jndiName ) throws IllegalArgumentException, NamingException {
        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
        bean.setJndiName("java:/comp/env/" + jndiName);
        bean.setProxyInterface(DataSource.class);
        bean.setLookupOnStartup(true);
        bean.afterPropertiesSet();

        return (DataSource) bean.getObject();
    }

}
