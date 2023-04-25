package ru.practicum.shareit.booking;

public enum BookingStatus {
	WAITING, //Новое бронирование
	APPROVED, //Бронирование подтверждено владельцем
	REJECTED, //Бронирование отклонено владельцем
	CANCELED //Бронирование отменено создателем
}
