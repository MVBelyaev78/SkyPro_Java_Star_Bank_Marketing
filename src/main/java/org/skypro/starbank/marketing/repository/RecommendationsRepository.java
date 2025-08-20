package org.skypro.starbank.marketing.repository;

import org.skypro.starbank.marketing.result.SearchResult;
import org.skypro.starbank.marketing.mapper.SearchResultMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

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

    public SearchResult getRecommendationTopSaving(String userId) {
        String sql = """
            SELECT
                EXISTS(
                    SELECT 1 FROM TRANSACTIONS t
                    JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID
                    WHERE t.USER_ID = ? AND p.TYPE = 'DEBIT'
                ) AS has_debit_product,
                COALESCE(SUM(CASE WHEN p.type = 'DEBIT' AND t.type = 'DEPOSIT' THEN t.amount ELSE 0 END), 0) >= 50000
                     AS debit_deposits_ge_50000,
                COALESCE(SUM(CASE WHEN p.type = 'SAVING' AND t.type = 'DEPOSIT' THEN t.amount ELSE 0 END), 0) >= 50000
                     AS saving_deposits_ge_50000,
                COALESCE(SUM(CASE WHEN p.type = 'DEBIT' AND t.type = 'DEPOSIT' THEN t.amount ELSE 0 END), 0) > 
                COALESCE(SUM(CASE WHEN p.type = 'DEBIT' AND t.type = 'WITHDRAW' THEN t.amount ELSE 0 END), 0)
                     AS deposits_gt_withdrawals,
                EXISTS(SELECT 1 FROM public.users u WHERE u.id = ?)
                     AS user_exists
            FROM transactions t
            JOIN products p ON p.id = t.product_id
            WHERE t.user_id = ? """;
        return jdbcTemplate.queryForObject(sql, new SearchResultMapper(), userId, userId, userId);
    }
}
