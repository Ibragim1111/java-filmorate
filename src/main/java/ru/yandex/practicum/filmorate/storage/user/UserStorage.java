package ru.yandex.practicum.filmorate.storage.user;


import ru.yandex.practicum.filmorate.model.user.User;
import java.util.Collection;

public interface UserStorage {
    User create(User user);

    User update(User user);

    User getById(int id);

    Collection<User> getAll();

    void addFriend(int userId, int friendId);

    void removeFriend(int userId, int friendId);

    Collection<User> getFriends(int userId);

    Collection<User> getCommonFriends(int userId, int otherId);
}