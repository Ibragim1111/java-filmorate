package ru.yandex.practicum.filmorate.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.film.Film;

import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.util.Collection;
import java.util.Optional;


@Service
public class FilmService {
    private final InMemoryFilmStorage filmStorage;

    private final UserService userService;

    @Autowired
    public FilmService(InMemoryFilmStorage filmStorage, UserService userService) {
        this.filmStorage = filmStorage;
        this.userService = userService;

    }

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film update(Film film) {

        if (filmStorage.getById(film.getId()) == null) {
            throw new NotFoundException("Фильм с id=" + film.getId() + " не найден");
        }

        return filmStorage.update(film);
    }

    public Film getById(int id) {

        return Optional.ofNullable(filmStorage.getById(id))
                .orElseThrow(() -> new NotFoundException("Фильм с id=" + id + " не найден"));
    }


    public Collection<Film> getAll() {
        return filmStorage.getAll();
    }

    public void addLike(int filmId, int userId) {

        if (filmStorage.getById(filmId) == null) {
            throw new NotFoundException("Фильм с id=" + filmId + " не найден");
        }

        userService.getById(userId);
        filmStorage.addLike(filmId, userId);
    }

    public void removeLike(int filmId, int userId) {


        if (filmStorage.getById(filmId) == null) {
            throw new NotFoundException("Фильм с id=" + filmId + " не найден");
        }

        userService.getById(userId);

        filmStorage.removeLike(filmId, userId);
    }

    public Collection<Film> getPopular(int count) {
        return filmStorage.getPopular(count == 0 ? 10 : count);
    }
}
