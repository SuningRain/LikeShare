package sunningrain.github.likeshare.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Create by 27837 on 2019/4/10.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CreatePresenter {
    Class<?>[] presenter() default {};
}
