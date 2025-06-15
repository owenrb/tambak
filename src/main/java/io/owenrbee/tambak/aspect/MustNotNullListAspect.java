package io.owenrbee.tambak.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import io.owenrbee.tambak.annotation.MustNotNullList;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Aspect that intercepts methods annotated with @MustNotNullList.
 * If the intercepted method returns {@code null}, this aspect replaces the
 * {@code null} with an empty {@link List}. The mutability of the new list
 * is determined by the {@code modifiable} attribute of the annotation.
 */
@Aspect // Declares this class as an Aspect
@Component // Makes this a Spring component
@Slf4j
public class MustNotNullListAspect {

    /**
     * Around advice that intercepts calls to methods annotated
     * with @MustNotNullList.
     * The pointcut expression ensures that only methods returning a {@link List}
     * and annotated with {@code @MustNotNullList} are advised.
     *
     * @param joinPoint                 The join point representing the method
     *                                  execution.
     * @param mustNotNullListAnnotation The instance of the @MustNotNullList
     *                                  annotation.
     * @return The result of the method execution, guaranteed to be a non-null List.
     * @throws Throwable if the original method execution throws an exception.
     */
    @Around("@annotation(mustNotNullListAnnotation) && execution(java.util.List *(..))")
    public Object ensureNotNullList(ProceedingJoinPoint joinPoint, MustNotNullList mustNotNullListAnnotation)
            throws Throwable {
        log.debug(">>> MustNotNullListAspect: Intercepting method: {}", joinPoint.getSignature().toShortString());

        // Cast to MethodSignature to access getReturnType()
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        // Proceed with the original method execution
        Object result = joinPoint.proceed();

        // Check if the result is null.
        // Also check if the method indeed returns a List (type safety).
        if (result == null && List.class.isAssignableFrom(signature.getReturnType())) {
            log.debug(">>> MustNotNullListAspect: Original method returned NULL for: {}",
                    joinPoint.getSignature().toShortString());
            if (mustNotNullListAnnotation.modifiable()) {
                log.debug(">>> MustNotNullListAspect: Returning new modifiable empty list.");
                return new ArrayList<>(); // Return a modifiable empty list
            } else {
                log.debug(">>> MustNotNullListAspect: Returning unmodifiable empty list.");
                return Collections.emptyList(); // Return an unmodifiable empty list
            }
        }

        return result; // Return the original result if not null or not a List
    }
}
