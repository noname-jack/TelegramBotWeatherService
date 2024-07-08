package ru.tn.nnjack.TelegramBotWeatherService.Utils;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.ChatMemberUpdated;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

@Getter
@Setter
public class UpdateInfo {
    private Long chatId;
    private String userName;
    private String firstName;
    private String lastName;

    private Long userId;

    private final Update update;

    private final HandlerType handlerType;

    public UpdateInfo(Update update)
    {
        this.update = update;
        handlerType = getTypeHandler();
        initInfo();
    }
    private HandlerType getTypeHandler() {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                String text = message.getText();
                if (text.startsWith("/")) {
                    return HandlerType.COMMAND;
                } else {
                    return HandlerType.TEXT;
                }
            } else if (message.hasLocation()) {
                return HandlerType.LOCATION;
            }
        } else if (update.hasCallbackQuery()) {
            return HandlerType.CALLBACK;
        }
        else if (update.hasMyChatMember()) {
            ChatMemberUpdated chatMemberUpdated = update.getMyChatMember();
            if (chatMemberUpdated.getNewChatMember() != null) {
                return HandlerType.UNKNOWN;
            }
        }
        return  HandlerType.UNKNOWN;
    }

    private void initInfo() {
        if (handlerType.equals(HandlerType.COMMAND) || handlerType.equals(HandlerType.TEXT) || handlerType.equals(HandlerType.LOCATION) ) {
            Message message = update.getMessage();
            User user = message.getFrom();
            chatId = message.getChatId();
            userId = user.getId();
            userName = user.getUserName();
            firstName = user.getFirstName();
            lastName = user.getLastName();
        } else if (handlerType.equals(HandlerType.CALLBACK)) {
            userId = update.getCallbackQuery().getFrom().getId();
            firstName = update.getCallbackQuery().getFrom().getFirstName();
            lastName = update.getCallbackQuery().getFrom().getLastName();
            userName = update.getCallbackQuery().getFrom().getUserName();
            chatId = update.getCallbackQuery().getMessage().getChatId();
        }
    }

    @Override
    public String toString() {
        return "UpdateInfo {\n" +
                "\tchatId=" + chatId + ",\n" +
                "\tuserName=" + userName + ",\n" +
                "\tfirstName=" + firstName + ",\n" +
                "\tlastName=" + lastName + ",\n" +
                "\tuserId=" + userId + "\n" +
                "}";
    }

}
