package ru.tn.nnjack.TelegramBotWeatherService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
public class WeatherDTO {
    private long dateUnix;
    private double temperatureAirC;
    private String icon;
    private String descriptionFull;
    private String precipitationType;
    private double precipitationAmount;
    private String windDirection;
    private int windSpeedMS;
    private int cityCode;

    public String toString(String langCode){

        Instant instant = Instant.ofEpochSecond(dateUnix);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneId.of("Europe/Moscow"));

        String formattedDateTime = formatter.format(instant);

        return "ru".equalsIgnoreCase(langCode) ?

                "Время прогноза: " + formattedDateTime +"\n"
                + "Температура: " + temperatureAirC + " °C" + "\n"
                + descriptionFull + "\n"
                + precipitationType +". Количество осадков: " + precipitationAmount + " мм." +"\n"
                + "Скорость ветра: " + windSpeedMS + " м/c. " + "Ветер: " + windDirection

                :

                "Forecast time: " + formattedDateTime +"\n"
                        + "Temperature: " + temperatureAirC + " °C" + "\n"
                        + descriptionFull + "\n"
                        + precipitationType + ". Precipitation amount: " + precipitationAmount + " mm." +"\n"
                        + "Wind speed: " + windSpeedMS + " m/s. " + "Wind: " + windDirection;

    }
}
