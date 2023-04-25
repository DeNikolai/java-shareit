package ru.practicum.shareit.user.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.User;

import java.util.*;

@Repository
@Slf4j
public class InMemoryUserRepository implements UserRepository {
	private final HashMap<Long, User> users;

	private long idCounter;

	public InMemoryUserRepository() {
		users = new HashMap<>();
		idCounter = 0;
	}

	@Override
	public User adduser(User user) {
		log.debug("Request to add user {} in repository.", user);

		user.setId(++idCounter);
		users.put(user.getId(), user);
		return user;
	}

	@Override
	public void updateUser(User user) {
		log.debug("Request to update user {} in repository.", user);

		users.put(user.getId(), user);
	}

	@Override
	public Optional<User> getUser(long id) {
		log.debug("Request to get user with id = {} form repository.", id);

		return Optional.ofNullable(users.get(id));
	}

	@Override
	public List<User> getUsers() {
		log.debug("Request to get users from repository.");

		return new ArrayList<>(users.values());
	}

	@Override
	public void deleteUser(long id) {
		log.debug("Request to delete user with id = {} from repository.", id);

		users.remove(id);
	}

	@Override
	public boolean isUserExist(long userId) {
		return users.containsKey(userId);
	}

	@Override
	public boolean isNameAvailable(User user) {
		return users.values().stream()
				.filter(u -> !Objects.equals(u.getId(), user.getId()))
				.noneMatch(u -> u.getName().equals(user.getName()));
	}

	@Override
	public boolean isEmailAvailable(User user) {
		return users.values().stream()
				.filter(u -> !Objects.equals(u.getId(), user.getId()))
				.noneMatch(u -> u.getEmail().equals(user.getEmail()));
	}
}
