package ru.stepkos.ybclubbot.botapi.handles;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.stepkos.ybclubbot.botapi.BotState;
import ru.stepkos.ybclubbot.botapi.InputMessageHandler;
import ru.stepkos.ybclubbot.cache.CustomerData;
import ru.stepkos.ybclubbot.cache.DataCache;
import ru.stepkos.ybclubbot.services.CustomerService;

@Component
@RequiredArgsConstructor
public class FillingProfileHandler implements InputMessageHandler {
    private final DataCache dataCache;
    private final CustomerService customerService;

    @Override
    public SendMessage handleMessage(Message message) {
        if (dataCache.getBotState(message.getFrom().getId()).equals(BotState.FILLING_PROFILE)) {
            dataCache.setBotState(message.getFrom().getId(), BotState.ASK_FIRST_QUESTION);
        }
        return processUsersInput(message);
    }

    private SendMessage processUsersInput(Message inputMsg) {
        String usersAnswer = inputMsg.getText();
        long userId = inputMsg.getFrom().getId();
        long chatId = inputMsg.getChatId();

        CustomerData profileData = dataCache.getCustomerData(userId);
        BotState botState = dataCache.getBotState(userId);

        SendMessage replyToUser = null;

        if (botState.equals(BotState.ASK_FIRST_QUESTION)) {
            replyToUser = new SendMessage(String.valueOf(chatId), "Первый вопрос: ");
            dataCache.setBotState(userId, BotState.ASK_SECOND_QUESTION);
        }

        if (botState.equals(BotState.ASK_SECOND_QUESTION)) {
            profileData.setAnswer1(usersAnswer);
            replyToUser = new SendMessage(String.valueOf(chatId), "Второй вопрос: ");
            dataCache.setBotState(userId, BotState.ASK_THIRD_QUESTION);
        }

        if (botState.equals(BotState.ASK_THIRD_QUESTION)) {
            profileData.setAnswer2(usersAnswer);
            replyToUser = new SendMessage(String.valueOf(chatId), "Третий вопрос: ");
            dataCache.setBotState(userId, BotState.FILLING_SUCCESS);
        }

        if (botState.equals(BotState.FILLING_SUCCESS)) {
            profileData.setAnswer3(usersAnswer);
            profileData.setName(inputMsg.getFrom().getFirstName() + " " + inputMsg.getFrom().getLastName());
            profileData.setUsername(inputMsg.getFrom().getUserName());
            customerService.saveCustomer(profileData);
            replyToUser = new SendMessage(String.valueOf(chatId), "Профиль заполнен");
            dataCache.setBotState(userId, BotState.WELCOME);

        }

        dataCache.setCustomerData(userId, profileData);

        return replyToUser;
    }
}
