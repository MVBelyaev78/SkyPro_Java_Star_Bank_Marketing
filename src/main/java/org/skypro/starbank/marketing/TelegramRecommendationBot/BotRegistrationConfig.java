package org.skypro.starbank.marketing.TelegramRecommendationBot;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Configuration
public class BotRegistrationConfig {
    @Bean
    public ApplicationRunner botRegistrationRunner(TelegramBotsApi botsApi, RecommendationBot bot) {
        return args -> {
            try {
                botsApi.registerBot(bot);
                System.out.println("Telegram бот успешно зарегистрирован и запущен!");
            } catch (TelegramApiException e) {
                System.err.println("Ошибка при регистрации бота: " + e.getMessage());
                throw new RuntimeException("Не удалось зарегистрировать Telegram бота", e);
            }
        };
    }
}
