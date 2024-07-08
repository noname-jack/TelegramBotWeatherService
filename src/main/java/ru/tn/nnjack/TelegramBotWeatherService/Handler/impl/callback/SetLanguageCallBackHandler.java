package ru.tn.nnjack.TelegramBotWeatherService.Handler.impl.callback;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import ru.tn.nnjack.TelegramBotWeatherService.Handler.Handler;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.CreateKeyboard;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.HandlerType;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.MessageToBot;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.UpdateInfo;
import ru.tn.nnjack.TelegramBotWeatherService.db.entity.User;
import ru.tn.nnjack.TelegramBotWeatherService.service.DbService;

@Component
public class SetLanguageCallBackHandler implements Handler {

    private  final DbService dbService;

    public SetLanguageCallBackHandler(DbService dbService) {
        this.dbService = dbService;
    }

    @Override
    public HandlerType getHandlingType() {
        return HandlerType.CALLBACK;
    }

    @Override
    public String getNameHandler(String langCode) {
        return "lang";
    }

    @Override
    public SendMessage sendingResponse(UpdateInfo updateInfo, User user) {
        SendMessage message = new SendMessage();
        message.setChatId(updateInfo.getChatId());
        String langCode = updateInfo.getUpdate().getCallbackQuery().getData().substring("lang:".length());
        user.setLanguage(langCode);
        dbService.saveUser(user);
        message.setText(MessageToBot.getLocalizedMessage(langCode, MessageToBot.LANGUAGE_SELECTED_RU,MessageToBot.LANGUAGE_SELECTED_EN));
        ReplyKeyboardMarkup replyKeyboardMarkup = CreateKeyboard.sendMenu(langCode);
        message.setReplyMarkup(replyKeyboardMarkup);
        return message;
    }
}
