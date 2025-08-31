package org.skypro.starbank.marketing.TelegramRecommendationBot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

public class RecommendationBot extends TelegramLongPollingBot {

    private final Map<String, User> userData = new HashMap<>();

    public RecommendationBot() {
        userData.put("ivanov", new User("Иван Иванов", new String[]{"Продукт A", "Продукт B", "Продукт C"}));
        userData.put("petrov", new User("Петр Петров", new String[]{"Продукт D", "Продукт E"}));
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String command = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();

            if (command.equals("/start")) {
                sendResponse(chatId, "Здравствуйте! Я бот, который поможет вам с рекомендациями.\n"
                        + "Используйте команду /recommend <username>, чтобы получить рекомендации.");
            } else if (command.startsWith("/recommend ")) {
                String username = command.split(" ")[1].toLowerCase();
                handleRecommendCommand(chatId, username);
            }
        }
    }

    private void handleRecommendCommand(String chatId, String username) {
        User user = userData.get(username);

        if (user != null) {
            StringBuilder recommendations = new StringBuilder("Новые продукты для вас:\n");
            for (String product : user.getRecommendations()) {
                recommendations.append("- ").append(product).append("\n");
            }
            sendResponse(chatId, "Здравствуйте " + user.getName() + "\n\n" + recommendations);
        } else {
            sendResponse(chatId, "Пользователь не найден.");
        }
    }

    private void sendResponse(String chatId, String text) {
        try {
            execute(new SendMessage(chatId, text));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "recommendation111_bot";
    }

    @Override
    public String getBotToken() {
        return "8285932023:AAF1Fs6csiDDuL-Mh092AYmcLz43_TZVOF8";
    }

    public static void main(String[] args) {
        // Здесь можно инициализировать бота и запустить его
    }

    private static class User {
        private final String name;
        private final String[] recommendations;

        public User(String name, String[] recommendations) {
            this.name = name;
            this.recommendations = recommendations;
        }

        public String getName() {
            return name;
        }

        public String[] getRecommendations() {
            return recommendations;
        }
    }
}