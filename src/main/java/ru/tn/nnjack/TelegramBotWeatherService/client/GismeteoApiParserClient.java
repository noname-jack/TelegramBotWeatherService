package ru.tn.nnjack.TelegramBotWeatherService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.tn.nnjack.TelegramBotWeatherService.dto.GeographicalObjectDTO;
import ru.tn.nnjack.TelegramBotWeatherService.dto.WeatherDTO;

import java.util.List;

@FeignClient(name = "GismeteoApiParserClient", url = "http://localhost:8080")
public interface GismeteoApiParserClient {
    @GetMapping("/api/weather/current")
    WeatherDTO getCurrentWeather(
            @RequestParam ("id") int id,
            @RequestParam("lang") String lang);

    @GetMapping("/api/weather/forecast")
    List<WeatherDTO> getForecastWeather(
            @RequestParam("id") int id,
            @RequestParam("lang") String lang,
            @RequestParam("days") int days);
    @GetMapping("/api/search/cities/location")
    List<GeographicalObjectDTO> searchGeogObjectByCoordinates(
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude,
            @RequestParam("lang")  String lang,
            @RequestParam("limit") int limit);

    @GetMapping("/api/search/cities/string")
    List<GeographicalObjectDTO> searchGeogObjectByString(
            @RequestParam("query") String query,
            @RequestParam("lang")  String lang,
            @RequestParam("limit") int limit);
}
