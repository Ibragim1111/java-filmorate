package ru.yandex.practicum.filmorate.release.film;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MinReleaseDateValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MinReleaseDate {
    String message() default "Дата релиза не может быть раньше 28 декабря 1895 года";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
