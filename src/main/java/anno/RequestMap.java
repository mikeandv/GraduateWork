package anno;


//import entity.Request;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequestMap {
    String name();
    String[] value();
    String method();
    String filename() default "";
    String[] headers() default "";
//    Class<?> request() default Request.class;

}
