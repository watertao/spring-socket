package net.watertao.springsock.infras.framework;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface API {
    String value();
}
