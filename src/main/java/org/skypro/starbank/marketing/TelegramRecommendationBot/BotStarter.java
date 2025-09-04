package org.skypro.starbank.marketing.TelegramRecommendationBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class BotStarter {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(BotStarter.class, args);
        System.out.println("Бот запущен и слушает обновления...");
    }
}