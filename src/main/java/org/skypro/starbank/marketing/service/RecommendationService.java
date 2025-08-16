package org.skypro.starbank.marketing.service;

import org.skypro.starbank.marketing.dto.Recommendation;
import org.skypro.starbank.marketing.dto.ServiceResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Service
public class RecommendationService {

    public ServiceResult getServiceResult(String userId) {
        // Пример данных о пользователе
        boolean usesCreditProduct = false; // Проверка на использование кредитных продуктов
        double totalDebitDeposits = 150000; // Сумма пополнений по дебетовым продуктам
        double totalDebitWithdrawals = 90000; // Сумма трат по дебетовым продуктам

        Collection<Recommendation> recommendations = new ArrayList<>();

        // Проверка условий для продукта "Простой кредит"
        if (!usesCreditProduct && totalDebitDeposits > totalDebitWithdrawals && totalDebitWithdrawals > 100000) {
            recommendations.add(new Recommendation(
                    "Простой кредит",
                    UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"),
                    "Откройте мир выгодных кредитов с нами! Ищете способ быстро и без лишних хлопот получить нужную сумму? " +
                            "Тогда наш выгодный кредит — именно то, что вам нужно! Мы предлагаем низкие процентные ставки, " +
                            "гибкие условия и индивидуальный подход к каждому клиенту.\n" +
                            "Почему выбирают нас:\n" +
                            "Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает всего несколько часов.\n" +
                            "Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном приложении.\n" +
                            "Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку недвижимости, автомобиля, образования, лечения и многое другое.\n" +
                            "Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!"
            ));
        }


        recommendations.add(new Recommendation("Yesterday",
                UUID.fromString("b9f1a8b5-35c5-4372-a83f-6f61e53c5c1a"),
                "I said something wrong, now I long for yesterday"));
        recommendations.add(new Recommendation("Thank you for the music",
                UUID.fromString("65545241-06e3-4a1d-b7a5-8b0a7f9aa4e8"),
                "She says I began to sing long before I could talk"));


        return new ServiceResult(UUID.fromString(userId), recommendations);
    }
}
