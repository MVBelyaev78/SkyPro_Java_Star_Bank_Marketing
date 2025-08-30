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
                    select rp.id rule_id,
                           (select count(1)
                              from public.recommendation_products_stat rps
                             where rps.recommendation_id = rp.id) "count"
                      from public.recommendation_products rp
                """;
        return new RuleStatInfoAll(jdbcTemplatePostgres.query(sql, new RuleStatRowMapper()));
    }
}
