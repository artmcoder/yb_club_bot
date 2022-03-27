package ru.stepkos.ybclubbot.botapi.handles;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.stepkos.ybclubbot.botapi.InputMessageHandler;

import javax.persistence.Column;

@Component
public class LoadProfileHandler implements InputMessageHandler {
    @Override
    public SendMessage handleMessage(Message message) {
        return null;
    }
}
