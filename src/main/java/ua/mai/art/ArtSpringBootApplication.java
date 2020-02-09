package ua.mai.art;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import ua.mai.LogIdUtils;
import ua.mai.art.config.LogConfig;
import ua.mai.art.module.ArtModuleInfo;
import ua.telesens.plu.log.StepLogRequest;
import ua.telesens.plu.log.StepLogRoot;
import ua.telesens.plu.module.ModuleInfo;


/**
 * Информация о приложении описывается в классе {@link ArtModuleInfo}, для которого есть бин moduleInfo. В этом классе
 * устанавливается название приложения. Такое же название приложения прописывается в файле application.properties в
 * свойстве spring.application.name.
 *
 * <h1>Выполнение приложения</h1>
 * <p>
 * Приложение можно:
 * <ol>
 * <li>Запустить из командной строки. В этоим случае оно будет выполняться на встроенном Tomcat.<br>
 *     Пример команды:<br>
 *     <code>java -jar art.war --server.port=8555 --spring.profiles.active=db-h2-in-file,ds-check</code>
 * <li>Развернуть на отдельном Tomcat.<br>
 * </ol>
 * Список комбинаций профилей, используемых для выполнения, описан в <code>resources\application.properties</code>.
 * <p>
 * <h1>Работа с Базой Данных</h1>
 * <p>
 * Для работы с БД используем пул соединений. В pom.xml отключаем используемый по умолчанию пул HikariCP и подключаем
 * пул Аpache Tomcat.
 * <p>
 * Для работы с БД используются профили:
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
 * <li>Определяющие DataSource:
 *     <ul>
 *     <li>Определяющие необходимость нахождения DataSource по его JNDI имени:
 *         <ul>
 *         <li><b>db-h2-jndi</b> - JNDI имя для БД H2, которое находится в
 *             <code>resources\application-db-h2-jndi.properties</code>.
 *         <li><b>db-oracle-jndi</b> - JNDI имя для БД Oracle, которое находится в находится в
 *             <code>resources\application-oracle-h2-jndi.properties</code>.
 *         <li><b>db-postgres-jndi</b> - JNDI имя для БД PostgreSQL, которое находится в
 *             <code>resources\application-db-postgres-jndi.properties</code>.
 *         </ul>
 *         Если задан один из этих профилей, то модуль будет искать DataSource по JNDI имени.
 *     <li>Для настройки DataSource и пула соединений:
 *         <ul>
 *         <li><b>ds-jndi-embedded</b> - при поиске DataSource по JNDI имени нужно использовать пул соединений,
 *             созданный приложением. С этим профилем работает класс {@link ua.mai.art.config.DbConfigJndiEmbeddedTomcat}.<br>
 *             Используется совместно с профилями <code>db-<i>имя_БД</i>-jndi</code>.
 *             Этот профиль используется в Idea для проверки поиска DataSource через JNDI. При этом пул соединений
 *             создает само приложение, используя Spring properties. Возможен запуск только в "Run Dashboard" (но не в
 *             "Application Server").<br>
 *         <li><b>ds-check</b> - используется для проверки DataSource после старта приложения (см. {@link ArtCheckDataSource}).
 *         </ul>
 *     </ul>
 * </ul>
 * Сочетания этих профилей описано в файле <code>resources\application.properties</code>, где устанавливается дефолтный
 * профиль.
 *
 * <h2>Установка активных профилей для работы приложения.</h2>
 * <p>
 * Среди выбранных активных профилей должен быть профиль, определяющий БД - <code>db-<i>имя_БД</i></code>.<br>
 * Так же должны быть указаны профили:
 * <ul>
 * <li>При запуске на embedded Tomcat (запуск приложения из коммандной строки):<br>
 *     В этом случае приложение само создает пул соединений по Spring properties.
 *     Если же среди активных профилей задан профиль определяющий JNDI имя DataSource - <code>db-<i>имя_БД</i>-jndi</code>,
 *     то в этом случае необходимо так же указывать профиль <code>ds-jndi-embedded</code>. Без указания этого профиля
 *     приложение будет завершаться с ошибкой.
 * <li>При запуске на внешем Tomcat (развертывание приложения):
 *     <ul>
 *     <li>Если среди активных профилей задан профиль определяющий JNDI имя DataSource - <code>db-<i>имя_БД</i>-jndi</code>,
 *         то в этом случае используется пул соединений, созданный во внешнем Tomcat с параметрами ресурса,
 *         определенными в файле Tomcat <code>conf/context.xml</code>. Этот ресурс ищется по JNDI имени, которое
 *         задается этим профилем <code>db-<i>имя_БД</i>-jndi</code>. В файле <code>conf/context.xml</code> должен
 *         обязательно существовать ресурс c этим именем. Если он не наден, то приложение выдаст ошибку.<br>
 *         C этим профилем приложение нельзя запустить в Idea на "Application Servers".
 *     <li>Если же профиля <code>db-<i>имя_БД</i>-jndi</code> нет среди активных, то приложение само создает пул
 *         соединений по Spring properties.
 *     </ul>
 *     Пример для БД PostgreSQL с заданием JNDI имени:
 *     <ul>
 *     <li>активные профили: <code>db-postgres, db-postgres-jndi, ds-check</code>;
 *     <li>JNDI имя в файле <code>resources\application-db-postgres-jndi.properties</code>:
 *         <code>spring.datasource.jndi-name=jdbc/art_postgres</code>;
 *     <li>определение ресурса в файле Tomcat <code>conf/context.xml</code>:<br>
 *          <pre>
 *          &lt;Resource name="jdbc/art_postgres" auth="Container"
 *                    factory="org.apache.tomcat.jdbc.pool.DataSourceFactory"
 *                    type="org.apache.tomcat.jdbc.pool.DataSource"
 *                    driverClassName="org.postgresql.Driver"
 *                    url="jdbc:postgresql://127.0.0.1:5432/art"
 *                    username="postgres" password="postgres"
 *                    maxActive="10" maxIdle="5" minIdle="2" initialSize="2"
 *                    maxWait="30000"/&gt;
 *           </pre>
 *      </ul>
 *      В указанной в примере конфигурации в Idea нельзя запустить приложение на "Application Servers".<br>
 *      На "Application Servers" можно запустить приложение с активным профилем не содержащим профиль, задающий JNDI,
 *      например: <code>db-postgres, ds-check</code>. В этом случае будет создаваться пул соединений в самой программе
 *      по Spring properties.
 * </ul>
 * Если нужно, чтобы при старте приложения сразу производилось тетирование DataSource, то нужно указать профиль
 * <code>ds-check</code>.
 *
 * <h1>Логирование</h1>
 * <p>
 * Для логирования используется slf4j и log4j2. В pom.xml отключается дефолтная для Spring библиотека логирования
 * logback и подключается log4j2.<br>
 * Настраивается логирование в файле <code>log4g.xml</code>.<br>
 *
 * <h1>Логирование</h1>
 * В файле <code>log4g.xml</code> можно задать каталог к месту хранения лог-файлов. Этот каталог определяется property
 * c <code>name="log-path</code>".<br>
 * Доступно 2 варианта определения каталога:
 * <ol>
 * <li>По значению переменной среды CATALINA_HOME.<br>
 *     В этом случае файлы логов будут писаться в каталог, определенный CATALINA_HOME, к пути которого добавляются
 *     каталоги <code>logs/<i>имя-модуля</i></code> (имя приложения устанавливается свойством
 *     <code>spring.application.name</code> в файле <code>application.properties</code>). Переменную среды можно задать,
 *     например в bat файле командой <code>set "CATALINA_HOME=."</code>.<br>
 *     Этот вариант можно использовать для выполнения на встроенном и внешем Tomcat.<br>
 *     Если переменная CATALINA_HOME не задана, то приложение запустится с ошибками и логирование не будет выполняться.
 * <li>По свойству <code>application.log-path</code>.<br>
 *     В этом случае файлы логов будут писаться в каталог, определенный свойством <code>application.log-path</code>. По
 *     умолчанию это каталог <code>logs/art</code>. Это свойство можно переопределить при старте приложения.<br>
 *     Этот вариант можно использовать для выполнения на встроенном Tomcat.
 * </ol>
 * Для выбора нужного варианта его нужно раскомментировать в файле <code>log4g.xml</code>, а ненужный - закомментировать.
 * <ol>
 *
 * <h2>Задание логирования через AOP</h2>
 * <p>
 * Определение логируемых Java методов:
 * <ul>
 * <li>работающих с БД осуществляется в классе {@link ua.mai.art.aspect.LoggingAspect}, в котором задается pointcut
 *     {@link ua.mai.art.aspect.LoggingAspect#stepLogDbPointcut()}. Этот pointcut определяет логирование работы с
 *     репозиториями - он определяет все pubic методы в классах из пакета <code>ua.mai.art.repository</code>, содержащих
 *     в своем имени строку "Repository" , а Around Advice
 *     {@link ua.mai.art.aspect.LoggingAspect#stepLogDbAround(ProceedingJoinPoint)} выполняет выбранные методы
 *     репозиториев в  шагах логирования {@link ua.telesens.plu.log.StepLogDb};
 * <li>работающих с сервисами осуществляется в классе {@link ua.mai.art.aspect.LoggingAspect}, в котором определяется
 *     pointcut {@link ua.mai.art.aspect.LoggingAspect#stepLogJobPointcut()}. Этот pointcut задает логирование
 *     работы сервисов - он определяет в классах  из пакета <code>ua.mai.art.service</code> все pubic методы, содержащие
 *     в имени строку "Service" и имеющих аннотацию @StepLogJobAnnotation. А Around Advice
 *     {@link ua.mai.art.aspect.LoggingAspect#stepLogDbAround(ProceedingJoinPoint)} выполняет методы сервисов в шагах
 *     логирования {@link ua.telesens.plu.log.StepLogJob}.
 * <li>получающих request-ы осуществляется в классе {@link ua.mai.art.aspect.LoggingAspect}, в котором задается pointcut
 *     {@link ua.mai.art.aspect.LoggingAspect#stepLogRequestPointcut()}. Этот pointcut определяет логирование работы с
 *     контроллерами - он определяет все pubic методы в классах из пакета <code>ua.mai.art.controller</code>, содержащих
 *     в своем имени строку "Controller" , а Around Advice
 *     {@link ua.mai.art.aspect.LoggingAspect#stepLogRequestAround(ProceedingJoinPoint)} выполняет выбранные методы
 *     контроллеров в шагах логирования {@link ua.telesens.plu.log.StepLogRequest}.
 * </ul>
 */
