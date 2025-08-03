package ru.yandex.practicum.filmorate.model.user;

import lombok.Data;
import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;


import java.time.LocalDate;

@Data
public class UpdateUserRequest {
    private Long id;

    @Email(message = "Некорректный формат email")
    private String email;

    @Pattern(regexp = "^$|^\\S+$", message = "Логин не должен содержать пробелы")
    private String login;

    private String name;

    @PastOrPresent(message = "Дата рождения не может быть в будущем")
    private LocalDate birthday;
}
