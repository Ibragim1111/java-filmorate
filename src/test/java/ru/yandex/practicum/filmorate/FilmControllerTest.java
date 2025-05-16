package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;

import jakarta.validation.*;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    private FilmController controller;
    private Validator validator;

    @BeforeEach
    void setUp() {
        controller = new FilmController();
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void createValidFilm() {
        Film film = new Film();
        film.setName("Valid Film");
        film.setDescription("Valid Description");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(120);

        Film created = controller.create(film);
        assertNotNull(created.getId());
    }

    @Test
    void validateFilmWithEmptyName() {
        Film film = new Film();
        film.setName("");
        film.setDescription("Description");
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
        film.setDuration(120);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
    }
}
