package ru.yandex.practicum.filmorate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;
import jakarta.validation.*;

import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
public class FiltrateUserTests {
    private UserService service;
    private Validator validator;

    @BeforeEach
    void setUp() {
         service = new UserService(new InMemoryUserStorage());
        validator = Validation.buildDefaultValidatorFactory().getValidator();

    }

    @Test
    void createValidUser() {
        User user = new User();
        user.setEmail("valid@email.com");
        user.setLogin("validLogin");
        user.setBirthday(LocalDate.of(2000, 1, 1));

        User created = service.create(user);
        assertNotNull(created.getId());
    }

    @Test
    void validateUserWithInvalidEmail() {
        User user = new User();
        user.setEmail("invalid-email");
        user.setLogin("validLogin");
        user.setBirthday(LocalDate.of(2000, 1, 1));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
    }

}
