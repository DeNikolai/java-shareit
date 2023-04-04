package ru.practicum.shareit.item;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class Item {
	@EqualsAndHashCode.Exclude
	private Long id;
	@NonNull
	@NotBlank
	private String name;
	@NonNull
	@NotBlank
	private String description;
	@NonNull
	private Boolean available;
	private Long owner;
	private Long request;
}
