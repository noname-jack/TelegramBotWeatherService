package ru.tn.nnjack.TelegramBotWeatherService.client.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponseDTO {
    private Date timestamp;
    private String message;
    private int statusCode;

    public ErrorResponseDTO(Date timestamp, String message, int statusCode) {
        this.timestamp = timestamp;
        this.message = message;
        this.statusCode = statusCode;
    }

}
