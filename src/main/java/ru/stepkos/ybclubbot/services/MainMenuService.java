package ru.stepkos.ybclubbot.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.stepkos.ybclubbot.utils.Emojis;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainMenuService {
    private final CustomerService customerService;

    public SendMessage getMainMenuMessage(long chatId, String text, User user) {
        final ReplyKeyboardMarkup replyKeyboardMarkup = getMainMenuKeyboard(user);
        final SendMessage mainMenuMessage =
                createMessageWithKeyboard(chatId, text, replyKeyboardMarkup);

        return mainMenuMessage;
    }

    private ReplyKeyboardMarkup getMainMenuKeyboard(User user) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboard = new ArrayList<>();
        if (customerService.profileIsFilling(user.getUserName())) {
            KeyboardRow row1 = new KeyboardRow();
            KeyboardRow row2 = new KeyboardRow();
            row1.add(new KeyboardButton("Заполнить анкету заново " + Emojis.MEMO));
            row2.add(new KeyboardButton("Выгрузить анкету  " + Emojis.WHITE_CHECK_MARK));
            keyboard.add(row1);
            keyboard.add(row2);
        } else {
            KeyboardRow row1 = new KeyboardRow();
            row1.add(new KeyboardButton("Заполнить анкету " + Emojis.MEMO));
            keyboard.add(row1);
        }
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    private SendMessage createMessageWithKeyboard(long chatId,
                                                  String textMessage,
                                                  ReplyKeyboardMarkup replyKeyboardMarkup) {
        final SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textMessage);
        if (replyKeyboardMarkup != null) {
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        }
        return sendMessage;
    }
}