@ServletComponentScan
//Не используем Security
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
//@SpringBootApplication
public class ArtSpringBootApplication extends  SpringBootServletInitializer {
//public class ArtApplication  implements CommandLineRunner {

  static private Logger logger = LoggerFactory.getLogger(ArtSpringBootApplication.class);

  /** Префикс, который используется при генерации идентификатора requestId, с которым запускается приложение. */
  private static final String INIT_REQUEST_LOG_PREFIX = "ini";


  private static  final String rootId;
  static {
    /* Создание идентификатора запущенного приложения. */
    String moduleName = ArtModuleInfo.getInstance().getModuleName();
    rootId = LogConfig.createRootId(moduleName);
  }

  /**
   * Информация о модуле Art.
   *
   * @return
   */
  @Bean
  public ModuleInfo moduleInfo() {
    return ArtModuleInfo.getInstance();
  }

  /** Логирование стрта приложения. */
  private static void logStartApp() {
    StepLogRoot stepLog = new StepLogRoot(logger, rootId, "main()");
    String moduleName = ArtModuleInfo.getInstance().getModuleName();
    stepLog.info("{} application starts...", moduleName);
    //Получаем идентификатор запроса requestId, который используется при старте приложения
    StepLogRequest initRequestLog =
        new StepLogRequest (logger, rootId, LogConfig.createRequestId(INIT_REQUEST_LOG_PREFIX), "main()");
    initRequestLog.startStep("Init application...");
  }


  public ArtSpringBootApplication() {
    super();
  }

  public static void main(String[] args) {
    //Логируем старт приложения.
//    logStartApp();
    StepLogRoot stepLog = new StepLogRoot(logger, rootId, "main()");
    String moduleName = ArtModuleInfo.getInstance().getModuleName();
    stepLog.info("{} application starts...", moduleName);
    //Получаем идентификатор запроса requestId, который используется при старте приложения
    StepLogRequest initRequestLog =
      new StepLogRequest (logger, rootId, LogConfig.createRequestId(INIT_REQUEST_LOG_PREFIX), "main()");
    initRequestLog.startStep("Init application...");

    SpringApplication app = new SpringApplication(ArtSpringBootApplication.class);
    ConfigurableApplicationContext context = app.run(args);
//    int i = 5;
  }

//Для:
//public class ArtApplication  implements CommandLineRunner {
//  @Override
//  public void run(String... args) throws Exception {
//    logger.info("run ArtSpringBootApplication");
//  }

}

