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
        String sql = """
                SELECT
                    EXISTS (SELECT 1 FROM users WHERE id = ?)
                    AND EXISTS (
                        SELECT 1
                        FROM TRANSACTIONS t
                        JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID
                        WHERE t.USER_ID = ? AND p.TYPE = 'DEBIT'
                    )
                    AND NOT EXISTS (
                        SELECT 1
                        FROM TRANSACTIONS t
                        JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID
                        WHERE t.USER_ID = ? AND p.TYPE = 'INVEST'
                    )
                    AND COALESCE((
                        SELECT SUM(t.AMOUNT)
                        FROM TRANSACTIONS t
                        JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID
                        WHERE t.USER_ID = ?
                        AND p.TYPE = 'SAVING'
                        AND t.TYPE = 'DEPOSIT'
                    ), 0) > 1000 AS result
                """;
        return jdbcTemplate.queryForObject(
                sql,
                new SearchResultMapper(),
                userId,
                userId,
                userId,
                userId);
    }
}
