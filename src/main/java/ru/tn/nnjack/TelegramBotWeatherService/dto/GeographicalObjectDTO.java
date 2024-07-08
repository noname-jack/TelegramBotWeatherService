package ru.tn.nnjack.TelegramBotWeatherService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GeographicalObjectDTO {
    private int id;
    private String name;
    private String kind;
    private String country;
    private String district;
    private String subDistrict;
}
