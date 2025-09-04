package org.skypro.starbank.marketing.TelegramRecommendationBot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;


@Component
public class RecommendationBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private static final Logger logger = Logger.getLogger(RecommendationBot.class.getName());

    @Autowired
    public RecommendationBot(BotConfig botConfig) {
        this.botConfig = botConfig;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = update.getMessage().getChatId().toString();
            String messageText = update.getMessage().getText();

            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText("Вы написали: " + messageText);

            try {
                execute(message);
                logger.info("Сообщение отправлено в чат: " + chatId);
            } catch (TelegramApiException e) {
                logger.severe("Ошибка при отправке сообщения: " + e.getMessage());
            }
        } else {
            logger.warning("Получено не текстовое сообщение или обновление: " + update);
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getBotToken();
    }
}
