package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.custom.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import jakarta.validation.Valid;

import java.util.*;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private int idCounter = 1;

    @GetMapping
    public Collection<User> findAll() {
        log.info("Getting all users");
        return users.values();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        user.setId(idCounter++);
        users.put(user.getId(), user);
        log.info("Created new user: {}", user);
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        if (!users.containsKey(user.getId())) {
            log.error("User with id {} not found", user.getId());
            throw new NotFoundException("User not found");
        }
        users.put(user.getId(), user);
        log.info("Updated user: {}", user);
        return user;
    }
}