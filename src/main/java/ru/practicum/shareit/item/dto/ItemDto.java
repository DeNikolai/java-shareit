package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class ItemDto {
	@NotBlank
	private String name;
	@NotBlank
	private String description;
	private Boolean available;
	private Long request;
}
