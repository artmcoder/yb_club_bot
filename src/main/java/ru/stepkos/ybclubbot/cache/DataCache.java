package ru.stepkos.ybclubbot.cache;

import ru.stepkos.ybclubbot.botapi.BotState;

public interface DataCache {
    void setBotState(long userId, BotState botState);

    BotState getBotState(long userId);

    void setCustomerData(long userId, CustomerData customerData);

    CustomerData getCustomerData(long userId);
}
