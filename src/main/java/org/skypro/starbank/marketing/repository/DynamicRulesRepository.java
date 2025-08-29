package org.skypro.starbank.marketing.repository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.skypro.starbank.marketing.dto.dynamicrule.SearchParameters;
import org.skypro.starbank.marketing.dto.recommendation.SearchResult;
import org.skypro.starbank.marketing.mapper.SearchResultMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Locale;

@Repository
public class DynamicRulesRepository {
    private final JdbcTemplate jdbcTemplate;

    public DynamicRulesRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Operation(summary = "Проверка пользователя", description = "Проверяет, существует ли пользователь в системе")
    public SearchResult getUserCheckQuery(@Parameter(description = "UUID пользователя") String userId) {
        final String sql = "select exists (select null from public.users u where u.id = ?) AS result";
        return jdbcTemplate.queryForObject(sql, new SearchResultMapper(), userId);
    }

    @Operation(summary = "Проверка типа продукта",
            description = "Проверяет, является ли пользователь клиентом определенного типа продукта")
    public SearchResult getUserOfQuery(
            @Parameter(description = "Параметры поиска: UUID пользователя, Тип продукта, Инвертировать результат")
            SearchParameters searchParameters) {
        if (searchParameters.userId() == null || searchParameters.userId().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }

        if (searchParameters.arguments().isEmpty()) {
            throw new IllegalArgumentException("List of arguments is empty");
        }

        if (searchParameters.arguments().size() > 1) {
            throw new IllegalArgumentException("incorrect list of arguments. Expected 1 argument");
        }

        final String productType = searchParameters.arguments().get(0).toUpperCase(Locale.ROOT);
        if (!productType.equals("DEBIT") &&
                !productType.equals("CREDIT") &&
                !productType.equals("INVEST") &&
                !productType.equals("SAVING")) {
            throw new IllegalArgumentException("incorrect product type");
        }

        final String sql = String.format("""
                select %s exists (
                    select null
                      from public.transactions t
                      join public.products p on p.id = t.product_id
                     where t.user_id = ?
                       and p."TYPE" = ?
                ) AS result
                """, searchParameters.negate() ? "not" : "");

        return jdbcTemplate.queryForObject(sql,
                new SearchResultMapper(),
                searchParameters.userId(),
                productType);
    }

    @Operation(summary = "Проверка активного использования",
            description = "Проверяет, активно ли пользователь использует продукт определенного типа")
    public SearchResult getActiveUserOfQuery(
            @Parameter(description = "Параметры поиска: UUID пользователя, Тип продукта, Инвертировать результат")
            SearchParameters searchParameters) {
        if (searchParameters.userId() == null || searchParameters.userId().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }

        if (searchParameters.arguments().isEmpty()) {
            throw new IllegalArgumentException("List of arguments is empty");
        }

        if (searchParameters.arguments().size() > 1) {
            throw new IllegalArgumentException("incorrect list of arguments. Expected 1 argument");
        }

        final String productType = searchParameters.arguments().get(0).toUpperCase(Locale.ROOT);
        if (!productType.equals("DEBIT") &&
                !productType.equals("CREDIT") &&
                !productType.equals("INVEST") &&
                !productType.equals("SAVING")) {
            throw new IllegalArgumentException("incorrect product type");
        }

        final Integer minTransactions = 5;
        final String sql = String.format("""
                select %s ? <= (
                    select count(1)
                      from public.transactions t
                      join public.products p on p.id = t.product_id
                     where t.user_id = ?
                       and p."TYPE" = ?
                ) AS result
                """, searchParameters.negate() ? "not" : "");

        return jdbcTemplate.queryForObject(sql,
                new SearchResultMapper(),
                minTransactions,
                searchParameters.userId(),
                productType);
    }

