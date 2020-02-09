package ua.mai.art.aspect;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Аннотация, использующаяся для метода который должен логировься как StepLogJob. Это методы сервиса.
 */
@Retention(RUNTIME)
@Target(METHOD)
@Inherited
public @interface StepLogJobAnnotation {
}
