package ua.mai.art.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ua.mai.LogIdUtils;

/*
 * Содержит бин rootId, бины для генерации идентификаторов jobId, detailId, а так же метод.
 */
@Configuration
public class LogConfig {

  /* Идентификатор запущенного приложения. */
  private static String rootId = null;

  /**
   * Создает идентификатор запущенного приложения rootId;
   * @param rootPrefix
   * @return
   */
  public static String createRootId(String rootPrefix) {
    rootId = LogIdUtils.createRootId(rootPrefix);
    return rootId;
  }

  /**
   * Возвращает идентификатор rootId.
   */
  @Bean("rootId")
  public String getRootId() {
    return rootId;
  }

  /**
   * Возвращает новый идентификатор requestId.<br>
   *
   * @param requestPrefix
   * @return идентификатор requestId
   */
  public static String createRequestId(String requestPrefix) {
    String requestId = LogIdUtils.createRequestId(requestPrefix);
    return requestId;
  }

  /**
   * Бин, возвращающий новый идентификатор requestId.<br>
   * Используется в {@link ua.mai.art.aspect.LoggingAspect#stepLogAround(ProceedingJoinPoint, String)}.
   *
   * @return идентификатор jobId
   */
  @Bean("requestId")
  @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  public String createRequestId() {
    String requestId = createRequestId("req");
    return requestId;
  }

  /**
   * Бин, возвращающий новый идентификатор jobId.<br>
   * Используется в {@link ua.mai.art.aspect.LoggingAspect#stepLogAround(ProceedingJoinPoint, String)}.
   *
   * @return идентификатор jobId
   */
  @Bean("jobId")
  @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  public String createJobId() {
    String jobId = LogIdUtils.createJobId();
    return jobId;
  }

  /**
   * Возвращает новый идентификатор detailId.<br>
   * Используется в {@link ua.mai.art.aspect.LoggingAspect#stepLogAround(ProceedingJoinPoint, String)}.
   *
   * @return идентификатор jobId
   */
  @Bean("detailId")
  @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  public String createDetailId() {
    String detailId = LogIdUtils.createDetailId();
    return detailId;
  }

}
