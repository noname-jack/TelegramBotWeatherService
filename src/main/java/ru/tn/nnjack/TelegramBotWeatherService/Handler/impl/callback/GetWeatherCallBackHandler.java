package ru.tn.nnjack.TelegramBotWeatherService.Handler.impl.callback;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.tn.nnjack.TelegramBotWeatherService.Handler.Handler;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.CreateKeyboard;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.HandlerType;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.MessageToBot;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.UpdateInfo;
import ru.tn.nnjack.TelegramBotWeatherService.client.GismeteoApiParserClient;
import ru.tn.nnjack.TelegramBotWeatherService.client.exception.ExternalApiException;
import ru.tn.nnjack.TelegramBotWeatherService.db.entity.GeographicalObject;
import ru.tn.nnjack.TelegramBotWeatherService.db.entity.User;
import ru.tn.nnjack.TelegramBotWeatherService.dto.WeatherDTO;
import ru.tn.nnjack.TelegramBotWeatherService.service.DbService;

import java.util.Optional;

@Component
public class GetWeatherCallBackHandler implements Handler {

    private final DbService dbService;
    private final GismeteoApiParserClient gismeteoApiParserClient;

    public GetWeatherCallBackHandler(DbService dbService, GismeteoApiParserClient gismeteoApiParserClient) {
        this.dbService = dbService;
        this.gismeteoApiParserClient = gismeteoApiParserClient;
    }

    @Override
    public HandlerType getHandlingType() {
        return HandlerType.CALLBACK;
    }

    @Override
    public String getNameHandler(String langCode) {
        return "geogObject: ";
    }

    @Override
    public SendMessage sendingResponse(UpdateInfo updateInfo, User user) {
        String langCode = user.getLanguage();
        SendMessage message = new SendMessage();
        message.setChatId(updateInfo.getChatId());
        int idGeogObject = Integer.parseInt(updateInfo.getUpdate().getCallbackQuery().getData().substring("geogObject: ".length()));
        Optional<GeographicalObject> geographicalObjectOptional = dbService.findGeographicalObjectsById(idGeogObject);
        String messageText;
        try {
            if (geographicalObjectOptional.isPresent()){
                GeographicalObject geographicalObject = geographicalObjectOptional.get();
                WeatherDTO weatherDTO = gismeteoApiParserClient.getCurrentWeather(idGeogObject, langCode);
                messageText = MessageToBot.getLocalizedMessage(langCode,MessageToBot.CURRENT_WEATHER_RU,MessageToBot.CURRENT_WEATHER_EN)
                        + geographicalObject.toStringShort(langCode) + "\n"
                        + weatherDTO.toString(langCode);
                InlineKeyboardMarkup markupInline = CreateKeyboard.sendForecastWeatherInlineKeyboard(langCode, idGeogObject);
                message.setReplyMarkup(markupInline);
            }
            else{
                messageText = MessageToBot.getLocalizedMessage(langCode,MessageToBot.ERROR_ADDING_LOCATION_RU,MessageToBot.ERROR_ADDING_LOCATION_EN);
            }
        }
        catch (ExternalApiException e) {
            messageText = "Error in the external api: " + e.getMessage();
        }
        message.setText(messageText);
        return message;
    }
}
