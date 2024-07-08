package ru.tn.nnjack.TelegramBotWeatherService.Handler.impl.text;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import ru.tn.nnjack.TelegramBotWeatherService.Handler.Handler;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.*;
import ru.tn.nnjack.TelegramBotWeatherService.db.entity.User;

@Component
public class LocationMenuTextHandler implements Handler {

    @Override
    public HandlerType getHandlingType() {
        return HandlerType.TEXT;
    }

    @Override
    public String getNameHandler(String langCode) {
        return HandlerNames.getHandlerName(langCode, HandlerNames.LOCATION_MENU_TEXT_HANDLER_RU, HandlerNames.LOCATION_MENU_TEXT_HANDLER_EN);
    }


    @Override
    public SendMessage sendingResponse(UpdateInfo updateInfo, User user) {
        String langCode = user.getLanguage();
        SendMessage message = new SendMessage();
        message.setChatId(updateInfo.getChatId());
        message.setText(MessageToBot.getLocalizedMessage(langCode,MessageToBot.CHOOSE_ACTION_FROM_MENU_RU,MessageToBot.CHOOSE_ACTION_FROM_MENU_EN));
        ReplyKeyboardMarkup replyKeyboardMarkup = CreateKeyboard.sendLocationMenu(langCode);
        message.setReplyMarkup(replyKeyboardMarkup);
        return message;
    }
}
