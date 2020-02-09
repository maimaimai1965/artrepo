package ua.telesens.plu.log;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.event.Level;

/**
 * Открывает конструкторы класса {@link StepLog}.
 */
public class StepLogExtra extends StepLog {

  public StepLogExtra(Logger logger, Marker marker) {
    super(logger, marker);
  }

  public StepLogExtra(Logger logger, Marker marker, String stepName) {
    super(logger, marker);
    setStepName(stepName);
  }

  public StepLogExtra(Logger logger, Marker marker, AdvancedLog advancedLog) {
    super(logger, marker, advancedLog);
  }

  public StepLogExtra(Logger logger, Marker marker, Level stepLevel, Level subStepLevel) {
    super(logger, marker, stepLevel, subStepLevel);
  }


  public StepLogExtra(Logger logger, Marker marker, Level stepLevel, Level subStepLevel, AdvancedLog advancedLog) {
    super(logger, marker, stepLevel, subStepLevel, advancedLog);
  }

  /**
   * Создание ROOT шага логирования с уровнем INFO и заданным уровнем subStepLevel равным DEBUG.
   * rootId (идентификатор запуска приложения) не изменяется.
   *
   * @param logger - логгер, связанный с этим шагом
   * @param stepName - имя (идентификатор) логируемого шага.
   */
//  public static StepLog createStepLogForRoot(Logger logger, String stepName) {
//    StepLog stepLog = new StepLog(logger, LogMarker.ROOT, Level.INFO, Level.DEBUG);
//    stepLog.setStepName(stepName);
//    return stepLog;
//  }

}
