package ua.mai.art.repository;

import org.springframework.stereotype.Repository;
import ua.telesens.plu.util.PluException;

/**
 * Интерфейс, описывающий API репозитария (хранилища) вспомогательных функций.
 */
@Repository
public interface SupportRepository {

  /**
   * Проверяет получение соединения с БД.<br>
   * Если метод не вызвал исключения, то подсоединение к БД прошло успешно. При неуспешном сединении с БД
   * возникает исключение. 
   *
   * @throws Exception - исключение
   */
  public void checkConnection();
  
  /**
   * Возвращает имя пользователя БД через которого идет работа с БД.<br>
   *
   * @return имя пользователя БД. Если имя пользователя не может быть определено, то возвращается
   *             {@link ua.telesens.plu.ticpayment.service.SupportService#UNDEF_DB_USER_NAME}.
   * @throws PluException - исключение
   */
//  public String getDbUser();

  /**
   * Возвращает url БД c котороq работает сервис.<br>
   *
   * @param pluRequest - запрос. 
   * @return url БД. Если url не может быть определен, то возвращается
   *             {@link ua.telesens.plu.ticpayment.service.SupportService#UNDEF_DB_URL}.
   * @throws PluException - исключение
   */
//  public String getDbUrl(PluRequest pluRequest);

}
