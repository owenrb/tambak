package io.owenrbee.tambak.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collections;
import java.util.List;

/**
 * Custom annotation to ensure that a method returning a {@link List}
 * never returns {@code null}. If the annotated method returns {@code null},
 * an empty list will be returned instead.
 * <p>
 * The {@code modifiable} option controls whether the newly created empty list
 * is modifiable (an instance of {@link java.util.ArrayList}) or unmodifiable
 * (obtained via {@link Collections#emptyList()}).
 */
@Retention(RetentionPolicy.RUNTIME) // Make the annotation available at runtime
@Target(ElementType.METHOD) // Apply this annotation only to methods
public @interface MustNotNullList {

    /**
     * Specifies whether the empty list returned when the original method returns
     * null should be modifiable.
     * <p>
     * If {@code true}, a new {@link java.util.ArrayList} is returned.
     * If {@code false}, {@link Collections#emptyList()} is returned.
     *
     * @return {@code true} if the list should be modifiable, {@code false}
     *         otherwise.
     *         Defaults to {@code true}.
     */
    boolean modifiable() default true;

}
