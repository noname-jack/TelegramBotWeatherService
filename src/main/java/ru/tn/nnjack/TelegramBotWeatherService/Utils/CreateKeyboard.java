package ru.tn.nnjack.TelegramBotWeatherService.Utils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.tn.nnjack.TelegramBotWeatherService.db.entity.GeographicalObject;

import java.util.ArrayList;
import java.util.List;

public class CreateKeyboard {

    public static ReplyKeyboardMarkup sendMenu(String langCode) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();


        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(getWeatherButtonText(langCode)));
        row1.add(new KeyboardButton(getChooseLocationButtonText(langCode)));


        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton(getChangeLanguageButtonText(langCode)));


        keyboard.add(row1);
        keyboard.add(row2);


        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    public static ReplyKeyboardMarkup sendLocationMenu(String langCode) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(getDeleteLocationButtonText(langCode)));
        KeyboardButton geolocationButton = new KeyboardButton(getDefineGeolocationButtonText(langCode));
        geolocationButton.setRequestLocation(true);
        row1.add(geolocationButton);

        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton(getAddLocationButtonText(langCode)));
        row2.add(new KeyboardButton(getBackToMainMenuButtonText(langCode)));

        keyboard.add(row1);
        keyboard.add(row2);

        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    public static InlineKeyboardMarkup sendForecastWeatherInlineKeyboard(String langCode, int idGeogObject){
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
        InlineKeyboardButton buttonForecastOneDay = new InlineKeyboardButton();
        buttonForecastOneDay.setText(MessageToBot.getLocalizedMessage(langCode,MessageToBot.DAILY_FORECAST_RU,MessageToBot.DAILY_FORECAST_EN));
        buttonForecastOneDay.setCallbackData("{\"forecast\":\"1\", \"id\":\"" + idGeogObject +"\"}");
        rowInline1.add(buttonForecastOneDay);
        rowsInline.add(rowInline1);

        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
        InlineKeyboardButton buttonForecastThreeDay = new InlineKeyboardButton();
        buttonForecastThreeDay.setText(MessageToBot.getLocalizedMessage(langCode,MessageToBot.THREE_DAY_FORECAST_RU,MessageToBot.THREE_DAY_FORECAST_EN));
        buttonForecastThreeDay.setCallbackData("{\"forecast\":\"3\", \"id\":\"" + idGeogObject +"\"}");
        rowInline2.add(buttonForecastThreeDay);
        rowsInline.add(rowInline2);

        List<InlineKeyboardButton> rowInline3 = new ArrayList<>();
        InlineKeyboardButton buttonForecastWeek = new InlineKeyboardButton();
        buttonForecastWeek.setText(MessageToBot.getLocalizedMessage(langCode,MessageToBot.WEEKLY_FORECAST_RU,MessageToBot.WEEKLY_FORECAST_EN));
        buttonForecastWeek.setCallbackData("{\"forecast\":\"7\", \"id\":\"" + idGeogObject +"\"}");
        rowInline3.add(buttonForecastWeek);
        rowsInline.add(rowInline3);

        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    public  static  InlineKeyboardMarkup sendGeogObjectsInlineKeyboard(String langCode, List<GeographicalObject> geographicalObjectList){
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        for (GeographicalObject geographicalObject : geographicalObjectList){
            List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(geographicalObject.toStringLong(langCode));
            button.setCallbackData("saveGeogObject: " + geographicalObject.getId());
            rowInline1.add(button);
            rowsInline.add(rowInline1);
        }
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    public  static  InlineKeyboardMarkup sendLangChooseLanguageInlineKeyboard(){
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
        InlineKeyboardButton buttonRu = new InlineKeyboardButton();
        buttonRu.setText("ru");
        buttonRu.setCallbackData("lang:ru");
        rowInline1.add(buttonRu);


        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
        InlineKeyboardButton buttonEn = new InlineKeyboardButton();
        buttonEn.setText("en");
        buttonEn.setCallbackData("lang:en");
        rowInline2.add(buttonEn);

        rowsInline.add(rowInline1);
        rowsInline.add(rowInline2);

        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    private static String getWeatherButtonText(String langCode) {
        return HandlerNames.getHandlerName(langCode, HandlerNames.WEATHER_TEXT_HANDLER_RU,HandlerNames.WEATHER_TEXT_HANDLER_EN);
    }

    private static String getChooseLocationButtonText(String langCode) {
        return HandlerNames.getHandlerName(langCode, HandlerNames.LOCATION_MENU_TEXT_HANDLER_RU, HandlerNames.LOCATION_MENU_TEXT_HANDLER_EN);
    }

    private static String getChangeLanguageButtonText(String langCode) {
        return HandlerNames.getHandlerName(langCode,HandlerNames.CHANGE_LANGUAGE_TEXT_HANDLER_RU, HandlerNames.CHANGE_LANGUAGE_TEXT_HANDLER_EN);
    }

    private static String getDeleteLocationButtonText(String langCode) {
        return HandlerNames.getHandlerName(langCode,HandlerNames.DELETE_LOCATION_TEXT_HANDLER_RU,HandlerNames.DELETE_LOCATION_TEXT_HANDLER_EN);
    }

    private static String getDefineGeolocationButtonText(String langCode) {
        return HandlerNames.getHandlerName(langCode,HandlerNames.LOCATION_HANDLER_RU,HandlerNames.LOCATION_HANDLER_EN);
    }

    private static String getAddLocationButtonText(String langCode) {
        return HandlerNames.getHandlerName(langCode,HandlerNames.ADD_GEOG_OBJECT_TEXT_HANDLER_RU,HandlerNames.ADD_GEOG_OBJECT_TEXT_HANDLER_EN);
    }

    private static String getBackToMainMenuButtonText(String langCode) {
        return HandlerNames.getHandlerName(langCode, HandlerNames.BACK_TO_MAIN_MENU_TEXT_HANDLER_RU,HandlerNames.BACK_TO_MAIN_MENU_TEXT_HANDLER_EN);
    }
}


