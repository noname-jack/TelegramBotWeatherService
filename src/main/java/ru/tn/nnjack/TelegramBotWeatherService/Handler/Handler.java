package ru.tn.nnjack.TelegramBotWeatherService.Handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.HandlerType;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.UpdateInfo;
import ru.tn.nnjack.TelegramBotWeatherService.db.entity.User;

public interface Handler {
    HandlerType getHandlingType();

    String getNameHandler(String langCode);
    SendMessage sendingResponse(UpdateInfo updateInfo, User user);
}
