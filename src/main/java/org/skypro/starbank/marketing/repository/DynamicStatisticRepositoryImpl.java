package org.skypro.starbank.marketing.repository;

import org.skypro.starbank.marketing.dto.dynamicrule.RuleStatInfoAll;
import org.skypro.starbank.marketing.mapper.RuleStatRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class DynamicStatisticRepositoryImpl implements DynamicStatisticRepository {
    private final JdbcTemplate jdbcTemplatePostgres;

    public DynamicStatisticRepositoryImpl(JdbcTemplate jdbcTemplatePostgres) {
        this.jdbcTemplatePostgres = jdbcTemplatePostgres;
    }

    @Override
    public void addRuleStat(UUID dynamicRuleUuid) {
        final String sql = "INSERT INTO recommendation_products_stat(id, recommendation_id) VALUES (?, ?)";
        jdbcTemplatePostgres.update(sql, UUID.randomUUID(), dynamicRuleUuid);
    }

    @Override
    public RuleStatInfoAll getRulesStat() {
        final String sql = """
                SELECT t.recommendation_id rule_id, COUNT(1) count
                  FROM recommendation_products_stat t
                 GROUP BY t.recommendation_id
                """;
        return new RuleStatInfoAll(jdbcTemplatePostgres.query(sql, new RuleStatRowMapper()));
    }

    @Override
    public void deleteRuleStatAll(UUID dynamicRuleUuid) {
        final String sql = "DELETE FROM recommendation_products_stat t WHERE t.recommendation_id = ?";
        jdbcTemplatePostgres.update(sql, dynamicRuleUuid);
    }
}
