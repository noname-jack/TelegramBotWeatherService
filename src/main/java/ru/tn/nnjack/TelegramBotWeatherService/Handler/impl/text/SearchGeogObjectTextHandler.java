package ru.tn.nnjack.TelegramBotWeatherService.Handler.impl.text;

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
import ru.tn.nnjack.TelegramBotWeatherService.db.enums.UserState;
import ru.tn.nnjack.TelegramBotWeatherService.dto.GeographicalObjectDTO;
import ru.tn.nnjack.TelegramBotWeatherService.service.DbService;

import java.util.List;

@Component
public class SearchGeogObjectTextHandler implements Handler {
    private final GismeteoApiParserClient gismeteoApiParserClient;
    private final DbService dbService;

    public SearchGeogObjectTextHandler(GismeteoApiParserClient gismeteoApiParserClient, DbService dbService) {
        this.gismeteoApiParserClient = gismeteoApiParserClient;
        this.dbService = dbService;
    }

    @Override
    public HandlerType getHandlingType() {
        return HandlerType.TEXT;
    }

    @Override
    public String getNameHandler(String langCode) {
        return null;
    }

    @Override
    public SendMessage sendingResponse(UpdateInfo updateInfo, User user) {
        String langCode = user.getLanguage();
        String query = updateInfo.getUpdate().getMessage().getText();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(user.getChatId());
        String msgText;
        try {
            List<GeographicalObjectDTO> geographicalObjectListRU = gismeteoApiParserClient.searchGeogObjectByString(query,"ru",5);
            List<GeographicalObjectDTO> geographicalObjectListEN = gismeteoApiParserClient.searchGeogObjectByString(query,"en",5);
            if (geographicalObjectListEN.isEmpty() || geographicalObjectListRU.isEmpty()){
                msgText = MessageToBot.getLocalizedMessage(langCode,MessageToBot.NO_SEARCH_RESULTS_RU,MessageToBot.NO_SEARCH_RESULTS_EN);
            }
            else{
                List<GeographicalObject> geographicalObjectList = dbService.saveGeographicalObjects(geographicalObjectListRU,geographicalObjectListEN);
                msgText = MessageToBot.getLocalizedMessage(langCode,MessageToBot.CHOOSE_DESIRED_LOCATION_RU,MessageToBot.CHOOSE_DESIRED_LOCATION_EN);
                InlineKeyboardMarkup markupInline = CreateKeyboard.sendGeogObjectsInlineKeyboard(langCode,geographicalObjectList);
                sendMessage.setReplyMarkup(markupInline);
            }
            user.setState(UserState.BASIC_STATE);
            dbService.saveUser(user);
        }
        catch (ExternalApiException e) {
            msgText = "Error in the external api: " + e.getMessage();
        }
        sendMessage.setText(msgText);
        return sendMessage;
    }
}
