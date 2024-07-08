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
public class DeleteLocationTextHandler implements Handler {
    private  final DbService dbService;

    public DeleteLocationTextHandler(DbService dbService) {
        this.dbService = dbService;
    }

    @Override
    public HandlerType getHandlingType() {
        return HandlerType.TEXT;
    }

    @Override
    public String getNameHandler(String langCode) {
        return HandlerNames.getHandlerName(langCode, HandlerNames.DELETE_LOCATION_TEXT_HANDLER_RU,HandlerNames.DELETE_LOCATION_TEXT_HANDLER_EN);
    }

    @Override
    public SendMessage sendingResponse(UpdateInfo updateInfo, User user) {
        String langCode = user.getLanguage();
        Set<GeographicalObject> geographicalObjectSet = dbService.findGeographicalObjectsByUserId(user.getId());
        if (geographicalObjectSet.isEmpty()) {
            String msgText = MessageToBot.getLocalizedMessage(langCode,MessageToBot.NOT_FOUND_LOCATION_RU,MessageToBot.NOT_FOUND_LOCATION_EN);
            return MessageToBot.createMessage(updateInfo.getChatId(), msgText);

        }
        else {
                SendMessage message = new SendMessage();
                message.setChatId(updateInfo.getChatId());
                message.setText(MessageToBot.getLocalizedMessage(langCode,MessageToBot.CHOOSE_LOCATION_TO_DELETE_RU,MessageToBot.CHOOSE_LOCATION_TO_DELETE_EN));
                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                for (GeographicalObject geographicalObject : geographicalObjectSet){
                    List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
                    InlineKeyboardButton button = new InlineKeyboardButton();
                    button.setText(geographicalObject.toStringShort(langCode));
                    button.setCallbackData("deleteGeogObject: " + geographicalObject.getId());
                    rowInline1.add(button);
                    rowsInline.add(rowInline1);
                }
                markupInline.setKeyboard(rowsInline);
                message.setReplyMarkup(markupInline);
                return message;
            }
        }

}
