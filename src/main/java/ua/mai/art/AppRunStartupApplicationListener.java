package ua.mai.art;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ua.mai.art.domain.Student;
import ua.mai.art.repository.StudentRepositoryJdbcImpl;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.Date;

/**
 * Listener, в котором проверяется DataSource после старта приложения.
 */
@Component
@Profile("ds-test")
public class AppRunStartupApplicationListener implements ApplicationListener<ApplicationReadyEvent> {

  private Logger logger = LoggerFactory.getLogger(this.getClass());


  @Autowired
  private StudentRepositoryJdbcImpl repository;

  @Autowired
  private DataSource dataSource;

  /**
   * Это событие выполняется настолько поздно, насколько это возможно, чтобы запустить тестовый метод для проверки
   * функциональности.
   */
  @Override
  public void onApplicationEvent(final ApplicationReadyEvent event) {
    //Тест при запуске приложения.
    testAfterAppStarted();
  }


  /**
   * Выполнение теста при запуске приложения.
   */
  public void testAfterAppStarted() {

    logger.info("Student id 10001 -> {}", repository.findById(10001L));

    //Удаляем запись (для случая, если она не удалилась при проведении этого теста ранее).
    repository.deleteById(10010L);
    System.out.println("Delete");

    Student student = new Student(10010L, "John", "A1234657",
                                  new Date(LocalDate.of(1999,1,28).toEpochDay()));
    repository.insert(student);
    logger.info("Inserting -> {}", student);
    System.out.println("Inserting");

    logger.info("Update 10010 -> {}", repository.update(
            new Student(10010L, "Name-Updated", "New-Passport",
                    new Date(LocalDate.of(2001,5,17).toEpochDay()))));
    System.out.println("Update");

    logger.info("All users -> {}", repository.findAll());
    System.out.println("All users");

    repository.deleteById(10010L);
    System.out.println("Delete");

    System.out.println("!!! DataSource OK  !!! DataSource = " + dataSource.getClass());
  }

}
