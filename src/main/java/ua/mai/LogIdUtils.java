package ua.mai;

import org.apache.commons.lang3.time.DurationFormatUtils;
import ua.telesens.plu.log.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * Методы для формирования идентификаторов для различных этапов логирования.
 * <p>
 *
 * <ul>
 * <li>Формирование идентификатора rootId (записывается с точностью до секунды):<br>
 *     <code><i>имя_приложения-yyMMdd:HHmmss</i></code>, где <i>yyMMdd:HHmmssSSS</i> - момент времени создания rootId.<br>
 *     Пример: <code>art-200423:143528</code>.<br>
 * <li>Формирование requestId (записывается с точностью до тысяной секунды):<br>
 *     <ul>
 *     <li>с собственным именем запроса:<br>
 *         <code><i>имя_запроса-yyMMdd:HHmmssSSS</i></code><br>
 *         , где <i>yyMMdd:HHmmssSSS</i> - момент времени создания requestId.<br>
 *         Пример: <code>mai-200214:171032983</code>.
 *     <li>без имени запроса<br>
 *         <code>request-<i>yyMMdd:HHmmssSSS</i></code><br>
 *         , где <code>request</code> - строка-константа {@link #DEFAULT_NAME_REQUEST};<br>
 *               <code><i>yyMMdd:HHmmssSSS</i></code> - момент времени создания requestId.<br>
 *         Пример: <code>request-200124:081002740</code>.
 *     </ul>
 * <li>Формирование идентификатора jobId (записывается с точностью до тысяной секунды):<br>
 *     <ul>
 *     <li>при существующем в MDC контексте идентификаторе requestId:<br>
 *         <code><i>имя_запроса-yyMMdd:HHmmssSSS</i>+<i>HmmssSSS</i></code><br>
 *         , где<br>
 *           <code><i>имя_запроса-yyMMdd:HHmmssSSS</i></code> - идентификатор requestId, полученный из MDC контекста;<br>
 *           <code>+</code> - символ определенный {@link #charAfterRequestId};<br>
 *           <code><i>HmmssSSS</i></code> - длительность к моменту создания jobId от момента создания requestId. Момент
 *           создания requestId определяется из идентификатора requestId.<br>
 *         Пример: <code>mai-200214:171032983+00010278</code>.
 *     <li>в остальных случаях генерируется уникальное число.<br>
 *     </ul>
 * <li>Формирование идентификатора detailId - генерируется уникальное число.
 * </ul>
 */
public class LogIdUtils {

  /** Формат даты и времени с точностью до секунды. */
  public static final String dateTimeSecondsFormat = "yyMMdd:HHmmss";
  /** Формат даты и времени с точностью до тысячных секунды. */
  public static final String dateTimeMilliSecondsFormat = "yyMMdd:HHmmssSSS";
  /** Формат длительности с точностью до тысячных секунды. */
  public static final String durationMilliSecondsFormat = "HmmssSSS";

  /** Форматер даты и времени с точностью до секунды. */
  public static final DateTimeFormatter dateTimeSecondsFormatter = DateTimeFormatter.ofPattern(dateTimeSecondsFormat);

  /** Форматер даты и времени с точностью до тысячных секунды. */
  public static final DateTimeFormatter dateTimeMilliSecondsFormatter = DateTimeFormatter.ofPattern(dateTimeMilliSecondsFormat);

  public static String DEFAULT_NAME_ROOT = "root";
  public static String DEFAULT_NAME_REQUEST = "request";

  public static char charAfterRootName = '-';
  public static char charAfterRequestId = '+';

  /**
   * Возвращает строку, содержащую дату и время в формате {@link #dateTimeSecondsFormatter} (с точностью до секунды).
   *
   * @return
   */
  public static String formatCurrDateTimeWithSeconds() {
    return dateTimeSecondsFormatter.format(LocalDateTime.now());
  }
  /**
   * Возвращает строку, содержащую дату и время в формате {@link #dateTimeMilliSecondsFormatter} (с точностью до
   * тысячных секунды).
   *
   * @return
   */
  public static String formatCurrDateTimeWithMilliSeconds() {
    return dateTimeMilliSecondsFormatter.format(LocalDateTime.now());
  }

  /**
   * Возвращает строку, которая будет добавляться вначале идентификатора.
   *
   * @param prefix префикс
   * @param defaultPrefix
   * @return если задан префикс, то он возвращается с приведеннием к нижнему регистру, без начальных и конечных
   *     пробелов, с присоединением к нему символа {@link #charAfterRootName}. Если же prefix не задан, то используется
   *     defaultPrefix. Если defaultPrefix задан, то он берется без преобразования. А если и он не задан, то
   *     возвращается пустая строка.
   */
  public static String processPrefix(String prefix, String defaultPrefix) {
    return (((prefix != null) && (!prefix.trim().isEmpty())) ?
               (prefix.trim().toLowerCase() + charAfterRootName) :
               (((defaultPrefix != null) && (!defaultPrefix.isEmpty())) ? (defaultPrefix + charAfterRootName) : ""));
  }

  /**
   * Возвращает строку-идентификатор, в начале которой содержится обработанный prefix
   * (см. {@link #processPrefix(String, String)}), а после - дата и время в формате {@link #dateTimeSecondsFormat} (с
   * точностью до секунды).<br>
   * Если prefix и defaultPrefix не заданы, то возвращается строка с датой и временем.
   *
   * @param prefix
   * @param defaultPrefix
   * @return
   */
  public static String getIdForCurrDateTimeWithSeconds(String prefix, String defaultPrefix) {
    return processPrefix(prefix, defaultPrefix) + formatCurrDateTimeWithSeconds();
  }
  /**
   * Возвращает строку-идентификатор, в начале которой содержится обработанный prefix (см.
   * {@link #processPrefix(String, String)}), а после - дата и время в формате {@link #dateTimeMilliSecondsFormat} (с
   * точностью до тысячных секунды).<br>
   * Если prefix и defaultPrefix не задан, то возвращается строка с датой и временем.
   *
   * @param prefix
   * @param defaultPrefix
   * @return
   */
  public static String getIdForCurrDateTimeWithMilliSeconds(String prefix, String defaultPrefix) {
    return processPrefix(prefix, defaultPrefix) + formatCurrDateTimeWithMilliSeconds();
  }


  /**
   * Возвращает дату со временем (с точностью до секунды) из строки str.
   *
   * @param str строка, в которой записана дата со временем в формате {@link #dateTimeSecondsFormat}
   * @return
   */
  public static LocalDateTime getDateTimeFromStringWithSeconds(String str) {
    return LocalDateTime.parse(str, dateTimeSecondsFormatter);
  }
  /**
   * Возвращает дату со временем (с точностью до тысячных секунды) из строки str.
   *
   * @param str строка, в которой записана дата со временем в формате {@link #dateTimeMilliSecondsFormat}
   * @return
   */
  public static LocalDateTime getDateTimeFromStringWithMilliSeconds(String str) {
    return LocalDateTime.parse(str, dateTimeMilliSecondsFormatter);
  }

  /**
   * Возврашает строку, содержащую длительность между моментами startInclusive и endExclusive в формате
   * {@link #durationMilliSecondsFormat} (с точностью до тысячных секунды).
   *
   * @param startInclusive
   * @param endExclusive
   * @return
   */
  public static String formatDuration(LocalDateTime startInclusive, LocalDateTime endExclusive) {
    Duration duration = Duration.between(startInclusive, endExclusive);
    return DurationFormatUtils.formatDuration(duration.toMillis(), durationMilliSecondsFormat, true);
  }

  /**
   * Возврашает строку, содержащую длительность между startDateTime и текущем моментом в формате
   * {@link #durationMilliSecondsFormat} (с точностью до тысячных секунды).
   *
   *
   * @param startDateTime
   * @return
   */
  public static String formatDurationForCurrDateTime(LocalDateTime startDateTime) {
    return formatDuration(startDateTime, LocalDateTime.now());
  }

  /**
   * Возвращает из строки str строку, которая идет за первым символом {@link #charAfterRootName}.<br>
   * Если в строке нет символа {@link #charAfterRootName}, то возвращает исходную строку.
   *
   * @param str
   * @return
   */
  public static String getStringAfterPrefix(String str) {
    if (str != null) {
      int index = str.indexOf(charAfterRootName);
      if (index >= 0) {
        return str.substring(index + 1, str.length());
      }
    }
    return str;
  }

  /**
   * Возвращает дату со временем (с точностью до секунды) из идентификатора id, записанную после символа
   * {@link #charAfterRootName}.<br>
   * Если присходит ошибка при получении даты со временем, то возвращается <code>null</code>.
   *
   * @param id идентификатор.
   * @return
   */
  public static LocalDateTime getDateTimeFromIdWithSecondsAfterPrefix(String id) {
    LocalDateTime dateTime = null;
    if ((id != null) && (!id.isEmpty())) {
      try {
        dateTime = LocalDateTime.parse(getStringAfterPrefix(id), dateTimeSecondsFormatter);
      }
      catch (Exception e) {
      }
    }
    return dateTime;
  }
  /**
   * Возвращает дату со временем (с точностью до тысячных секунды) из идентификатора id, записанную после символа
   * {@link #charAfterRootName}.<br>
   * Если присходит ошибка при получении даты со временем, то возвращается <code>null</code>.
   *
   * @param id
   * @return
   */
  public static LocalDateTime getDateTimeFromIdWithMilliSecondsAfterPrefix(String id) {
    LocalDateTime dateTime = null;
    if ((id != null) && (!id.isEmpty())) {
      try {
        dateTime = LocalDateTime.parse(getStringAfterPrefix(id), dateTimeMilliSecondsFormatter);
      }
      catch (Exception e) {
      }
    }
    return dateTime;
  }


  /**
   * Возвращает rootId из MDC контекста.
   *
   * @return
   */
  public static String getRootIdFromMdc() {
    return StepLogRoot.getRootIdFromMdc();
  }
  /**
   * Создает идентификатор rootId, начинающийся с rootPrefix, и содержащий дату и время в формате
   * {@link #dateTimeSecondsFormatter} (с точностью до секунды).<br>
   * Если rootPrefix не задан, то в качестве префикса используется {@link #DEFAULT_NAME_ROOT}.
   *
   * @param rootPrefix
   * @return
   */
  public static String createRootId(String rootPrefix) {
    return getIdForCurrDateTimeWithSeconds(rootPrefix, DEFAULT_NAME_ROOT);
  }
//  /**
//   * Возвращает rootId из MDC контекста. Если его там нет, то создает его в MDC контексте с идентификатором rootPrefix,
//   * содержащим дату и время в формате {@link #dateTimeSecondsFormatter} (с точностью до секунды).
//   *
//   * @param rootPrefix
//   * @return
//   */
//  public static String getRootId(String rootPrefix) {
//    String rootId = StepLogRoot.getRootIdFromMdc();
//    return (rootId != null) ? rootId : createRootId(rootPrefix);
//  }


  /**
   * Возвращает requestId из MDC контекста.
   *
   * @return
   */
  public static String getRequestIdFromMdc() {
    return StepLogRequest.getRequestIdFromMdc();
  }
  /**
   * Создает идентификатор requestId, начинающийся с requestPrefix, и содержащий дату и время в формате
   * {@link #dateTimeSecondsFormatter} (с точностью до тысячных секунды).
   * Если requestPrefix не задан, то в качестве префикса используется {@link #DEFAULT_NAME_REQUEST}.
   *
   * @param requestPrefix
   * @return
   */
  public static String createRequestId(String requestPrefix) {
    return getIdForCurrDateTimeWithMilliSeconds(requestPrefix, DEFAULT_NAME_REQUEST);
  }
  /**
   * Создает идентификатор requestId, в котором в качестве префикса используется {@link #DEFAULT_NAME_REQUEST}, и
   * который содержит дату и время в формате {@link #dateTimeSecondsFormatter} (с точностью до тысячных секунды).
   *
   * @return
   */
  public static String createDefaultRequestId() {
    return createRequestId(DEFAULT_NAME_REQUEST);
  }


//  /**
//   * Создает идентификатор requestId, начинающийся с requestPrefix, и содержащий дату и время в формате
//   * {@link #dateTimeMilliSecondsFormatter} (с точностью до тысячных секунды).
//   *
//   * @param rootId
//   * @return
//   */
//  public static String createRequestIdFromRootId(String rootId) {
//    LocalDateTime startDateTime = getDateTimeFromIdWithMilliSecondsAfterPrefix(rootId);
//    return null;
//  }
//
//  /**
//   * Возвращает requestId из MDC контекста. Если его там нет, то создает его в MDC контексте с идентификатором
//   * requestPrefix, содержащим дату и время в формате {@link #dateTimeSecondsFormatter} (с точностью до секунды).
//   *
//   * @param requestPrefix
//   * @return
//   */
//  public static String getRequestId(String requestPrefix) {
//    String requestId = StepLogRequest.getRequestIdFromMdc();
//    return (requestId != null) ? requestId : createRequestId(requestPrefix);
//  }


//  public static String jobIdFromRequestId(String requestId) {
//    String jobId = null;
//    if (requestId != null) {
//      try {
//        int index = requestId.indexOf('-');
//        String onlyDateTime = requestId.substring(0, index);
////        LocalDateTime requestDateTime = getDateTimeMilliSecond(onlyDateTime);
////        Duration duration = Duration.between(LocalDateTime.now(), requestDateTime);
////        DurationFormatUtils.formatDuration(duration.toMillis(), "**HmmssSSS**", true);
//      }
//      catch (Exception e) {
//      }
//
//    }
//    return jobId;
//  }




  /**
   * Возвращает requestId из MDC контекста.
   *
   * @return
   */
  public static String getJobIdFromMdc() {
    return StepLogRequest.getJobIdFromMdc();
  }

  /**
   * Создает jobId.<br>
   * Формирование идентификатора jobId (записывается с точностью до тысяной секунды):<br>
   * <ul>
   * <li>при существующем в MDC контексте идентификаторе requestId:<br>
   *     <code><i>имя_запроса-yyMMdd:HHmmssSSS</i>+<i>HmmssSSS</i></code><br>
   *     , где<br>
   *       <code><i>имя_запроса-yyMMdd:HHmmssSSS</i></code> - идентификатор requestId, полученный из MDC контекста;<br>
   *       <code>+</code> - символ определенный {@link #charAfterRequestId};<br>
   *       <code><i>HmmssSSS</i></code> - длительность к моменту создания jobId от момента создания requestId. Момент
   *       создания requestId определяется из идентификатора requestId.<br>
   *     Пример: <code>mai-200214:171032983J00010278</code>.
   * <li>в остальных случаях генерируется уникальное число.<br>
   * </ul>
   *
   * @return
   */
  public static String createJobId() {
    String requestId = getRequestIdFromMdc();
    if (requestId != null) {
      LocalDateTime startDateTime = getDateTimeFromIdWithMilliSecondsAfterPrefix(requestId);
      if (startDateTime != null) {
        String duration = formatDurationForCurrDateTime(startDateTime);
        return requestId + charAfterRequestId + duration;
      }
    }
    return PluLogUtils.getUniqueId();
  }

  /**
   * Создает идентификатор detailId.<br>
   * Генерируется уникальное число.
   *
   * @return
   */
  public static String createDetailId() {
    return PluLogUtils.getUniqueId();
  }

  /**
   *
   * @param startInclusive
   * @param endExclusive
   * @param millisecs
   * @return
   */
  public static String formatInterval(LocalDateTime startInclusive, LocalDateTime endExclusive, boolean millisecs) {
    Duration duration = Duration.between(startInclusive, endExclusive);
    return formatInterval(duration.toMillis(), millisecs);
  }

  public static String formatInterval(final long interval, boolean millisecs ) {
    final long hr  = TimeUnit.MILLISECONDS.toHours(interval);
    final long min = TimeUnit.MILLISECONDS.toMinutes(interval) %60;
    final long sec = TimeUnit.MILLISECONDS.toSeconds(interval) %60;
    final long ms  = TimeUnit.MILLISECONDS.toMillis(interval) %1000;
    if( millisecs ) {
      return String.format("%d%02d%02d%03d", hr, min, sec, ms);
    } else {
      return String.format("%d%02d%02d", hr, min, sec );
    }
  }

}

