package ru.practicum.shareit.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.exception.*;

@RestControllerAdvice
public class ErrorHandler {
	@ExceptionHandler({ItemDoesNotExistException.class, UserDoesNotExistException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleNotExist(RuntimeException e) {
		return e.getMessage();
	}

	@ExceptionHandler({UserIsNotOwnerException.class})
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public String handleNotOwner(RuntimeException e) {
		return e.getMessage();
	}

	@ExceptionHandler({EmailIsNotAvailableException.class, NameIsNotAvailableException.class})
	@ResponseStatus(HttpStatus.CONFLICT)
	public String notAvailableHandler(RuntimeException e) {
		return e.getMessage();
	}
}
