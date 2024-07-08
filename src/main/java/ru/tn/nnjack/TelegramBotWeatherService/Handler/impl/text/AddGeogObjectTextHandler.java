package ru.tn.nnjack.TelegramBotWeatherService.Handler.impl.text;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.tn.nnjack.TelegramBotWeatherService.Handler.Handler;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.HandlerNames;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.HandlerType;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.MessageToBot;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.UpdateInfo;
import ru.tn.nnjack.TelegramBotWeatherService.db.entity.User;
import ru.tn.nnjack.TelegramBotWeatherService.db.enums.UserState;
import ru.tn.nnjack.TelegramBotWeatherService.service.DbService;

@Component
public class AddGeogObjectTextHandler implements Handler {
    private final DbService dbService;

    public AddGeogObjectTextHandler(DbService dbService) {
        this.dbService = dbService;
    }

    @Override
    public HandlerType getHandlingType() {
        return HandlerType.TEXT;
    }

    @Override
    public String getNameHandler(String langCode) {
        return HandlerNames.getHandlerName(langCode, HandlerNames.ADD_GEOG_OBJECT_TEXT_HANDLER_RU, HandlerNames.ADD_GEOG_OBJECT_TEXT_HANDLER_EN);
    }

    @Override
    public SendMessage sendingResponse(UpdateInfo updateInfo, User user) {
        String langCode = user.getLanguage();
        user.setState(UserState.WAITING_FOR_GEOG_OBJECT_STATE);
        dbService.saveUser(user);
        String msgText = MessageToBot.getLocalizedMessage(langCode, MessageToBot.INPUT_PROMPT_GEOG_OBJ_RU, MessageToBot.INPUT_PROMPT_GEOG_OBJ_EN);
        return MessageToBot.createMessage(updateInfo.getChatId(),msgText);
    }
}
