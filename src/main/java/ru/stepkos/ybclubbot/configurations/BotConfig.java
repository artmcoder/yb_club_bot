package ru.stepkos.ybclubbot.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.stepkos.ybclubbot.YbClubBot;
import ru.stepkos.ybclubbot.botapi.BotApi;

@Configuration
public class BotConfig {
    @Value("${telegrambot.webHookPath}")
    private String webHookPath;
    @Value("${telegrambot.botUserName}")
    private String botUserName;
    @Value("${telegrambot.botToken}")
    private String botToken;

    @Bean
    public YbClubBot ybClubBot(BotApi botApi) {
        YbClubBot myWizardTelegramBot = new YbClubBot(botApi);
        myWizardTelegramBot.setBotUserName(botUserName);
        myWizardTelegramBot.setBotToken(botToken);
        myWizardTelegramBot.setWebHookPath(webHookPath);
        return myWizardTelegramBot;
    }
}