package ru.yandex.practicum.filmorate.exepcions.сustom;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String error;
    private String description;
}
