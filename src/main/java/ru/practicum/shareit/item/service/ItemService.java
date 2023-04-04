package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
	Item addItem(Item item, long userId);
	Item updateItem(ItemDto itemDto, long userId, long itemId);
	Item getItem(long itemId);
	List<Item> getUserItems(long userId);
	List<Item> getItemsByText(String text);
}
