package org.skypro.starbank.marketing.repository;

import org.skypro.starbank.marketing.dto.recommendation.SearchResult;
import org.skypro.starbank.marketing.mapper.SearchResultMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FixedRulesRepository {
    private final JdbcTemplate jdbcTemplate;

    public FixedRulesRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public SearchResult getSearchResultInvest500(String userId) {
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
                    ), 0) > ? AS result
                """;
        return jdbcTemplate.queryForObject(
                sql,
                new SearchResultMapper(),
                userId,
                userId,
                userId,
                userId,
                1000);
    }

    public SearchResult getSearchResultTopSaving(String userId) {
        String sql = """
                SELECT (
                    EXISTS (SELECT 1 FROM public.users u WHERE u.id = ?)
                    AND EXISTS (SELECT 1 FROM TRANSACTIONS t
                                JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID
                                WHERE t.USER_ID = ? AND p.TYPE = 'DEBIT')
                    AND (
                        (SELECT COALESCE(SUM(t.amount), 0) FROM TRANSACTIONS t
                        JOIN PRODUCTS p ON p.id = t.product_id
                        WHERE p.type = 'DEBIT' AND t.type = 'DEPOSIT' AND t.user_id = ?) >= ?
                        OR
                        (SELECT COALESCE(SUM(t.amount), 0) FROM TRANSACTIONS t
                        JOIN PRODUCTS p ON p.id = t.product_id
                        WHERE p.type = 'SAVING' AND t.type = 'DEPOSIT' AND t.user_id = ?) >= ?
                    )
                    AND (
                        SELECT COALESCE(SUM(CASE WHEN t.type = 'DEPOSIT' THEN t.amount ELSE 0 END), 0) -
                               COALESCE(SUM(CASE WHEN t.type = 'WITHDRAW' THEN t.amount ELSE 0 END), 0)
                        FROM transactions t
                        JOIN products p ON p.id = t.product_id
                        WHERE p.type = 'DEBIT' AND t.user_id = ?
                    )> 0
                ) AS result
                """;
        return jdbcTemplate.queryForObject(sql, new SearchResultMapper(),
                userId, userId, userId, 50000, userId, 50000, userId);
    }

    public SearchResult getSearchResultSimpleLoan(String userId) {
        String sql = """
                SELECT EXISTS (SELECT NULL
                                 FROM public.users u
                                WHERE u.id = ?)
                   AND NOT EXISTS (SELECT NULL
                                     FROM public.transactions t
                                     JOIN public.products p ON p.id = t.product_id
                                    WHERE p."TYPE" = 'CREDIT'
                                      AND t.user_id = ?)
                   AND 0 < (SELECT COALESCE(SUM(CASE t."TYPE" WHEN 'DEPOSIT' THEN t.amount ELSE 0 END), 0) -
                                   COALESCE(SUM(CASE t."TYPE" WHEN 'WITHDRAW' THEN t.amount ELSE 0 END), 0)
                              FROM public.transactions t
                              JOIN public.products p ON p.id = t.product_id
                             WHERE p."TYPE" = 'DEBIT'
                               AND t.user_id = ?)
                   AND ? < (SELECT COALESCE(SUM(t.amount), 0)
                                   FROM public.transactions t
                                   JOIN public.products p ON p.id = t.product_id
                                  WHERE p."TYPE" = 'DEBIT'
                                    AND t."TYPE" = 'WITHDRAW'
                                    AND t.user_id = ?)
                   AS result
                """;
        return jdbcTemplate.queryForObject(sql, new SearchResultMapper(),
                userId, userId, userId, 100000, userId);
    }
}
