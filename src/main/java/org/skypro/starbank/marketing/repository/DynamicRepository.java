package org.skypro.starbank.marketing.repository;

import org.skypro.starbank.marketing.dto.dynamicrule.DynamicRule;
import org.skypro.starbank.marketing.dto.dynamicrule.QueryType;
import org.skypro.starbank.marketing.mapper.ProductRowMapper;
import org.skypro.starbank.marketing.mapper.RuleRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public class DynamicRepository implements DynamicRulesDatabase {
    private final JdbcTemplate jdbcTemplatePostgres;

    public DynamicRepository(JdbcTemplate jdbcTemplatePostgres) {
        this.jdbcTemplatePostgres = jdbcTemplatePostgres;
    }

    @Override
    public DynamicRule addRule(DynamicRule product) {
        String productSql = "INSERT INTO recommendation_products (id, product_id, product_name, product_text) VALUES (?, ?, ?, ?) returning id";
        UUID id = jdbcTemplatePostgres.queryForObject(productSql, UUID.class, UUID.randomUUID(), product.recommendationUuid(), product.name(), product.text());

        for (QueryType rule : product.rule()) {
            jdbcTemplatePostgres.update(connection -> {
                String ruleSql = "INSERT INTO recommendation_rules (id, recommendation_id, query, arguments, negate) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement ps = connection.prepareStatement(ruleSql);

                ps.setObject(1, UUID.randomUUID());
                ps.setObject(2, id);
                ps.setString(3, rule.query());

                Array argumentsArray = connection.createArrayOf("text", rule.arguments().toArray());
                ps.setArray(4, argumentsArray);

                ps.setBoolean(5, rule.negate());
                return ps;
            });
        }

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
}
