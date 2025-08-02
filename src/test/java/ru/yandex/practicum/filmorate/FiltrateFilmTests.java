package ru.yandex.practicum.filmorate;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.film.Film;

import jakarta.validation.*;
import ru.yandex.practicum.filmorate.service.FilmService;

import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;


import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FiltrateFilmTests {
	private FilmController controller;
	private Validator validator;

	private FilmService filmService;

	@BeforeEach
	void setUp() {

		filmService = new FilmService(new InMemoryFilmStorage(), new UserService(new InMemoryUserStorage()));

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void shouldAddFilm() {
		Film film = new Film();
		film.setName("Test Film");
		film.setDescription("Test Description");
		film.setReleaseDate(LocalDate.of(2000, 1, 1));
		film.setDuration(120);

		Film created = filmService.create(film);
		assertNotNull(created.getId());
		assertEquals(1, filmService.getAll().size());
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