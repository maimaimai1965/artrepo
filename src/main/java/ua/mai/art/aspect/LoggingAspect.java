package ua.mai.art.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.mai.LogIdUtils;
import ua.mai.art.config.LogConfig;
import ua.telesens.plu.log.*;

import java.util.Arrays;
import java.util.function.Supplier;

/**
 * Aspect for logging execution of repositories, services and controllers.
 */
@Aspect
@Component
public class LoggingAspect //implements ApplicationContextAware
{

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  //Поставщики Id шагов логирования:
  private Supplier<String> rootIdSupplier;
  private Supplier<String> requestIdSupplier;
  private Supplier<String> jobIdSupplier;
  private Supplier<String> detailIdSupplier;

  @Autowired
  public LoggingAspect(Supplier<String> rootIdSupplier, Supplier<String> requestIdSupplier,
                       Supplier<String> jobIdSupplier, Supplier<String> detailIdSupplier) {
    this.rootIdSupplier = rootIdSupplier;
    this.requestIdSupplier = requestIdSupplier;
    this.jobIdSupplier = jobIdSupplier;
    this.detailIdSupplier = detailIdSupplier;
  }

  //--------------------------------------------- Repository -----------------------------------------------------------
  /**
   * Pointcut для логирования репозиториев.<br>
   * Определяет все pubic методы в классах содержащих в имени "Repository" из пакета ua.mai.art.repository.
   */
  @Pointcut("execution(public * ua.mai.art.repository.*Repository*.*(..))")
  public void stepLogDbPointcut() {}

  /**
   * Совет для логирования методов в репозиториях.
   *
   * @param joinPoint join point for advice
   * @return result
   * @throws Throwable throws IllegalArgumentException
   */
  @Around("stepLogDbPointcut()")
  public Object stepLogDbAround(ProceedingJoinPoint joinPoint) throws Throwable {
    return stepLogAround(joinPoint, LogMarkerName.DB);
  }


  //--------------------------------------------- Service --------------------------------------------------------------
  /**
   * Pointcut для логирования сервисов.<br>
   * Определяет pubic методы в классах содержащих в имени "Service" из пакета <code>ua.mai.art.service</code> с
   * аннотацией @StepLogJobAnnotation.
   */
  @Pointcut("execution(public * ua.mai.art.service.*Service*.*(..)) && @annotation(StepLogJobAnnotation)")
  public void stepLogJobPointcut() {}

  /**
   * Совет для логирования методов в репозиториях.
   *
   * @param joinPoint join point for advice
   * @return result
   * @throws Throwable throws IllegalArgumentException
   */
  @Around("stepLogJobPointcut()")
  public Object stepLogJobAround(ProceedingJoinPoint joinPoint) throws Throwable {
    return stepLogAround(joinPoint, LogMarkerName.JOB);
  }

  //--------------------------------------------- Controller -----------------------------------------------------------
  /**
   * Pointcut для логирования контроллеров.<br>
   * Определяет все pubic методы в классах содержащих в имени "Controller" из пакета ua.mai.art.controller.
   */
  @Pointcut("execution(public * ua.mai.art.controller.*Controller*.*(..))")
  public void stepLogRequestPointcut() {}

  /**
   * Совет для логирования методов в репозиториях.
   *
   * @param joinPoint join point for advice
   * @return result
   * @throws Throwable throws IllegalArgumentException
   */
  @Around("stepLogRequestPointcut()")
  public Object stepLogRequestAround(ProceedingJoinPoint joinPoint) throws Throwable {
    return stepLogAround(joinPoint, LogMarkerName.REQUEST);
  }


  public enum LogMarkerName {
    ROOT, REQUEST, JOB, DETAIL, DB, EXT
  }

  public Object stepLogAround(ProceedingJoinPoint joinPoint, LogMarkerName logMarkerName) throws Throwable {
    String target = joinPoint.getTarget().toString();
    target = target.substring(target.lastIndexOf('.') + 1);
    target = target.substring(0, target.lastIndexOf('@')) + "." + joinPoint.getSignature().getName() + "()";
    StepLog stepLog = null;
    switch (logMarkerName) {
      case DB:      //Используются установленные ранее Id.
                    stepLog = new StepLogDb(logger, target);
                    break;
      case JOB:     //jobId получаем из бина в LogConfig.
                    stepLog = new StepLogJob(logger, jobIdSupplier.get(), target);
                    break;
      case REQUEST: //requstId получаем из бина в LogConfig.
                    stepLog = new StepLogRequest(logger, requestIdSupplier.get(), target);
                    break;
      default:      stepLog = new StepLogDetail(logger, detailIdSupplier.get(), target);
    }
    stepLog.startStep("...");
    try {
      Object result = joinPoint.proceed();
      stepLog.finishStepOk();
      return result;
    }
    catch (IllegalArgumentException e) {
      stepLog.finishStepErr("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
        joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
      throw e;
    }
    catch (Throwable e) {
      stepLog.finishStepErr(e);
      throw e;
    }
  }

}