package org.skypro.starbank.marketing.recommendation.repository;

import org.skypro.starbank.marketing.recommendation.dto.SearchResult;
import org.skypro.starbank.marketing.recommendation.mapper.SearchResultMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Locale;

public class DynamicRulesRepository {
    private final JdbcTemplate jdbcTemplate;

    public DynamicRulesRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public SearchResult getUserCheckQuery(String userId) {
        final String sql = "select exists (select null from public.users u where u.id = ?)";
        return jdbcTemplate.queryForObject(sql, new SearchResultMapper(), userId);
    }

    public SearchResult getUserOfQuery(String userId, List<String> arguments, Boolean negate) {
        if (arguments.isEmpty()) {
            throw new IllegalArgumentException("incorrect list of arguments");
        }
        if (arguments.size() > 1) {
            throw new IllegalArgumentException("incorrect list of arguments");
        }
        if (!arguments.get(0).toUpperCase(Locale.ROOT).equals("DEBIT") ||
                !arguments.get(0).toUpperCase(Locale.ROOT).equals("CREDIT") ||
                !arguments.get(0).toUpperCase(Locale.ROOT).equals("INVEST") ||
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
                )
                """, negate ? "not" : "");
        return jdbcTemplate.queryForObject(sql,
                new SearchResultMapper(),
                userId,
                arguments.get(0).toUpperCase(Locale.ROOT));
    }

    public SearchResult getActiveUserOfQuery(String userId, List<String> arguments, Boolean negate) {
        if (arguments.isEmpty()) {
            throw new IllegalArgumentException("incorrect list of arguments");
        }
        if (arguments.size() > 1) {
            throw new IllegalArgumentException("incorrect list of arguments");
        }
        if (!arguments.get(0).toUpperCase(Locale.ROOT).equals("DEBIT") ||
                !arguments.get(0).toUpperCase(Locale.ROOT).equals("CREDIT") ||
                !arguments.get(0).toUpperCase(Locale.ROOT).equals("INVEST") ||
                !arguments.get(0).toUpperCase(Locale.ROOT).equals("SAVING")) {
            throw new IllegalArgumentException("incorrect list of arguments");
        }
        final Integer maxTransactionsCount = 5;
        final String sql = String.format("""
                select %s ? < (
                    select count(1)
                      from public.transactions t
                      join public.products p on p.id = t.product_id
                     where t.user_id = ?
                       and p."TYPE" = ?
                )
                """, negate ? "not" : "");
        return jdbcTemplate.queryForObject(sql,
                new SearchResultMapper(),
                maxTransactionsCount,
                userId,
                arguments.get(0).toUpperCase(Locale.ROOT));
    }
}
