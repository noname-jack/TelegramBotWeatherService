package ru.tn.nnjack.TelegramBotWeatherService.Handler.impl.text;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.tn.nnjack.TelegramBotWeatherService.Handler.Handler;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.HandlerNames;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.HandlerType;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.MessageToBot;
import ru.tn.nnjack.TelegramBotWeatherService.Utils.UpdateInfo;
import ru.tn.nnjack.TelegramBotWeatherService.db.entity.GeographicalObject;
import ru.tn.nnjack.TelegramBotWeatherService.db.entity.User;
import ru.tn.nnjack.TelegramBotWeatherService.service.DbService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class WeatherTextHandler implements Handler {
    private  final DbService dbService;

    public WeatherTextHandler(DbService dbService) {
        this.dbService = dbService;
    }

    @Override
    public HandlerType getHandlingType() {
        return HandlerType.TEXT;
    }

    @Override
    public String getNameHandler(String langCode) {
        return HandlerNames.getHandlerName(langCode, HandlerNames.WEATHER_TEXT_HANDLER_RU,HandlerNames.WEATHER_TEXT_HANDLER_EN);
    }

    @Override
    public SendMessage sendingResponse(UpdateInfo updateInfo, User user) {
        Set<GeographicalObject> geographicalObjectList = dbService.findGeographicalObjectsByUserId(user.getId());
        String langCode = user.getLanguage();
        if (geographicalObjectList.isEmpty()) {
            return ("ru".equalsIgnoreCase(langCode) ?
                    MessageToBot.createMessage(updateInfo.getChatId(), MessageToBot.NOT_FOUND_LOCATION_RU) :
                    MessageToBot.createMessage(updateInfo.getChatId(), MessageToBot.NOT_FOUND_LOCATION_EN));
        }
        else{
            SendMessage message = new SendMessage();
            message.setChatId(updateInfo.getChatId());
            message.setText(MessageToBot.getLocalizedMessage(langCode,MessageToBot.CHOOSE_LOCATION_FOR_WEATHER_RU,MessageToBot.CHOOSE_LOCATION_FOR_WEATHER_EN));
            InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
            for (GeographicalObject geographicalObject : geographicalObjectList){
                List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(geographicalObject.toStringShort(langCode));
                button.setCallbackData("geogObject: " + geographicalObject.getId());
                rowInline1.add(button);
                rowsInline.add(rowInline1);
            }
            markupInline.setKeyboard(rowsInline);
            message.setReplyMarkup(markupInline);
            return message;
        }

    }
}
