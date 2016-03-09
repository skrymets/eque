package my.school.spring.beans;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author skrymets
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomDataProvider {

    public String seed() default "";
}
