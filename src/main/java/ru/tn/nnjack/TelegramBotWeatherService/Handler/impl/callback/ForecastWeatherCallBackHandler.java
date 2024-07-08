package ru.tn.nnjack.TelegramBotWeatherService.Handler.impl.callback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.tn.nnjack.TelegramBotWeatherService.Handler.Handler;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.HandlerType;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.MessageToBot;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.UpdateInfo;
import ru.tn.nnjack.TelegramBotWeatherService.client.GismeteoApiParserClient;
import ru.tn.nnjack.TelegramBotWeatherService.client.exception.ExternalApiException;
import ru.tn.nnjack.TelegramBotWeatherService.db.entity.User;
import ru.tn.nnjack.TelegramBotWeatherService.dto.WeatherDTO;

import java.util.List;

@Component
public class ForecastWeatherCallBackHandler implements Handler {
    private final GismeteoApiParserClient gismeteoApiParserClient;

    public ForecastWeatherCallBackHandler(GismeteoApiParserClient gismeteoApiParserClient) {
        this.gismeteoApiParserClient = gismeteoApiParserClient;
    }

    @Getter
    @Setter
    public static class ForecastJson {
        private int forecast;
        private int id;
    }

    @Override
    public HandlerType getHandlingType() {
        return HandlerType.CALLBACK;
    }

    @Override
    public String getNameHandler(String langCode) {
        return "{\"forecast\"";
    }

    @Override
    public SendMessage sendingResponse(UpdateInfo updateInfo, User user) {
        String json = updateInfo.getUpdate().getCallbackQuery().getData();
        String langCode = user.getLanguage();
        String messageText;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ForecastJson forecastJson = objectMapper.readValue(json, ForecastJson.class);
            List<WeatherDTO> weatherDTOList = gismeteoApiParserClient.getForecastWeather(
                    forecastJson.getId(), langCode, forecastJson.getForecast());
            if (!weatherDTOList.isEmpty()) {
                StringBuilder messageStringBuilder = new StringBuilder();

                for (WeatherDTO weatherDTO : weatherDTOList) {
                    messageStringBuilder.append(weatherDTO.toString(langCode));
                    messageStringBuilder.append("\n\n");
                }
                messageText = messageStringBuilder.toString();
            } else {
                messageText = MessageToBot.getLocalizedMessage(langCode, MessageToBot.ERROR_GETTING_WEATHER_FORECAST_RU, MessageToBot.ERROR_GETTING_WEATHER_FORECAST_EN);
            }
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
            messageText = MessageToBot.getLocalizedMessage(langCode, MessageToBot.ERROR_GETTING_WEATHER_FORECAST_RU, MessageToBot.ERROR_GETTING_WEATHER_FORECAST_EN);
        }
        catch (ExternalApiException e) {
            messageText = "Error in the external api: " + e.getMessage();
        }
        return MessageToBot.createMessage(updateInfo.getChatId(), messageText);

    }
}
