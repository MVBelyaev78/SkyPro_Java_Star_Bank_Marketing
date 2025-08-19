package org.skypro.starbank.marketing.repository;

import org.skypro.starbank.marketing.result.SearchResult;
import org.skypro.starbank.marketing.mapper.SearchResultMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RecommendationsRepository {
    private final JdbcTemplate jdbcTemplate;

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public SearchResult getSearchResult(String userId) {
        return jdbcTemplate.queryForObject(
                "SELECT EXISTS (SELECT NULL FROM public.users u WHERE u.id = ?) AND NOT EXISTS (SELECT NULL FROM public.transactions t JOIN public.products p ON p.id = t.product_id WHERE p.\"TYPE\" = 'INVEST' AND t.user_id = ?) AS \"result\"",
                new SearchResultMapper(),
                userId,
                userId);
    }
}
