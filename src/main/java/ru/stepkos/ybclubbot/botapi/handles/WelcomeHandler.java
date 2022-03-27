package ru.stepkos.ybclubbot.botapi.handles;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.stepkos.ybclubbot.botapi.InputMessageHandler;
import ru.stepkos.ybclubbot.services.MainMenuService;
import ru.stepkos.ybclubbot.utils.Emojis;

@Component
@RequiredArgsConstructor
public class WelcomeHandler implements InputMessageHandler {
    private final MainMenuService mainMenuService;

    @Override
    public SendMessage handleMessage(Message message) {
        String replySendMessageText = String.format("Привет %s\nДобро пожаловать в YB CLUB. Воспользуйся главным меню чтобы заполнить анкету...", Emojis.WAVE);
        return mainMenuService.getMainMenuMessage(message.getChatId(), replySendMessageText, message.getFrom());
    }
}
