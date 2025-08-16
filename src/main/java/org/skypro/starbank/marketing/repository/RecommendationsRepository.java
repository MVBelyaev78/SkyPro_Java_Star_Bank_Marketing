package org.skypro.starbank.marketing.repository;

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

    public boolean isUserHasDebitProduct(UUID user){
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                "SELECT EXISTS( SELECT 1 FROM TRANSACTIONS t JOIN PRODUCT p ON t.PRODUCT_ID = p.ID WHERE t.USER_ID = ? AND p.TYPE = 'DEBIT') AS user_debit_product",
                Boolean.class,
                user));
    }
}
