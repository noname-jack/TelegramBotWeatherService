package ru.tn.nnjack.TelegramBotWeatherService.Handler.impl.comand;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import ru.tn.nnjack.TelegramBotWeatherService.Handler.Handler;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.CreateKeyboard;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.HandlerType;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.MessageToBot;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.UpdateInfo;
import ru.tn.nnjack.TelegramBotWeatherService.db.entity.User;
import ru.tn.nnjack.TelegramBotWeatherService.db.enums.UserState;
import ru.tn.nnjack.TelegramBotWeatherService.service.DbService;

@Component
@Getter
public class StartCommandHandler implements Handler {

    private final HandlerType handlingType = HandlerType.COMMAND;
    private final DbService dbService;

    public StartCommandHandler(DbService dbService) {
        this.dbService = dbService;
    }

    @Override
    public String getNameHandler(String langCode) {
        return "/start";
    }

    @Override
    public SendMessage sendingResponse(UpdateInfo updateInfo, User user) {
        user.setState(UserState.BASIC_STATE);
        dbService.saveUser(user);
        SendMessage message = new SendMessage();
        message.setChatId(updateInfo.getChatId());
        String langCode = user.getLanguage();
        String text = MessageToBot.getLocalizedMessage(langCode,MessageToBot.HELP_MESSAGE_RU,MessageToBot.HELP_MESSAGE_EN);
        message.setText(text);
        ReplyKeyboardMarkup replyKeyboardMarkup = CreateKeyboard.sendMenu(langCode);
        message.setReplyMarkup(replyKeyboardMarkup);
        return message;
    }
}
