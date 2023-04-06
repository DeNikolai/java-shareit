package ru.practicum.shareit.item;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class Item {
	@EqualsAndHashCode.Exclude
	private Long id;
	@NotNull
	@NotBlank
	private String name;
	@NotNull
	@NotBlank
	private String description;
	@NotNull
	private Boolean available;
	private Long owner;
	private Long request;
}