    @Operation(summary = "Сравнение сумм всех транзакций",
            description = "Запрос сравнивает сумму всех транзакций одного типа по продуктам одного типа с некоторой константой")
    public SearchResult getTransactionSumCompare(
            @Parameter(description = "Параметры поиска: UUID пользователя, Тип продукта, Тип транзакции, Тип сравнения, Неотрицательное целое число, Инвертировать результат")
            SearchParameters searchParameters) {
        if (searchParameters.userId() == null || searchParameters.userId().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }

        if (searchParameters.arguments().isEmpty()) {
            throw new IllegalArgumentException("List of arguments is empty");
        }

        if (searchParameters.arguments().size() < 4) {
            throw new IllegalArgumentException("incorrect list of arguments. Expected 4 arguments");
        }

        final String productType = searchParameters.arguments().get(0).toUpperCase(Locale.ROOT);
        if (!productType.equals("DEBIT") &&
                !productType.equals("CREDIT") &&
                !productType.equals("INVEST") &&
                !productType.equals("SAVING")) {
            throw new IllegalArgumentException("incorrect product type");
        }

        final String transactionType = searchParameters.arguments().get(1).toUpperCase(Locale.ROOT);
        if (!transactionType.equals("DEPOSIT") &&
                !transactionType.equals("WITHDRAW")) {
            throw new IllegalArgumentException("incorrect transaction type");
        }

        final String comparisonOperator = searchParameters.arguments().get(2).toUpperCase(Locale.ROOT);
        if (!comparisonOperator.matches("[><=]+|>=|<=")) {
            throw new IllegalArgumentException("incorrect comparison operator");
        }

        int comparable;
        try {
            comparable = Integer.parseInt(searchParameters.arguments().get(3));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        if (comparable < 0) {
            throw new IllegalArgumentException("the number must be greater than or equals to 0");
        }

        final String sql = String.format("""
                SELECT %s coalesce(sum(t.AMOUNT), 0) %s ? "result"
                  FROM PUBLIC.TRANSACTIONS t
                  JOIN PUBLIC.PRODUCTS p ON p.ID = t.PRODUCT_ID
                 WHERE t.USER_ID = ?
                   AND p."TYPE" = ?
                   AND t."TYPE" = ?;
                """, searchParameters.negate() ? "not" : "", comparisonOperator);

        return jdbcTemplate.queryForObject(sql,
                new SearchResultMapper(),
                comparable,
                searchParameters.userId(),
                productType,
                transactionType);
    }

    @Operation(summary = "Сравнение суммы пополнений с тратами",
            description = "Сравнение суммы пополнений с тратами по всем продуктам одного типа")
    public SearchResult getTransactionSumCompareDepositWithdraw(
            @Parameter(description = "Параметры поиска: UUID пользователя, Тип продукта, Тип сравнения, Инвертировать результат")
            SearchParameters searchParameters) {
        if (searchParameters.userId() == null || searchParameters.userId().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }

        if (searchParameters.arguments().isEmpty()) {
            throw new IllegalArgumentException("List of arguments is empty");
        }

        if (searchParameters.arguments().size() != 2) {
            throw new IllegalArgumentException("incorrect list of arguments. Expected 2 arguments");
        }

        final String productType = searchParameters.arguments().get(0).toUpperCase(Locale.ROOT);
        if (!productType.equals("DEBIT") && !productType.equals("CREDIT") &&
                !productType.equals("INVEST") && !productType.equals("SAVING")) {
            throw new IllegalArgumentException("incorrect product type");
        }

        final String comparisonOperator = searchParameters.arguments().get(1);
        if (!comparisonOperator.matches("[><=]+|>=|<=")) {
            throw new IllegalArgumentException("incorrect comparison operator");
        }

        final String sql = String.format("""
                SELECT %s coalesce(sum(CASE t."TYPE" WHEN ? THEN t.amount ELSE 0 END), 0) %s
                       coalesce(sum(CASE t."TYPE" WHEN ? THEN t.amount ELSE 0 END), 0) "result"
                  FROM PUBLIC.TRANSACTIONS t
                  JOIN PUBLIC.PRODUCTS p ON p.ID = t.PRODUCT_ID
                 WHERE t.USER_ID = ?
                   AND p."TYPE" = ?;
                """, searchParameters.negate() ? "not" : "", comparisonOperator);

        return jdbcTemplate.queryForObject(sql,
                new SearchResultMapper(),
                "DEPOSIT",
                "WITHDRAW",
                searchParameters.userId(),
                productType);
    }
}
