package ru.stepkos.ybclubbot.botapi;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.stepkos.ybclubbot.botapi.handles.FillingProfileHandler;
import ru.stepkos.ybclubbot.botapi.handles.LoadProfileHandler;
import ru.stepkos.ybclubbot.botapi.handles.WelcomeHandler;

@Component
@RequiredArgsConstructor
public class BotStateContext {
    private final FillingProfileHandler fillingProfileHandler;
    private final WelcomeHandler welcomeHandler;
    private final LoadProfileHandler loadProfileHandler;

    public SendMessage processBotState(BotState botState, Message message) {
        InputMessageHandler inputMessageHandler = findMessageHandler(botState);
        return inputMessageHandler.handleMessage(message);
    }

    private InputMessageHandler findMessageHandler(BotState botState) {
        switch (botState) {
            case LOAD_PROFILE:
                return loadProfileHandler;
            case FILLING_PROFILE:
            case ASK_FIRST_QUESTION:
            case ASK_SECOND_QUESTION:
            case ASK_THIRD_QUESTION:
            case FILLING_SUCCESS:
                return fillingProfileHandler;
            default:
                return welcomeHandler;
        }
    }
}
