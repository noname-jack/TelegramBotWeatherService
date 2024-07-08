package ru.tn.nnjack.TelegramBotWeatherService.Handler.impl.callback;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.tn.nnjack.TelegramBotWeatherService.Handler.Handler;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.HandlerType;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.MessageToBot;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.UpdateInfo;
import ru.tn.nnjack.TelegramBotWeatherService.db.entity.GeographicalObject;
import ru.tn.nnjack.TelegramBotWeatherService.db.entity.User;
import ru.tn.nnjack.TelegramBotWeatherService.service.DbService;

import java.util.Optional;

@Component
public class SaveGeogObjectCallBackHandler implements Handler {
    private final DbService dbService;

    public SaveGeogObjectCallBackHandler(DbService dbService) {
        this.dbService = dbService;
    }

    @Override
    public HandlerType getHandlingType() {
        return HandlerType.CALLBACK;
    }

    @Override
    public String getNameHandler(String langCode) {
        return "saveGeogObject: ";
    }

    @Override
    public SendMessage sendingResponse(UpdateInfo updateInfo, User user) {
        String langCode = user.getLanguage();
        int idGeogObject = Integer.parseInt(updateInfo.getUpdate().getCallbackQuery().getData().substring("saveGeogObject: ".length()));
        Optional<GeographicalObject> geographicalObjectOptional = dbService.findGeographicalObjectsById(idGeogObject);
        if (geographicalObjectOptional.isPresent()){
            GeographicalObject geographicalObject = geographicalObjectOptional.get();
            dbService.addGeographicalObjectToUser(user,geographicalObject);
            String messageText = MessageToBot.getLocalizedMessage(langCode,MessageToBot.LOCATION_SAVED_RU, MessageToBot.LOCATION_SAVED_EN);
            return MessageToBot.createMessage(updateInfo.getChatId(), messageText);
        }
        else{
            String messageText = MessageToBot.getLocalizedMessage(langCode,MessageToBot.ERROR_ADDING_RU,MessageToBot.ERROR_ADDING_EN);
            return MessageToBot.createMessage(updateInfo.getChatId(), messageText);
        }

    }
}
