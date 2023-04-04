package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {
	User addUser(User user);
	User updateUser(UserDto userDto, long userId);
	User getUser(long userId);
	List<User> getUsers();
	void deleteUser(long userId);
}
