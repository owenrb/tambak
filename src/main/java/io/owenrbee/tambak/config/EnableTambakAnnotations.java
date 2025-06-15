package io.owenrbee.tambak.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Convenience annotation to enable the Tambak library functionality.
 * <p>
 * This annotation acts as a composite annotation, combining:
 * <ul>
 * <li>{@link Configuration}: Marks this class as a source of bean
 * definitions.</li>
 * <li>{@link EnableAspectJAutoProxy}: Enables Spring's support for detecting
 * and
 * registering beans for which Spring should create proxies to advise method
 * calls.
 * {@code proxyTargetClass = true} ensures CGLIB proxies are used for
 * class-based
 * proxying.</li>
 * <li>{@link ComponentScan}: Scans the package where the aspect implementation
 * resides (`io.owenrbee.tambak.aspect`), ensuring it's discovered and
 * registered
 * as a Spring bean.</li>
 * </ul>
 * <p>
 * Developers can simply add {@code @EnableTambakAnnotations} to their Spring
 * Boot
 * application class or any configuration class to activate the list reversal
 * aspect.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) // This annotation can be applied to classes/interfaces
@Configuration // Marks this as a Spring configuration class
@EnableAspectJAutoProxy(proxyTargetClass = true) // Enable AspectJ auto-proxying
@ComponentScan("io.owenrbee.tambak.aspect") // Scan for the Tambak aspect
public @interface EnableTambakAnnotations {

}
