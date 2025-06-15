package io.owenrbee.tambak.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // Make the annotation available at runtime for AOP processing
@Target(ElementType.METHOD) // Apply this annotation only to methods
public @interface UniqueList {

}
