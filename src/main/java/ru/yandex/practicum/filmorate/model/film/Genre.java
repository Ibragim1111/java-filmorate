package ru.yandex.practicum.filmorate.model.film;

import lombok.Getter;

@Getter
enum Genre {
    COMEDY("Комедия"),
    DRAMA("Драма"),
    ANIMATION("Мультфильм"),
    THRILLER("Триллер"),
    DOCUMENTARY("Документальный"),
    ACTION("Боевик");

    private final String displayName;

    Genre(String displayName) {
        this.displayName = displayName;
    }

}


