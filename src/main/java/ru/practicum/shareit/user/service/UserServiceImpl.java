package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.EmailIsNotAvailableException;
import ru.practicum.shareit.exception.NameIsNotAvailableException;
import ru.practicum.shareit.exception.UserDoesNotExistException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserDtoMapper;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;

	@Override
	public User addUser(User user) {
		log.debug("Request to add user {}.", user);

		if (!userRepository.isNameAvailable(user))
			throw new NameIsNotAvailableException("Name " + user.getName() + " is not available.");
		if (!userRepository.isEmailAvailable(user))
			throw new EmailIsNotAvailableException("Email " + user.getEmail() + " is not available.");

		return userRepository.adduser(user);
	}

	@Override
	public User updateUser(UserDto userDto, long userId) {
		log.debug("Request to update user = {}, with id = {}.", userDto, userId);

		if (!userRepository.isUserExist(userId))
			throw new UserDoesNotExistException("User with id = " + userId + " does not exist.");

		User user = UserDtoMapper.updateUser(userRepository.getUser(userId).get(), userDto);

		if (!userRepository.isNameAvailable(user))
			throw new NameIsNotAvailableException("Name " + user.getName() + " is not available.");
		if (!userRepository.isEmailAvailable(user))
			throw new EmailIsNotAvailableException("Email " + user.getEmail() + " is not available.");

		userRepository.updateUser(user);
		return user;
	}

	@Override
	public User getUser(long userId) {
		log.debug("Request to get user with id = {}.", userId);

		return userRepository.getUser(userId).orElseThrow(
				() -> new UserDoesNotExistException("User with id = " + userId + " does not exist.")
		);
	}

	@Override
	public List<User> getUsers() {
		log.debug("Request to get users.");

		return userRepository.getUsers();
	}

	@Override
	public void deleteUser(long userId) {
		log.debug("Request to delete user with id = {}.", userId);

		if (!userRepository.isUserExist(userId))
			throw new UserDoesNotExistException("User with id = " + userId + " does not exist.");

		userRepository.deleteUser(userId);
	}
}
