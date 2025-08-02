package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;


import java.util.Collection;

@Service
public class UserService {
    private final InMemoryUserStorage userStorage;

    @Autowired
    public UserService(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User create(User user) {
        return userStorage.create(user);
    }

    public User update(User user) {

        if (userStorage.getById(user.getId()) == null) {
            throw new NotFoundException("Пользователь с id=" + user.getId() + " не найден");
        }

        return userStorage.update(user);
    }

    public User getById(int id) {

        User user = userStorage.getById(id);
        if (user == null) {
            throw new NotFoundException("Пользователь с id=" + id + " не найден");
        }
        return user;

    }

    public Collection<User> getAll() {
        return userStorage.getAll();
    }

    public void addFriend(int userId, int friendId) {

        if (userStorage.getById(userId) == null) {
            throw new NotFoundException("Пользователь с id=" + userId + " не найден");
        }
        if (userStorage.getById(friendId) == null) {
            throw new NotFoundException("Пользователь с id=" + friendId + " не найден");
        }

        userStorage.addFriend(userId, friendId);
    }

    public void removeFriend(int userId, int friendId) {

        if (userStorage.getById(userId) == null) {
            throw new NotFoundException("Пользователь с id=" + userId + " не найден");
        }
        if (userStorage.getById(friendId) == null) {
            throw new NotFoundException("Пользователь с id=" + friendId + " не найден");
        }

        userStorage.removeFriend(userId, friendId);
    }

    public Collection<User> getFriends(int userId) {

        if (userStorage.getById(userId) == null) {
            throw new NotFoundException("Пользователь с id=" + userId + " не найден");
        }

        return userStorage.getFriends(userId);
    }

    public Collection<User> getCommonFriends(int userId, int otherId) {

        if (userStorage.getById(userId) == null) {
            throw new NotFoundException("Пользователь с id=" + userId + " не найден");
        }
        if (userStorage.getById(otherId) == null) {
            throw new NotFoundException("Пользователь с id=" + otherId + " не найден");
        }

        return userStorage.getCommonFriends(userId, otherId);
    }
}
