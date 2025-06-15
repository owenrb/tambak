package io.owenrbee.tambak.aspect;

import java.util.Collections;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import io.owenrbee.tambak.annotation.ReverseList;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class ReverseListAspect {

    /**
     * Around advice that intercepts calls to methods annotated with @Reverse.
     * The pointcut expression ensures that only methods returning a
     * `java.util.List`
     * and annotated with `@Reverse` are advised.
     *
     * @param joinPoint         The join point representing the method execution,
     *                          allowing
     *                          proceeding with the original method or accessing its
     *                          signature.
     * @param reverseAnnotation The instance of the @ReverseList annotation,
     *                          injected by
     *                          AspectJ.
     * @return The result of the method execution, with the List potentially
     *         reversed.
     * @throws Throwable if the original method execution throws an exception, it's
     *                   rethrown.
     */
    @Around("@annotation(reverseAnnotation) && execution(java.util.List *(..))")
    public Object reverseList(ProceedingJoinPoint joinPoint, ReverseList reverseAnnotation) throws Throwable {
        // Logging for demonstration purposes, showing when the aspect is active
        log.debug(">>> ReverseListAspect: Intercepting method: {}", joinPoint.getSignature().toShortString());

        // Proceed with the original method execution. This executes the method
        // that was annotated with @Reverse.
        Object result = joinPoint.proceed();

        // Check if the result obtained from the original method is an instance of List.
        // This ensures type safety before attempting to reverse.
        if (result instanceof List) {
            log.debug(">>> ReverseListAspect: Reversing the list returned by: {}",
                    joinPoint.getSignature().toShortString());
            // Cast the result to a List of unknown type (using wildcard ?) as we only need
            // to call the static reverse method which doesn't care about element type.
            List<?> list = (List<?>) result;
            Collections.reverse(list); // Reverse the list in-place.
        } else {
            // This case should theoretically not be hit if the 'execution' pointcut
            // is precise, but acts as a safeguard and for clearer logging.
            log.warn(">>> ReverseListAspect: Method did not return a List or returned null, no reversal applied.");
        }

        // Return the (potentially reversed) result to the original caller.
        return result;
    }
}
