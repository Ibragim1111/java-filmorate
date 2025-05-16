package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exepcions.сustom.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import jakarta.validation.Valid;

import java.util.*;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private int idCounter = 1;

    @GetMapping
    public Collection<Film> findAll() {
        log.info("Getting all films");
        return films.values();
    }

    @PostMapping
    public Film create( @Valid @RequestBody Film film) {
        film.setId(idCounter++);
        films.put(film.getId(), film);
        log.info("Created new film: {}", film);
        return film;
    }

    @PutMapping
    public Film update( @Valid @RequestBody Film film) {

        if (!films.containsKey(film.getId())) {
            log.error("Film with id {} not found", film.getId());
            throw new NotFoundException("Film not found");
        }

        films.put(film.getId(), film);
        log.info("Updated film: {}", film);
        return film;
    }
}