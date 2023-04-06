package ru.practicum.shareit.user.dto;

import ru.practicum.shareit.user.User;

public class UserDtoMapper {
	public static User updateUser(User user, UserDto userDto) {
		User u = User.builder()
				.id(user.getId())
				.name(user.getName())
				.email(user.getEmail())
				.build();

		if (userDto.getName() != null)
			u.setName(userDto.getName());
		if (userDto.getEmail() != null)
			u.setEmail(userDto.getEmail());

		return u;
	}
}
