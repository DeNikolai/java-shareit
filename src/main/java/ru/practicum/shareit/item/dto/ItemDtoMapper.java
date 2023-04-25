package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.Item;

public class ItemDtoMapper {
	public static void updateItem(Item item, ItemDto itemDto) {
		if (itemDto.getName() != null)
			item.setName(itemDto.getName());
		if (itemDto.getDescription() != null)
			item.setDescription(itemDto.getDescription());
		if (itemDto.getAvailable() != null)
			item.setAvailable(itemDto.getAvailable());
		if (itemDto.getRequest() != null)
			item.setRequest(itemDto.getRequest());
	}
}
