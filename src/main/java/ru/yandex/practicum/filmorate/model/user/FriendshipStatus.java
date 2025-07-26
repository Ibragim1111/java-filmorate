package ru.yandex.practicum.filmorate.model.user;

import lombok.Getter;

@Getter
public enum FriendshipStatus {
    PENDING("неподтверждённая"),
    CONFIRMED("подтверждённая");

    private final String description;

    FriendshipStatus(String description) {
        this.description = description;
    }

}
