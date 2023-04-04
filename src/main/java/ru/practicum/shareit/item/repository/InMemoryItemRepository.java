package ru.practicum.shareit.item.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class InMemoryItemRepository implements ItemRepository {
	private final HashMap<Long, Item> items;
	private long idCounter;

	public InMemoryItemRepository() {
		items = new HashMap<>();
		idCounter = 0;
	}

	@Override
	public Item addItem(Item item) {
		log.debug("Request to add item = {} in repository.", item);

		item.setId(++idCounter);
		items.put(item.getId(), item);
		return item;
	}

	@Override
	public void updateItem(Item item) {
		log.debug("Request to update item = {} in repository.", item);

		items.put(item.getId(), item);
	}

	@Override
	public Optional<Item> getItem(long id) {
		log.debug("Request to get item with id = {} from repository.", id);

		return Optional.ofNullable(items.get(id));
	}

	@Override
	public List<Item> getItems() {
		log.debug("Request to get all Items from repository.");

		return new ArrayList<>(items.values());
	}

	@Override
	public void deleteItem(long id) {
		log.debug("Request to delete item with id = {} from repository.", id);

		items.remove(id);
	}

	@Override
	public List<Item> getOwnerItems(long ownerId) {
		log.debug("Request to get owner items from repository. Owner id = {}.", ownerId);

		return items.values().stream()
				.filter(item -> item.getOwner().equals(ownerId))
				.collect(Collectors.toList());
	}

	@Override
	public List<Item> getItemsByText(String text) {
		log.debug("Request to find items from repository by search query = {}.", text);

		return items.values().stream()
				.filter(Item::getAvailable)
				.filter(
						item -> item.getDescription().toLowerCase().contains(text.toLowerCase())
								|| item.getName().toLowerCase().contains(text.toLowerCase())
				)
				.collect(Collectors.toList());
	}
}
