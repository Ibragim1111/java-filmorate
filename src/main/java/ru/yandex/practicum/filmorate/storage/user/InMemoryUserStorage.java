package ru.yandex.practicum.filmorate.storage.user;


import org.springframework.stereotype.Component;

import ru.yandex.practicum.filmorate.model.user.User;
import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private final Map<Integer, Set<Integer>> friends = new HashMap<>();
    private int idCounter = 1;

    @Override
    public User create(User user) {
        user.setId(idCounter++);
        users.put(user.getId(), user);
        friends.put(user.getId(), new HashSet<>());
        return user;
    }

    @Override
    public User update(User user) {

        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User getById(int id) {

        return users.get(id);
    }

    @Override
    public Collection<User> getAll() {
        return users.values();
    }

    @Override
    public void addFriend(int userId, int friendId) {

        friends.get(userId).add(friendId);
        friends.get(friendId).add(userId);
    }

    @Override
    public void removeFriend(int userId, int friendId) {
        friends.get(userId).remove(friendId);
        friends.get(friendId).remove(userId);
    }

    @Override
    public Collection<User> getFriends(int userId) {
        return friends.get(userId).stream()
                .map(users::get)
                .toList();
    }

    @Override
    public Collection<User> getCommonFriends(int userId, int otherId) {
        Set<Integer> commonIds = new HashSet<>(friends.get(userId));
        commonIds.retainAll(friends.get(otherId));
        return commonIds.stream()
                .map(users::get)
                .toList();
    }


}
