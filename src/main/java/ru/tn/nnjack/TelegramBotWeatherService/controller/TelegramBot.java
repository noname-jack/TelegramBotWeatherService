package ru.tn.nnjack.TelegramBotWeatherService.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.tn.nnjack.TelegramBotWeatherService.config.TelegramBotConfig;
import ru.tn.nnjack.TelegramBotWeatherService.service.TelegramBotService;


import java.util.ArrayList;
import java.util.List;


@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final TelegramBotConfig telegramBotConfig;
    private final TelegramBotService telegramBotService;

    public TelegramBot(TelegramBotConfig telegramBotConfig, TelegramBotService telegramBotService) {
        super(telegramBotConfig.getToken());
        this.telegramBotConfig = telegramBotConfig;
        this.telegramBotService = telegramBotService;
    }
    @PostConstruct
    public void init() {
        try {
            List<BotCommand> myCommands = new ArrayList<>();
            myCommands.add(new BotCommand("/start", "Начать работу с ботом"));
            myCommands.add(new BotCommand("/help", "Подробная информация"));

            execute(new SetMyCommands(myCommands, new BotCommandScopeDefault(), null));


        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage = telegramBotService.process(update);
        if (sendMessage != null){
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public String getBotUsername() {
        return telegramBotConfig.getBotName();
    }
}
