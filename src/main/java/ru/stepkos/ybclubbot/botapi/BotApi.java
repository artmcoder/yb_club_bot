package ru.stepkos.ybclubbot.botapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.stepkos.ybclubbot.cache.DataCache;
import ru.stepkos.ybclubbot.services.MainMenuService;
import ru.stepkos.ybclubbot.utils.Emojis;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class BotApi {
    private final MainMenuService mainMenuService;
    private final DataCache dataCache;
    private final BotStateContext botStateContext;

    public SendMessage handleUpdate(Update update) {
        SendMessage replyMessage = null;


        Message message = update.getMessage();
        if (message != null && message.hasText() && message.getText() != null) {
            log.info("New message from User: {}, userId: {}, chatId: {},  with text: {}",
                    message.getFrom().getUserName(), message.getFrom().getId(), message.getChatId(), message.getText());
            replyMessage = handleInputMessage(message);
        }
        return replyMessage;
    }

    private SendMessage handleInputMessage(Message message) {
        SendMessage replyMessage;
        String messageText = message.getText();
        long userId = message.getFrom().getId();
        BotState botState;

        switch (messageText) {
            case "/start":
                botState = BotState.WELCOME;
                break;
            case "Заполнить анкету":
                botState = BotState.FILLING_PROFILE;
                break;
            case "Выгрузить анкету":
                botState = BotState.LOAD_PROFILE;
                break;
            default:
                botState = dataCache.getBotState(userId);
                break;
        }

        dataCache.setBotState(userId, botState);

        replyMessage = botStateContext.processBotState(botState, message);

        return replyMessage;
    }
}
