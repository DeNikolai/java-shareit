package ru.practicum.shareit.user;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class User {
	@EqualsAndHashCode.Exclude
	private Long id;
	@NotBlank
	@NotNull
	private String name;
	@Email
	@NotNull
	private String email;
}
