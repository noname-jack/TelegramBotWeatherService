package ru.tn.nnjack.TelegramBotWeatherService.Utils;

public class HandlerNames {
    public static String getHandlerName(String langCode, String nameRu, String nameEn){
        return "ru".equalsIgnoreCase(langCode) ? nameRu : nameEn;
    }
    // AddGeogObjectTextHandler
    public static final String ADD_GEOG_OBJECT_TEXT_HANDLER_RU = "Добавить локацию";
    public static final String ADD_GEOG_OBJECT_TEXT_HANDLER_EN = "Add location";

    // BackToMainMenuTextHandler
    public static final String BACK_TO_MAIN_MENU_TEXT_HANDLER_RU = "Назад в главное меню";
    public static final String BACK_TO_MAIN_MENU_TEXT_HANDLER_EN = "Back to main menu";

    // ChangeLanguageTextHandler
    public static final String CHANGE_LANGUAGE_TEXT_HANDLER_RU = "Поменять язык";
    public static final String CHANGE_LANGUAGE_TEXT_HANDLER_EN = "Change language";

    // DeleteLocationTextHandler
    public static final String DELETE_LOCATION_TEXT_HANDLER_RU = "Удалить локацию";
    public static final String DELETE_LOCATION_TEXT_HANDLER_EN = "Delete location";

    // LocationMenuTextHandler
    public static final String LOCATION_MENU_TEXT_HANDLER_RU = "Выбрать локацию";
    public static final String LOCATION_MENU_TEXT_HANDLER_EN = "Choose location";


    // LocationHandler
    public static final String LOCATION_HANDLER_RU =  "Определить геолокацию";
    public static final String LOCATION_HANDLER_EN =  "Define geolocation";

    // WeatherTextHandler
    public static final String WEATHER_TEXT_HANDLER_RU = "Погода";
    public static final String WEATHER_TEXT_HANDLER_EN = "Weather";



}
