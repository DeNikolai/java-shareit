package ru.practicum.shareit.user;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class User {
	@EqualsAndHashCode.Exclude
	private long id;
	@NotBlank
	@NonNull
	private String name;
	@Email
	@NonNull
	private String email;
}
