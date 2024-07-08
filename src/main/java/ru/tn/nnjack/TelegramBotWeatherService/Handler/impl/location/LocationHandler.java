package ru.tn.nnjack.TelegramBotWeatherService.Handler.impl.location;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.tn.nnjack.TelegramBotWeatherService.Handler.Handler;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.*;
import ru.tn.nnjack.TelegramBotWeatherService.client.GismeteoApiParserClient;
import ru.tn.nnjack.TelegramBotWeatherService.client.exception.ExternalApiException;
import ru.tn.nnjack.TelegramBotWeatherService.db.entity.GeographicalObject;
import ru.tn.nnjack.TelegramBotWeatherService.db.entity.User;
import ru.tn.nnjack.TelegramBotWeatherService.dto.GeographicalObjectDTO;
import ru.tn.nnjack.TelegramBotWeatherService.service.DbService;

import java.util.List;

@Component
public class LocationHandler implements Handler {
    private final  GismeteoApiParserClient gismeteoApiParserClient;
    private final DbService dbService;
    public LocationHandler(GismeteoApiParserClient gismeteoApiParserClient, DbService dbService) {
        this.gismeteoApiParserClient = gismeteoApiParserClient;
        this.dbService = dbService;
    }

    @Override
    public HandlerType getHandlingType() {
        return HandlerType.LOCATION;
    }

    @Override
    public String getNameHandler(String langCode) {
        return HandlerNames.getHandlerName(langCode, HandlerNames.LOCATION_HANDLER_RU, HandlerNames.LOCATION_HANDLER_EN);
    }

    @Override
    public SendMessage sendingResponse(UpdateInfo updateInfo, User user) {
        String langCode = user.getLanguage();
        Message messageLocation = updateInfo.getUpdate().getMessage();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(updateInfo.getChatId());

        if (messageLocation != null && messageLocation.hasLocation()) {
            Location location = messageLocation.getLocation();
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            String messageText;
            try {
                List<GeographicalObjectDTO> geographicalObjectListRU = gismeteoApiParserClient.searchGeogObjectByCoordinates(latitude, longitude,"ru", 5);
                List<GeographicalObjectDTO> geographicalObjectListEN = gismeteoApiParserClient.searchGeogObjectByCoordinates(latitude, longitude,"en", 5);
                if (geographicalObjectListEN.isEmpty() || geographicalObjectListRU.isEmpty()){
                    messageText = MessageToBot.getLocalizedMessage(langCode,MessageToBot.CANNOT_VIEW_WEATHER_RU,MessageToBot.CANNOT_VIEW_WEATHER_EN);
                }
                else{
                    List<GeographicalObject> geographicalObjectList = dbService.saveGeographicalObjects(geographicalObjectListRU,geographicalObjectListEN);
                    messageText = MessageToBot.getLocalizedMessage(langCode,MessageToBot.CHOOSE_NEARBY_LOCATIONS_RU,MessageToBot.CHOOSE_NEARBY_LOCATIONS_EN);
                    InlineKeyboardMarkup markupInline = CreateKeyboard.sendGeogObjectsInlineKeyboard(langCode, geographicalObjectList);
                    sendMessage.setReplyMarkup(markupInline);
                }
            }
            catch (ExternalApiException e) {
                messageText = "Error in the external api: " + e.getMessage();
            }
            sendMessage.setText(messageText);
            return sendMessage;
        }
        return null;

    }
}
