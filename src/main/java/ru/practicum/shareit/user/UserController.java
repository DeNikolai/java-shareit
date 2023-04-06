package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
@AllArgsConstructor
public class UserController {
	private final UserService userService;

	@PostMapping
	public User addUser(@RequestBody @Valid User user) {
		return userService.addUser(user);
	}

	@PatchMapping("/{userId}")
	public User updateUser(@RequestBody UserDto userDto,
						   @PathVariable long userId) {
		return userService.updateUser(userDto, userId);
	}

	@GetMapping("/{userId}")
	public User getUser(@PathVariable long userId) {
		return userService.getUser(userId);
	}

	@GetMapping
	public List<User> getUsers() {
		return userService.getUsers();
	}

	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable long userId) {
		userService.deleteUser(userId);
	}
}
