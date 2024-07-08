package ru.tn.nnjack.TelegramBotWeatherService.client.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExternalApiException extends RuntimeException {
    private final int statusCode;
    private final String errorMessage;

    public ExternalApiException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
        this.errorMessage = message;
    }

}
