package org.skypro.starbank.marketing.repository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.skypro.starbank.marketing.dto.recommendation.SearchResult;
import org.skypro.starbank.marketing.mapper.SearchResultMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    public SearchResult getUserOfQuery(@Parameter(description = "UUID пользователя") String userId,
                                       @Parameter(description = "Тип продукта [DEBIT/CREDIT/INVEST/SAVING]") List<String> arguments,
                                       @Parameter(description = "Инвертировать результат") Boolean negate) {
        if (arguments.isEmpty()) {
            throw new IllegalArgumentException("incorrect list of arguments");
        }
        if (arguments.size() > 1) {
            throw new IllegalArgumentException("incorrect list of arguments");
        }
        if (!arguments.get(0).toUpperCase(Locale.ROOT).equals("DEBIT") &&
                !arguments.get(0).toUpperCase(Locale.ROOT).equals("CREDIT") &&
                !arguments.get(0).toUpperCase(Locale.ROOT).equals("INVEST") &&
                !arguments.get(0).toUpperCase(Locale.ROOT).equals("SAVING")) {
            throw new IllegalArgumentException("incorrect list of arguments");
        }
        final String sql = String.format("""
                select %s exists (
                    select null
                      from public.transactions t
                      join public.products p on p.id = t.product_id
                     where t.user_id = ?
                       and p."TYPE" = ?
                ) AS result
                """, negate ? "not" : "");
        return jdbcTemplate.queryForObject(sql,
                new SearchResultMapper(),
                userId,
                arguments.get(0).toUpperCase(Locale.ROOT));
    }

    @Operation(summary = "Проверка активного использования",
            description = "Проверяет, активно ли пользователь использует продукт определенного типа")
    public SearchResult getActiveUserOfQuery(@Parameter(description = "UUID пользователя") String userId,
                                             @Parameter(description = "Тип продукта [DEBIT/CREDIT/INVEST/SAVING]") List<String> arguments,
                                             @Parameter(description = "Инвертировать результат") Boolean negate) {
        if (arguments.isEmpty()) {
            throw new IllegalArgumentException("incorrect list of arguments");
        }
        if (arguments.size() > 1) {
            throw new IllegalArgumentException("incorrect list of arguments");
        }
        if (!arguments.get(0).toUpperCase(Locale.ROOT).equals("DEBIT") &&
                !arguments.get(0).toUpperCase(Locale.ROOT).equals("CREDIT") &&
                !arguments.get(0).toUpperCase(Locale.ROOT).equals("INVEST") &&
                !arguments.get(0).toUpperCase(Locale.ROOT).equals("SAVING")) {
            throw new IllegalArgumentException("incorrect list of arguments");
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
                """, negate ? "not" : "");
        return jdbcTemplate.queryForObject(sql,
                new SearchResultMapper(),
                minTransactions,
                userId,
                arguments.get(0).toUpperCase(Locale.ROOT));
    }
    public SearchResult getTransactionSumCompare(@Parameter(description = "UUID пользователя") String userId,
                                                 @Parameter(description = "Тип продукта [DEBIT/CREDIT/INVEST/SAVING]") List<String> arguments,
                                                 @Parameter(description = "Инвертировать результат") Boolean negate) {
        if (arguments == null || arguments.isEmpty() || arguments.size() != 4) {
            throw new IllegalArgumentException("incorrect list of arguments");
        }

        String productType = arguments.get(0).toUpperCase(Locale.ROOT);
        if (!productType.equals("DEBIT") && !productType.equals("CREDIT") &&
                !productType.equals("INVEST") && !productType.equals("SAVING")) {
            throw new IllegalArgumentException("incorrect product type");
        }

        String transactionType = arguments.get(1).toUpperCase(Locale.ROOT);
        if (!transactionType.equals("WITHDRAW") && !transactionType.equals("DEPOSIT")) {
            throw new IllegalArgumentException("incorrect transaction type");
        }

        String comparisonOperator = arguments.get(2);
        if (!comparisonOperator.matches("[><=]+|>=|<=")) {
            throw new IllegalArgumentException("incorrect comparison operator");
        }

        int comparisonValue;
        try {
            comparisonValue = Integer.parseInt(arguments.get(3));
            if (comparisonValue < 0) {
                throw new IllegalArgumentException("C must be a non-negative integer");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("C must be a non-negative integer");
        }

        final String sql = String.format("""
        SELECT SUM(transaction_amount) FROM transactions 
        WHERE user_id = ? AND product_type = ? AND transaction_type = ? 
        GROUP BY user_id HAVING SUM(transaction_amount) %s ?
    """, comparisonOperator);

        return jdbcTemplate.queryForObject(sql,
                new SearchResultMapper(),
                userId,
                productType,
                transactionType,
                comparisonValue);
    }

}
