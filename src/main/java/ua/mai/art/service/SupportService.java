package ua.mai.art.service;

import org.springframework.stereotype.Service;
import ua.mai.art.aspect.StepLogJobAnnotation;

public interface SupportService {

  /**
   * Проверяет получение соединения с БД.<br>
   * Если метод не вызвал исключения, то подсоединение к БД прошло успешно. При неуспешном сединении с БД
   * возникает исключение.
   *
   * @return флаг
   * @throws RuntimeException - исключение
   */
  public boolean checkConnection();

}
