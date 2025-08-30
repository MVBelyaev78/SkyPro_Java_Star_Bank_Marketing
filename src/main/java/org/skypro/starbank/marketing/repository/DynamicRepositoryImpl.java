package org.skypro.starbank.marketing.repository;

import org.skypro.starbank.marketing.dto.dynamicrule.DynamicRule;
import org.skypro.starbank.marketing.dto.dynamicrule.QueryType;
import org.skypro.starbank.marketing.dto.dynamicrule.RuleStatInfoAll;
import org.skypro.starbank.marketing.mapper.ProductRowMapper;
import org.skypro.starbank.marketing.mapper.RuleRowMapper;
import org.skypro.starbank.marketing.mapper.RuleStatRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public class DynamicRepositoryImpl implements DynamicRepository {
    private final JdbcTemplate jdbcTemplatePostgres;

    public DynamicRepositoryImpl(JdbcTemplate jdbcTemplatePostgres) {
        this.jdbcTemplatePostgres = jdbcTemplatePostgres;
    }

    @Override
    public DynamicRule addRule(DynamicRule product) {
        String productSql = "INSERT INTO recommendation_products (id, product_id, product_name, product_text) VALUES (?, ?, ?, ?) returning id";
        UUID id = jdbcTemplatePostgres.queryForObject(productSql, UUID.class, UUID.randomUUID(), product.recommendationUuid(), product.name(), product.text());

        String ruleSql = "INSERT INTO recommendation_rules (id, recommendation_id, query, arguments, negate) VALUES (?, ?, ?, ?, ?)";
        product.rule()
                .stream()
                .<PreparedStatementCreator>map(rule -> connection -> {
                    PreparedStatement ps = connection.prepareStatement(ruleSql);
                    ps.setObject(1, UUID.randomUUID());
                    ps.setObject(2, id);
                    ps.setString(3, rule.query());
                    ps.setArray(4, connection.createArrayOf("text", rule.arguments().toArray()));
                    ps.setBoolean(5, rule.negate());
                    return ps;
                })
                .forEach(jdbcTemplatePostgres::update);

        return new DynamicRule(
                id,
                product.name(),
                product.recommendationUuid(),
                product.text(),
                product.rule()
        );
    }

    @Override
    public Collection<DynamicRule> getRules() {
        String productSql = "SELECT * FROM recommendation_products";
        List<DynamicRule> products = jdbcTemplatePostgres.query(productSql, new ProductRowMapper());

        for (DynamicRule product : products) {
            String rulesSql = "SELECT * FROM recommendation_rules WHERE recommendation_id = ?";
            List<QueryType> rules = jdbcTemplatePostgres.query(
                    rulesSql,
                    new RuleRowMapper(),
                    product.id()
            );
            product.rule().addAll(rules);
        }

        return products;
    }

    @Override
    public void deleteRule(UUID recommendationUuid) {
        String sql = "DELETE FROM recommendation_products WHERE product_id = ?";
        jdbcTemplatePostgres.update(sql, recommendationUuid);
    }

    @Override
    public void addRuleStat(UUID dynamicRuleUuid) {
        final String sql = "INSERT INTO recommendation_products_stat(id, recommendation_id) VALUES (?, ?))";
        jdbcTemplatePostgres.update(sql, UUID.randomUUID(), dynamicRuleUuid);
    }

    @Override
    public RuleStatInfoAll getRulesStat() {
        final String sql = """
                SELECT t.recommendation_id, COUNT(1) count
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
