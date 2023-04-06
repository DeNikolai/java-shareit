package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
	User adduser(User user);

	void updateUser(User user);

	Optional<User> getUser(long id);

	List<User> getUsers();

	void deleteUser(long id);

	boolean isUserExist(long userId);

	boolean isNameAvailable(User user);

	boolean isEmailAvailable(User user);
}
