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
public class DeleteLocationCallBackHandler implements Handler {

    private final DbService dbService;

    public DeleteLocationCallBackHandler(DbService dbService) {
        this.dbService = dbService;
    }

    @Override
    public HandlerType getHandlingType() {
        return HandlerType.CALLBACK;
    }

    @Override
    public String getNameHandler(String langCode) {
        return "deleteGeogObject:";
    }

    @Override
    public SendMessage sendingResponse(UpdateInfo updateInfo, User user) {
        String langCode = user.getLanguage();
        int idGeogObject = Integer.parseInt(updateInfo.getUpdate().getCallbackQuery().getData().substring("deleteGeogObject: ".length()));
        Optional<GeographicalObject> geographicalObjectOptional = dbService.findGeographicalObjectsById(idGeogObject);
        if (geographicalObjectOptional.isPresent()){
            GeographicalObject geographicalObject = geographicalObjectOptional.get();
            dbService.deleteGeographicalObjectFromUser(user,geographicalObject);
            return MessageToBot.createMessage(updateInfo.getChatId(), MessageToBot.getLocalizedMessage(langCode,MessageToBot.REMOVED_FROM_SAVED_LOCATIONS_RU, MessageToBot.REMOVED_FROM_SAVED_LOCATIONS_EN));
        }
        else{
            return MessageToBot.createMessage(updateInfo.getChatId(), MessageToBot.getLocalizedMessage(langCode,MessageToBot.ERROR_DELETING_LOCATION_RU,MessageToBot.ERROR_DELETING_LOCATION_EN));
        }

    }
}
