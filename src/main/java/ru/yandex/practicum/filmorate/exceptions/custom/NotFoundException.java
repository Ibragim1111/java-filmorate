package ru.yandex.practicum.filmorate.exceptions.custom;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
