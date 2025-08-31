package org.skypro.starbank.marketing.TelegramRecommendationBot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class BotConfig {

    @Value("${bot.name}")
    private String recommendation111_bot;

    @Value("${bot.token}")
    private String botToken;


    public String getBotName() {
        return recommendation111_bot;
    }

    public String getBotToken() {
        return botToken;
    }
}
