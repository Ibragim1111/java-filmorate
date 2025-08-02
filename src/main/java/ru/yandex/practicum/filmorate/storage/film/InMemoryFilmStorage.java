package ru.yandex.practicum.filmorate.storage.film;


import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.model.film.Film;
import java.util.*;


@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private final Map<Integer, Set<Integer>> likes = new HashMap<>();
    private int idCounter = 1;

    @Override
    public Film create(Film film) {
        film.setId(idCounter++);
        films.put(film.getId(), film);
        likes.put(film.getId(), new HashSet<>());
        return film;
    }

    @Override
    public Film update(Film film) {
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film getById(int id) {
        return films.get(id);
    }

    @Override
    public Collection<Film> getAll() {
        return films.values();
    }

    @Override
    public void addLike(int filmId, int userId) {
        likes.get(filmId).add(userId);
    }

    @Override
    public void removeLike(int filmId, int userId) {
        likes.get(filmId).remove(userId);
    }

    @Override
    public Collection<Film> getPopular(int count) {
        return films.values().stream()
                .sorted(Comparator.comparingInt(f -> -likes.get(f.getId()).size()))
                .limit(count)
                .toList();
    }
}
