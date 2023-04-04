package ru.practicum.shareit.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.EmailIsNotAvailableException;
import ru.practicum.shareit.exception.NameIsNotAvailableException;
import ru.practicum.shareit.exception.UserDoesNotExistException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
	UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User addUser(User user) {
		log.debug("Request to add user {}.", user);

		List<User> users = userRepository.getUsers();
		isNameAvailable(user.getName(), users);
		isEmailAvailable(user.getEmail(), users);

		return userRepository.adduser(user);
	}

	private void isNameAvailable(String name, List<User> users) {
		if (name == null)
			return;
		if (users.isEmpty())
			return;

		users.stream()
				.filter(user -> user.getName().equals(name))
				.findAny()
				.ifPresent( user -> {
					throw new NameIsNotAvailableException("Name " + name + " is not available.");
				});
	}

	private void isEmailAvailable(String email, List<User> users) {
		if (email == null)
			return;
		if (users.isEmpty())
			return;

		users.stream()
				.filter(user -> user.getEmail().equals(email))
				.findAny()
				.ifPresent( user -> {
					throw new EmailIsNotAvailableException("Email " + email + " is not available.");
				});
	}

	@Override
	public User updateUser(UserDto userDto, long userId) {
		log.debug("Request to update user = {}, with id = {}.", userDto, userId);

		Optional<User> user = userRepository.getUser(userId);
		user.orElseThrow(
				() -> new UserDoesNotExistException("User with id = " + userId + " does not exist.")
		);

		List<User> users = userRepository.getUsers();
		users.remove(user.get());
		isNameAvailable(userDto.getName(), users);
		isEmailAvailable(userDto.getEmail(), users);

		if (userDto.getName() != null)
			user.get().setName(userDto.getName());
		if (userDto.getEmail() != null)
			user.get().setEmail(userDto.getEmail());

		userRepository.updateUser(user.get());
		return user.get();
	}

	@Override
	public User getUser(long userId) {
		log.debug("Request to get user with id = {}.", userId);

		Optional<User> userFromRepository = userRepository.getUser(userId);
		userFromRepository.orElseThrow(
				() -> new UserDoesNotExistException("User with id = " + userId + " does not exist.")
		);

		return userFromRepository.get();
	}

	@Override
	public List<User> getUsers() {
		log.debug("Request to get users.");

		return userRepository.getUsers();
	}

	@Override
	public void deleteUser(long userId) {
		log.debug("Request to delete user with id = {}.", userId);

		userRepository.getUser(userId).orElseThrow(
				() -> new UserDoesNotExistException("User with id = " + userId + " does not exist.")
		);

		userRepository.deleteUser(userId);
	}
}
