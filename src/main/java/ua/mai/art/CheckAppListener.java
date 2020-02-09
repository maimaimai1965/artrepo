package ua.mai.art;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * Listener, в котором делается проверка, в приложении после его старта.
 * <p>
 * Проверка задается методом {@link CheckAppListener#checkAfterAppStarted()}.
 */
public abstract class CheckAppListener implements ApplicationListener<ApplicationReadyEvent> {

  /**
   * Делает проверку в приложении после его старта.<br>
   * Это событие выполняется настолько поздно, насколько это возможно, чтобы запустить тестовый метод для проверки
   * функциональности.
   *
   * @param event
   */
  @Override
  public void onApplicationEvent(final ApplicationReadyEvent event) {
    //Проверка при запуске приложения.
    checkAfterAppStarted();
  }

  /** Выполнение проверки при запуске приложения. */
  abstract protected void checkAfterAppStarted();

}
