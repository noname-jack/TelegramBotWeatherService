package ru.tn.nnjack.TelegramBotWeatherService.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import ru.tn.nnjack.TelegramBotWeatherService.Handler.Handler;
import ru.tn.nnjack.TelegramBotWeatherService.Handler.impl.text.SearchGeogObjectTextHandler;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.HandlerNames;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.HandlerType;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.MessageToBot;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.UpdateInfo;
import ru.tn.nnjack.TelegramBotWeatherService.db.entity.User;
import ru.tn.nnjack.TelegramBotWeatherService.db.enums.UserState;

import java.util.List;


@Service
public class TelegramBotService {
    private final List<Handler> handlers;
    private final DbService dbService;

    @Autowired
    public TelegramBotService(List<Handler> handler, DbService dbService) {
        this.handlers = handler;
        this.dbService = dbService;
    }

    public SendMessage process(Update update) {
        if (update == null) {
            return null;
        }
        System.out.println(update);
        UpdateInfo updateInfo = new UpdateInfo(update);
        System.out.println(updateInfo);
        HandlerType updateHandlerType = updateInfo.getHandlerType();
        System.out.println(updateHandlerType);

        if (updateHandlerType != HandlerType.UNKNOWN) {
            return handleUpdate(updateInfo, updateHandlerType);
        }

        return null;
    }

    private SendMessage handleUpdate(UpdateInfo updateInfo, HandlerType updateHandlerType) {
        User user = dbService.findOrCreateAndUpdateUser(updateInfo);
        String langCode = user.getLanguage();
        Handler handler = findHandler(updateInfo.getUpdate(), user, updateHandlerType);

        if (handler != null) {
            return handleValidHandler(updateInfo, user, langCode, handler);
        }

        else {
            return MessageToBot.createMessage(updateInfo.getChatId(),
                    langCode.equals("ru") ? MessageToBot.UNKNOWN_HANDLER_RU : MessageToBot.UNKNOWN_HANDLER_EN);
        }
    }

    private SendMessage handleValidHandler(UpdateInfo updateInfo, User user, String langCode, Handler handler) {
        UserState userState = user.getState();
        if (handler.getHandlingType() == HandlerType.COMMAND) {
            return handler.sendingResponse(updateInfo, user);
        }
        if (userState == UserState.WAITING_FOR_GEOG_OBJECT_STATE && !(handler instanceof SearchGeogObjectTextHandler)) {
            return MessageToBot.createMessage(updateInfo.getChatId(),
                    MessageToBot.getLocalizedMessage(langCode, MessageToBot.INPUT_PROMPT_GEOG_OBJ_RU, MessageToBot.INPUT_PROMPT_GEOG_OBJ_EN));
        } else {
            return handler.sendingResponse(updateInfo, user);
        }
    }

    private Handler findHandler(Update update, User user, HandlerType updateHandlerType) {
        String langCode = user.getLanguage();
        String text = extractTextFromUpdate(update, updateHandlerType,langCode);

        if (text != null) {

            Handler findHandler =  handlers.stream()
                    .filter(handler -> handler.getHandlingType() == updateHandlerType)
                    .filter(handler -> isMatchingHandler(handler, text, updateHandlerType, langCode))
                    .findFirst()
                    .orElse(null);
            if (findHandler == null && user.getState() == UserState.WAITING_FOR_GEOG_OBJECT_STATE){
                findHandler = handlers.stream()
                        .filter(handler -> handler instanceof SearchGeogObjectTextHandler)
                        .findFirst()
                        .orElse(null);
            }
            return findHandler;
        }

        return null;
    }

    private String extractTextFromUpdate(Update update, HandlerType updateHandlerType, String langCode) {
        if (updateHandlerType == HandlerType.COMMAND || updateHandlerType == HandlerType.TEXT) {
            if (update.hasMessage()) {
                Message message = update.getMessage();
                return message.getText();
            }
        }
        else if (updateHandlerType == HandlerType.CALLBACK) {
            if (update.hasCallbackQuery()) {
                return update.getCallbackQuery().getData();
            }
        }
        else if(updateHandlerType == HandlerType.LOCATION){
            return HandlerNames.getHandlerName(langCode,HandlerNames.LOCATION_HANDLER_RU,HandlerNames.LOCATION_HANDLER_EN);
        }

        return null;
    }

    private boolean isMatchingHandler(Handler handler, String text, HandlerType updateHandlerType, String langCode) {
        if (updateHandlerType == HandlerType.CALLBACK) {
            return text.startsWith(handler.getNameHandler(langCode));
        } else {
            return text.equals(handler.getNameHandler(langCode));
        }
    }
}
