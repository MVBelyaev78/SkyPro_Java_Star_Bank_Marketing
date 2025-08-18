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

    public SearchResult searchResultStringParam(String userId, String sqlQuery) {
        return jdbcTemplate.queryForObject(
                sqlQuery,
                new SearchResultMapper(),
                userId);
    }
}
