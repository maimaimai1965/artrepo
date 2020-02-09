package ua.mai.art.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import ua.mai.art.repository.SupportRepository;
import ua.telesens.plu.log.StepLogJob;

@Service
public class SupportServiceImpl implements SupportService, ApplicationContextAware {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private SupportRepository supportRepository;

  private ApplicationContext ctx;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.ctx = applicationContext;
  }


  /**
   * Проверяет получение соединения с БД.<br>
   * Если метод не вызвал исключения, то подсоединение к БД прошло успешно. При неуспешном сединении с БД
   * возникает исключение.
   *
   * @return флаг
   * @throws RuntimeException - исключение
   */
  @Override
  public boolean checkConnection() {
    StepLogJob stepLogJob = new StepLogJob(logger, (String)ctx.getBean("jobId"), "SupportService.checkConnection()");
    stepLogJob.startStep("Check connection.");
    try {
      supportRepository.checkConnection();
      stepLogJob.finishStepOk("Connection OK.");
      return true;
    }
    catch (Exception e) {
      stepLogJob.finishStepOk("Bad connection!!!.");
      return false;
    }
  }

}
