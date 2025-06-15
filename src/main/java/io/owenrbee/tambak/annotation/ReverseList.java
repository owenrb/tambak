package io.owenrbee.tambak.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation to indicate that the List returned by a method
 * should be reversed. This annotation is retained at runtime, allowing
 * AOP aspects to intercept and modify the method's return value.
 */
@Retention(RetentionPolicy.RUNTIME) // Make the annotation available at runtime for AOP processing
@Target(ElementType.METHOD) // Apply this annotation only to methods
public @interface ReverseList {

}
