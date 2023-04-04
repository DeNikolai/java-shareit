package ru.practicum.shareit.item.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ItemDoesNotExistException;
import ru.practicum.shareit.exception.UserDoesNotExistException;
import ru.practicum.shareit.exception.UserIsNotOwnerException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService{
	ItemRepository itemRepository;
	UserRepository userRepository;

	@Autowired
	public ItemServiceImpl(ItemRepository itemRepository, UserRepository userRepository) {
		this.itemRepository = itemRepository;
		this.userRepository = userRepository;
	}

	@Override
	public Item addItem(Item item, long userId) {
		log.debug("Request to add item {} from user with id = {}.", item, userId);

		userRepository.getUser(userId).orElseThrow(
				() -> new UserDoesNotExistException("User with id = " + userId + " does not exist.")
		);

		item.setOwner(userId);
		return itemRepository.addItem(item);
	}

	@Override
	public Item updateItem(ItemDto itemDto, long userId, long itemId) {
		log.debug("Request to update item {} with id = {} by user with id = {}.", itemDto, itemId, userId);

		userRepository.getUser(userId).orElseThrow(
				() -> new UserDoesNotExistException("User with id = " + userId + " does not exist.")
		);

		Optional<Item> item = itemRepository.getItem(itemId);
		item.orElseThrow(
				() -> new ItemDoesNotExistException("Item with id = " + itemId + " does not exist.")
		);

		if(!item.get().getOwner().equals(userId))
			throw new UserIsNotOwnerException("Only owners can edit an item.");

		updateItem(item.get(), itemDto);
		itemRepository.updateItem(item.get());

		return item.get();
	}

	private void updateItem(Item item, ItemDto itemDto) {
		if (itemDto.getName() != null)
			item.setName(itemDto.getName());
		if (itemDto.getDescription() != null)
			item.setDescription(itemDto.getDescription());
		if (itemDto.getAvailable() != null)
			item.setAvailable(itemDto.getAvailable());
		if (itemDto.getRequest() != null)
			item.setRequest(itemDto.getRequest());
	}

	@Override
	public Item getItem(long itemId) {
		log.debug("Request to get item with id = {}.", itemId);

		Optional<Item> item = itemRepository.getItem(itemId);
		item.orElseThrow(
				() -> new ItemDoesNotExistException("Item with id = " + itemId + " does not exist.")
		);

		return item.get();
	}

	@Override
	public List<Item> getUserItems(long userId) {
		log.debug("Request to get user items by user with id = {}.", userId);

		userRepository.getUser(userId).orElseThrow(
				() -> new UserDoesNotExistException("User with id = " + userId + " does not exist.")
		);

		return itemRepository.getOwnerItems(userId);
	}

	@Override
	public List<Item> getItemsByText(String text) {
		log.debug("Request to find items by search query = {}.", text);

		if (text.isBlank())
			return List.of();

		return itemRepository.getItemsByText(text);
	}
}
