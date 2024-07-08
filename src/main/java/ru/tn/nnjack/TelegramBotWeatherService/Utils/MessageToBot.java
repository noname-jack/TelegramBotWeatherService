package ru.tn.nnjack.TelegramBotWeatherService.Utils;


import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class MessageToBot {

    public static SendMessage createMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        return sendMessage;
    }

    public static String getLocalizedMessage(String langCode, String msgRu, String msgEn){
        return "ru".equalsIgnoreCase(langCode) ? msgRu : msgEn;
    }

    public static final String UNKNOWN_HANDLER_EN = "Your action was not recognized";

    public static final String UNKNOWN_HANDLER_RU = "Ваше действие не было распознано";

    public static final String HELP_MESSAGE_EN = """
                    This bot provides weather information. You can use the following commands:
                    To check the weather, choose one of the options:

                    1. Search weather by geographical location
                    2. View weather based on your current location
                    3. Set language for responses (Russian or English)

                    To use any of these options, click on the corresponding menu item or enter the corresponding command.

                    For additional information or assistance, refer to the menu or use the available commands.""";

    public static final String HELP_MESSAGE_RU = """
            Этот бот предоставляет информацию о погоде. Вы можете использовать следующие команды:
            Чтобы узнать погоду, выберите один из вариантов:

            1. Поиск погоды по географическому объекту
            2. Просмотр погоды по вашему текущему местоположению
            3. Установка языка для ответов (русский или английский)

            Чтобы использовать любой из этих вариантов, нажмите на соответствующий пункт меню или введите соответствующую команду.

            Для получения дополнительной информации или помощи, обратитесь к меню или воспользуйтесь доступными командами.""";

    public static final String NOT_FOUND_LOCATION_RU = "У вас нет сохранненых локаций";
    public static final String NOT_FOUND_LOCATION_EN = "You don't have any saved locations";
    public static final String INPUT_PROMPT_GEOG_OBJ_RU = "Введите имя географического объекта (город, поселение и т.д.), или для отмены действия введите /start.";
    public static final String INPUT_PROMPT_GEOG_OBJ_EN = "Please enter the name of a geographical object (city, settlement, etc.), or type /start to cancel the action.";
    public static final String ERROR_DELETING_LOCATION_RU = "Произошла ошибка при удалении локации";
    public static final String ERROR_DELETING_LOCATION_EN = "An error occurred while deleting the location";

    public static final String ERROR_GETTING_WEATHER_FORECAST_RU = "Не удалось получить прогноз погоды.";
    public static final String ERROR_GETTING_WEATHER_FORECAST_EN = "Failed to get weather forecast.";

    public static final String CURRENT_WEATHER_RU = "Текущая погода:";
    public static final String CURRENT_WEATHER_EN = "Current weather:";

    public static final String DAILY_FORECAST_RU = "Прогноз на день";
    public static final String DAILY_FORECAST_EN = "Daily forecast";

    public static final String THREE_DAY_FORECAST_RU = "Прогноз на три дня";
    public static final String THREE_DAY_FORECAST_EN = "Three-day forecast";

    public static final String WEEKLY_FORECAST_RU = "Прогноз на неделю";
    public static final String WEEKLY_FORECAST_EN = "Weekly forecast";

    public static final String ERROR_ADDING_LOCATION_RU = "Произошла ошибка, попробуйте добавить локацию заново";
    public static final String ERROR_ADDING_LOCATION_EN = "An error occurred, please try adding the location again";

    public static final String ERROR_ADDING_RU = "Произошла ошибка при добавлении";
    public static final String ERROR_ADDING_EN = "An error occurred while adding";

    public static final String LANGUAGE_SELECTED_RU = "Вы выбрали язык: Русский";
    public static final String LANGUAGE_SELECTED_EN = "You have selected the language: English";

    public static final String CANNOT_VIEW_WEATHER_RU = "По вашей локации невозможно просмотреть погоду";
    public static final String CANNOT_VIEW_WEATHER_EN = "Weather cannot be viewed for your location";

    public static final String CHOOSE_NEARBY_LOCATIONS_RU = "Выберите географические объекты рядом:";
    public static final String CHOOSE_NEARBY_LOCATIONS_EN = "Select nearby geographical objects:";

    public static final String CHOOSE_LOCATION_TO_DELETE_RU = "Выберите локацию для удаления:";
    public static final String CHOOSE_LOCATION_TO_DELETE_EN = "Select a location to delete:";

    public static final String MAIN_MENU_SELECTED_RU = "Вы выбрали главное меню";
    public static final String MAIN_MENU_SELECTED_EN = "You have selected the main menu";

    public static final String CHOOSE_LANGUAGE_RU = "Выберите язык:";
    public static final String CHOOSE_LANGUAGE_EN = "Choose a language:";

    public static final String CHOOSE_ACTION_FROM_MENU_RU = "Выберите действие из меню";
    public static final String CHOOSE_ACTION_FROM_MENU_EN = "Select an action from the menu";

    public static final String NO_SEARCH_RESULTS_RU = "Поиск не дал результатов";
    public static final String NO_SEARCH_RESULTS_EN = "No search results";

    public static final String CHOOSE_DESIRED_LOCATION_RU = "Выберите нужную вам локацию:";
    public static final String CHOOSE_DESIRED_LOCATION_EN = "Select the location you need:";

    public static final String CHOOSE_LOCATION_FOR_WEATHER_RU = "Выберите локацию для просмотра погоды:";
    public static final String CHOOSE_LOCATION_FOR_WEATHER_EN = "Select a location to view the weather:";

    public static final String REMOVED_FROM_SAVED_LOCATIONS_RU = "Удалено из сохранённых локаций:";
    public static final String REMOVED_FROM_SAVED_LOCATIONS_EN = "Removed from saved locations:";

    public static final String LOCATION_SAVED_RU = "Ваша локация сохранена";
    public static final String LOCATION_SAVED_EN = "Your location has been saved";



}
