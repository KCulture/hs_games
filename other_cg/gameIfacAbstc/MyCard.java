package gameIfacAbstc;

import java.lang.annotation.*;
@Retention(RetentionPolicy.RUNTIME)
public @interface MyCard {
  String str() default "";
  int val() default 0; 
}
