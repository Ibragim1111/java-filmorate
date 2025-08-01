package ru.yandex.practicum.filmorate.model.film;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import ru.yandex.practicum.filmorate.release.film.MinReleaseDate;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.experimental.FieldDefaults;



import java.util.HashSet;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@AllArgsConstructor
public class Film {
    Long id;

    @NotBlank(message = "Название не должно быть пустым")
    String name;

    @Size(max = 200, message = "Максимальная длина описания — 200 символов")
    String description;

    @MinReleaseDate
    LocalDate releaseDate;

    @Positive(message = "Продолжительность фильма должна быть положительным числом")
    int duration;

    Set<Genre> genres;

    @JsonProperty("mpa")
    MpaRating mpaRating;

    final Set<Long> movieRating = new HashSet<>();
}