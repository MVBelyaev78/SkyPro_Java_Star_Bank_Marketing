package org.skypro.starbank.marketing.service.rule;

import lombok.AllArgsConstructor;
import org.skypro.starbank.marketing.dto.RecommendationProduct;
import org.skypro.starbank.marketing.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class Invest500Rule implements RecommendationRule {
    private final RecommendationsRepository repo;

    private static final UUID PRODUCT_ID = UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a");
    private static final RecommendationProduct PRODUCT = new RecommendationProduct(
            PRODUCT_ID,
            "Invest 500",
            "Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка! Воспользуйтесь налоговыми льготами и начните инвестировать с умом. Пополните счет до конца года и получите выгоду в виде вычета на взнос в следующем налоговом периоде. Не упустите возможность разнообразить свой портфель, снизить риски и следить за актуальными рыночными тенденциями. Откройте ИИС сегодня и станьте ближе к финансовой независимости!"
    );

    @Override
    public Optional<RecommendationProduct> recommendation(UUID userId) {
        boolean hasDebit = repo.hasProductType(userId, "DEBIT");
        boolean hasInvest = repo.hasProductType(userId, "INVEST");
        BigDecimal savingDeposits = repo.getTotalAmountByType(userId, "SAVING","DEPOSIT");
        boolean savingCondition = savingDeposits.compareTo(new BigDecimal("1000")) > 0;

        if (hasDebit && !hasInvest && savingCondition) {
            return Optional.of(PRODUCT);
        }

        return Optional.empty();
    }
}