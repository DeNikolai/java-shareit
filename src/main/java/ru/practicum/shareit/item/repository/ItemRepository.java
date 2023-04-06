package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
	Item addItem(Item item);

	void updateItem(Item item);

	Optional<Item> getItem(long id);

	List<Item> getItems();

	void deleteItem(long id);

	List<Item> getOwnerItems(long ownerId);

	List<Item> getItemsByText(String text);

	boolean isItemExist(long itemId);
}
