package ru.tn.nnjack.TelegramBotWeatherService.Handler.impl.comand;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.tn.nnjack.TelegramBotWeatherService.Handler.Handler;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.HandlerType;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.MessageToBot;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.UpdateInfo;
import ru.tn.nnjack.TelegramBotWeatherService.db.entity.User;

@Component
public class HelpCommandHandler implements Handler {
    @Override
    public HandlerType getHandlingType() {
        return HandlerType.COMMAND;
    }

    @Override
    public String getNameHandler(String langCode) {
        return "/help";
    }

    @Override
    public SendMessage sendingResponse(UpdateInfo updateInfo, User user) {
        String langCode = user.getLanguage();
        SendMessage message = new SendMessage();
        message.setChatId(updateInfo.getChatId());
        String helpMessage = MessageToBot.getLocalizedMessage(langCode,MessageToBot.HELP_MESSAGE_RU, MessageToBot.HELP_MESSAGE_EN);
        message.setText(helpMessage);
        return message;
    }
}
