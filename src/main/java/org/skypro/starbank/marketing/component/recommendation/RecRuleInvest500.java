package org.skypro.starbank.marketing.component.recommendation;

import org.skypro.starbank.marketing.dto.recommendation.Recommendation;
import org.skypro.starbank.marketing.repository.RecommendationsRepository;
import org.skypro.starbank.marketing.dto.recommendation.SearchResult;
import org.springframework.stereotype.Component;

@Component
public class RecRuleInvest500 extends RecommendationRuleImpl {
    public RecRuleInvest500(RecommendationsRepository recommendationsRepository) {
        super(recommendationsRepository);
    }

    @Override
    protected SearchResult getSearchResult(String userId) {
        return getRecommendationsRepository().getSearchResultInvest500(userId);
    }

    @Override
    protected Recommendation getNewRecommendation() {
        return new Recommendation(
                "Invest 500",
                "147f6a0f-3b91-413b-ab99-87f081d60d5a",
                "Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка! Воспользуйтесь налоговыми льготами и начните инвестировать с умом. Пополните счет до конца года и получите выгоду в виде вычета на взнос в следующем налоговом периоде. Не упустите возможность разнообразить свой портфель, снизить риски и следить за актуальными рыночными тенденциями. Откройте ИИС сегодня и станьте ближе к финансовой независимости!");
    }
}
