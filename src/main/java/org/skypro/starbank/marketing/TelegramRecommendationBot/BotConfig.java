package org.skypro.starbank.marketing.TelegramRecommendationBot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class BotConfig {

    @Value("${bot.name}")
    private String RecommendationBot;

    @Value("${bot.token}")
    private String botToken;

    public String getBotName() {
        return RecommendationBot;
    }

    public String getBotToken() {
        return botToken;
    }
}
