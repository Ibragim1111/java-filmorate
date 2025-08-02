package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.user.NewUserRequest;
import ru.yandex.practicum.filmorate.model.user.UpdateUserRequest;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.mappers.UserMapper;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(@Qualifier("userDbStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public UserDto create(NewUserRequest request) {

        User user = UserMapper.mapToUser(request);

        userStorage.create(user);
        log.info("Добавлен новый юзер \"{}\" c id {}", user.getLogin(), user.getId());
        return UserMapper.mapToUserDto(user);
    }

    public UserDto update(UpdateUserRequest request) {
        User updatedUser = userStorage.findById(request.getId())
                .map(user -> UserMapper.updateUserFields(user, request))
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        updatedUser = userStorage.update(updatedUser);
        return UserMapper.mapToUserDto(updatedUser);


    }

    public UserDto findById(Long userId) {
        return userStorage.findById(userId).map(UserMapper::mapToUserDto)
                .orElseThrow(() -> new NotFoundException("Пользователь с id = " + userId + " не найден"));
    }

    public Collection<UserDto> getUsers() {
        return userStorage.getUsers().stream().map(UserMapper::mapToUserDto).collect(Collectors.toList());
    }

    public void addFriend(Long userId, Long friendId) {
        if (userId.equals(friendId)) {
            throw new ValidationException("Нельзя добавить самого себя в друзья");
        }
        UserDto user = findById(userId);
        UserDto friend = findById(friendId);

        userStorage.addFriend(userId, friendId);
        log.info("{} отправил заявку {} на добавление в друзья!", user.getName(), friend.getName());

        if (userStorage.hasFriendRequest(friendId, userId)) {
            userStorage.confirmFriendship(userId, friendId);
            userStorage.confirmFriendship(friendId, userId);
            log.info("{} и {} подтвердили дружбу!", user.getName(), friend.getName());
        }
    }

    public void deleteFriend(Long userId, Long friendId) {
        if (userId.equals(friendId)) {
            throw new ValidationException("Нельзя удалить самого себя из друзей");
        }
        UserDto user = findById(userId);
        UserDto friend = findById(friendId);

        userStorage.deleteFriend(userId, friendId);
        log.info("{} и {} больше не друзья!", user.getName(), friend.getName());
    }

    public List<UserDto> commonFriends(Long userId, Long friendId) {
        UserDto user = findById(userId);
        UserDto friend = findById(friendId);

        return userStorage.getCommonFriends(userId, friendId).stream().map(UserMapper::mapToUserDto).collect(Collectors.toList());
    }

    public List<UserDto> getFriends(Long userId) {
        userStorage.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id = " + userId + " не найден"));

        return userStorage.getFriends(userId).stream().map(UserMapper::mapToUserDto).collect(Collectors.toList());
    }


}
