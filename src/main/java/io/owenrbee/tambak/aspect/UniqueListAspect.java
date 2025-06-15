package io.owenrbee.tambak.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import io.owenrbee.tambak.annotation.UniqueList;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Aspect that intercepts methods annotated with @UniqueList.
 * If the intercepted method returns a List, this aspect removes duplicate
 * elements
 * from the List while preserving the original insertion order, before returning
 * it to the caller.
 */
@Aspect // Declares this class as an Aspect
@Component // Makes this a Spring component
@Slf4j
public class UniqueListAspect {

    /**
     * Around advice that intercepts calls to methods annotated with @UniqueList.
     * The pointcut expression ensures that only methods returning a
     * `java.util.List`
     * and annotated with `@UniqueList` are advised.
     *
     * @param joinPoint            The join point representing the method execution.
     * @param uniqueListAnnotation The instance of the @UniqueList annotation.
     * @return The result of the method execution, with duplicates removed from the
     *         List.
     * @throws Throwable if the original method execution throws an exception.
     */
    @Around("@annotation(uniqueListAnnotation) && execution(java.util.List *(..))")
    public Object makeListUnique(ProceedingJoinPoint joinPoint, UniqueList uniqueListAnnotation) throws Throwable {
        log.debug(">>> UniqueListAspect: Intercepting method: {}", joinPoint.getSignature().toShortString());

        // Proceed with the original method execution
        Object result = joinPoint.proceed();

        // Check if the result is a List and remove duplicates
        if (result instanceof List) {
            log.debug(">>> UniqueListAspect: Removing duplicates from list returned by: {}",
                    joinPoint.getSignature().toShortString());
            List<?> list = (List<?>) result;

            // Use LinkedHashSet to maintain insertion order while removing duplicates
            Set<Object> uniqueElements = new LinkedHashSet<>(list);

            // Create a new ArrayList from the LinkedHashSet to preserve order and return as
            // List
            return new ArrayList<>(uniqueElements);
        } else {
            log.debug(
                    ">>> UniqueListAspect: Method did not return a List or returned null, no uniqueness applied.");
        }

        return result; // Return the (potentially modified) result
    }
}
