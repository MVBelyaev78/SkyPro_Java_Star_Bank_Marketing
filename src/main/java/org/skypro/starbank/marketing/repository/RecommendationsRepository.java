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

    public boolean isUserHasDebitProduct(String userId) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                "SELECT EXISTS( SELECT 1 FROM TRANSACTIONS t JOIN PRODUCT p ON t.PRODUCT_ID = p.ID WHERE t.USER_ID = ? AND p.TYPE = 'DEBIT') AS user_debit_product",
                Boolean.class,
                userId));
    }

    public boolean amountDepositsDEBITGreater50000(String userId) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                "SELECT " +
                        "COALESCE(SUM(transactions.amount), 0) >= 50000 " +
                        "FROM transactions JOIN products ON products.id = transactions.product_id " +
                        "WHERE products.type = 'DEBIT' AND transaction.type = 'DEPOSIT' AND transactions.user_id = ?",
                Boolean.class,
                userId));
    }

    public boolean amountDepositsSAVINGGreater50000(String userId) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                "SELECT " +
                        "COALESCE(SUM(transactions.amount), 0) >= 50000 " +
                        "FROM transactions JOIN products ON products.id = transactions.product_id " +
                        "WHERE products.type = 'SAVING' AND transaction.type = 'DEPOSIT' AND transactions.user_id = ?",
                Boolean.class,
                userId));
    }

    public boolean amountDepositsGreaterWithdraw(String userId) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                "SELECT " +
                        "COALESCE(SUM(CASE WHEN t.TYPE = 'DEPOSIT' THEN t.amount ELSE 0 END), 0) > " +
                        "COALESCE(SUM(CASE WHEN t.TYPE = 'WITHDRAW' THEN t.amount ELSE 0 END), 0) " +
                        "FROM transactions t JOIN products p ON p.id = t.product_id " +
                        "WHERE p.TYPE = 'DEBIT' AND t.user_id = ?",
                Boolean.class,
                userId));
    }

}
