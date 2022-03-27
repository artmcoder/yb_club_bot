package ru.stepkos.ybclubbot.cache.impl;

import org.springframework.stereotype.Component;
import ru.stepkos.ybclubbot.botapi.BotState;
import ru.stepkos.ybclubbot.cache.CustomerData;
import ru.stepkos.ybclubbot.cache.DataCache;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserDataCache implements DataCache {
    private Map<Long, BotState> userBotState = new HashMap<>();
    private Map<Long, CustomerData> userCustomerData = new HashMap<>();

    @Override
    public void setBotState(long userId, BotState botState) {
        userBotState.put(userId, botState);
    }

    @Override
    public BotState getBotState(long userId) {
        BotState botState = userBotState.get(userId);
        return botState == null ? BotState.WELCOME : botState;
    }

    @Override
    public void setCustomerData(long userId, CustomerData customerData) {
        userCustomerData.put(userId, customerData);
    }

    @Override
    public CustomerData getCustomerData(long userId) {
        CustomerData customerData = userCustomerData.get(userId);
        return customerData == null ? new CustomerData() : customerData;
    }
}
