package ru.yandex.practicum.filmorate.controller.user;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.model.user.NewUserRequest;
import ru.yandex.practicum.filmorate.model.user.UpdateUserRequest;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDto create(@Valid @RequestBody NewUserRequest user) {
        return userService.create(user);
    }

    @PutMapping
    public UserDto update(@Valid @RequestBody UpdateUserRequest user) {
        return userService.update(user);
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable("id") Long userId) {
        return userService.findById(userId);
    }

    @GetMapping
    public Collection<UserDto> getUsers() {
        return userService.getUsers();
    }
}