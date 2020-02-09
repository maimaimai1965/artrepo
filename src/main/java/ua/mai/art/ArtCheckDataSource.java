package ua.mai.art;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ua.mai.art.domain.Student;
import ua.mai.art.repository.StudentRepository;
import ua.mai.art.service.StudentService;
import ua.mai.art.service.SupportService;
import ua.telesens.plu.log.*;
import ua.telesens.plu.util.PluException;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.Date;

/**
 * Listener, в котором делается проверка DataSource приложения после его старта.
 * <p>
 * Чтобы проверка DataSource выполнялась нужно, чтобы профиль <code>ds-check</code> был активирован.
 */
@Component
@Profile("ds-check")
public class ArtCheckDataSource extends CheckAppListener {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private DataSource dataSource;

  @Autowired
  private StudentService studentService;

  @Autowired
  private SupportService supportService;


  /**
   * Выполнение теста при запуске приложения.<br>
   * Если тест не пройден, то возникает исключение
   */
  protected void checkAfterAppStarted() {
    StepLogRoot stepLogRoot = new StepLogRoot(logger, "ArtCheckDataSource.checkAfterAppStarted()");
    stepLogRoot.startStep("Check DataSource...");

    boolean ok = supportService.checkConnection();
    if (ok) {
      stepLogRoot.finishStepOk("DataSource OK! DataSource={}", dataSource.getClass().getName());
    }
    else {
      stepLogRoot.finishStepErr("Bad DataSource!");
      throw new PluException("Bad DataSource!");
    }

    stepLogRoot = new StepLogRoot(logger, "ArtCheckDataSource.checkAfterAppStarted()");
    stepLogRoot.startStep("Check DML operations ...");
    try {
      studentService.findById(10001L);

      //Удаляем запись (для случая, если она не удалилась при проведении этого теста ранее).
      studentService.deleteById(10010L);

      Student student = new Student(10010L, "John", "A1234657",
        new Date(LocalDate.of(1999, 1, 28).toEpochDay()));
      studentService.insert(student);

      studentService.update(new Student(10010L, "Name-Updated", "New-Passport",
        new Date(LocalDate.of(2001, 5, 17).toEpochDay())));

      studentService.findAll();

      studentService.deleteById(10010L);

      stepLogRoot.finishStepOk("DML operations OK.");
    }
    catch (Exception e){
      stepLogRoot.finishStepErr(e);
    }
  }

}
