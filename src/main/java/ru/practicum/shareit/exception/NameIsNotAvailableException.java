package ru.practicum.shareit.exception;

public class NameIsNotAvailableException extends RuntimeException {
	public NameIsNotAvailableException(String message) {
		super(message);
	}
}
