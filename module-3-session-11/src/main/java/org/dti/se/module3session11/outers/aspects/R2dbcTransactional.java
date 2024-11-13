package org.dti.se.module3session11.outers.aspects;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
public @interface R2dbcTransactional {
}
