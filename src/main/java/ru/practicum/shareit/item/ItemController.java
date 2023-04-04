package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
	ItemService itemService;

	@Autowired
	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}

	@PostMapping
	public Item addItem(@RequestBody @Valid Item item,
						@RequestHeader("X-Sharer-User-Id") long userId) {
		return itemService.addItem(item, userId);
	}

	@PatchMapping("/{itemId}")
	public Item updateItem(@RequestBody ItemDto itemDto,
						   @RequestHeader("X-Sharer-User-Id") long userId,
						   @PathVariable long itemId) {
		return itemService.updateItem(itemDto, userId, itemId);
	}

	@GetMapping("/{itemId}")
	public Item getItem(@PathVariable long itemId) {
		return itemService.getItem(itemId);
	}

	@GetMapping
	public List<Item> getUserItems(@RequestHeader("X-Sharer-User-Id") long userId) {
		return itemService.getUserItems(userId);
	}

	@GetMapping("/search")
	public List<Item> getItemsByText(@RequestParam String text) {
		return itemService.getItemsByText(text);
	}
}
