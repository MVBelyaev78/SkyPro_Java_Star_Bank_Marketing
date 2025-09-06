package org.skypro.starbank.marketing.component.telegrambot;

import org.skypro.starbank.marketing.configuration.telegrambot.BotConfig;
import org.skypro.starbank.marketing.dto.recommendation.RecommendationServiceResult;
import org.skypro.starbank.marketing.dto.recommendation.UserInfo;
import org.skypro.starbank.marketing.repository.FixedRulesRepository;
import org.skypro.starbank.marketing.service.RecommendationService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RecommendationBot extends TelegramLongPollingBot {
    private final BotConfig botConfig;
    private final FixedRulesRepository repo;
    private final RecommendationService service;

    private final static String welcomeMsg = """
            Добро пожаловать в Star Bank Marketing Bot!
            Я помогу вам получить персональные финансовые рекомендации.
            
            Для получения рекомендаций используйте команду:
            /recommend username
            Например: /recommend ivanov
            """;

    public RecommendationBot(BotConfig botConfig, FixedRulesRepository repo, RecommendationService service) {
        this.botConfig = botConfig;
        this.repo = repo;
        this.service = service;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();

            if (messageText.equals("/start")) {
                sendMessage(chatId, welcomeMsg);
            } else if (messageText.contains("/recommend")) {
                getRecommendations(chatId, messageText);
            }
        }
    }

    private void getRecommendations(long chatId, String messageText) {
        String[] parts = messageText.split(" ");
        if (!parts[0].equals("/recommend")) {
            sendMessage(chatId, "Пожалуйста, укажите username после команды.\nПример: /recommend quintin.deckow");
            return;
        }

        String username = parts[1];
        Optional<UserInfo> userInfo = repo.getUserInfo(username);
        if (userInfo.isEmpty()) {
            sendMessage(chatId, "Пользователь не найден");
            return;
        }

        RecommendationServiceResult products = service.getServiceResult(userInfo.get().id());
        StringBuilder message = new StringBuilder();
        message.append("Здравствуйте, ").append(userInfo.get().firstName() + " " + userInfo.get().lastName());

        if (products.recommendations().isEmpty()) {
            message.append("\nНа данный момент рекомендаций нет.");
        } else {
            message.append("\n\n<b>Новые продукты для вас:</b>\n");
            message.append(formatRecommendations(products));
        }

        sendMessage(chatId, message.toString());
    }

    public String formatRecommendations(RecommendationServiceResult products) {
        String formattedRecommendations = products.recommendations().stream()
                .map(recommendation ->
                        "• <i>" + recommendation.name() + "</i>\n" +
                        "  " + recommendation.text() + "\n")
                .collect(Collectors.joining("\n"));


        return formattedRecommendations;
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setParseMode("HTML");
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.err.println("Ошибка при отправке сообщения: " + e.getMessage());
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
