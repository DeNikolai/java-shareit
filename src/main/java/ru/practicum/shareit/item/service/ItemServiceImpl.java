package ru.practicum.shareit.item.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ItemDoesNotExistException;
import ru.practicum.shareit.exception.UserDoesNotExistException;
import ru.practicum.shareit.exception.UserIsNotOwnerException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoMapper;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {
	private final ItemRepository itemRepository;
	private final UserRepository userRepository;

	@Override
	public Item addItem(Item item, long userId) {
		log.debug("Request to add item {} from user with id = {}.", item, userId);

		if (!userRepository.isUserExist(userId))
			throw new UserDoesNotExistException("User with id = " + userId + " does not exist.");

		item.setOwner(userId);
		return itemRepository.addItem(item);
	}

	@Override
	public Item updateItem(ItemDto itemDto, long userId, long itemId) {
		log.debug("Request to update item {} with id = {} by user with id = {}.", itemDto, itemId, userId);

		if (!userRepository.isUserExist(userId))
			throw new UserDoesNotExistException("User with id = " + userId + " does not exist.");

		if (!itemRepository.isItemExist(itemId))
			throw new ItemDoesNotExistException("Item with id = " + itemId + " does not exist.");

		Optional<Item> item = itemRepository.getItem(itemId);

		if (!item.get().getOwner().equals(userId))
			throw new UserIsNotOwnerException("Only owners can edit an item.");

		ItemDtoMapper.updateItem(item.get(), itemDto);
		itemRepository.updateItem(item.get());

		return item.get();
	}

	@Override
	public Item getItem(long itemId) {
		log.debug("Request to get item with id = {}.", itemId);

		return itemRepository.getItem(itemId).orElseThrow(
				() -> new ItemDoesNotExistException("Item with id = " + itemId + " does not exist.")
		);
	}

	@Override
	public List<Item> getUserItems(long userId) {
		log.debug("Request to get user items by user with id = {}.", userId);

		if (!userRepository.isUserExist(userId))
			throw new UserDoesNotExistException("User with id = " + userId + " does not exist.");

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
