package org.skypro.starbank.marketing.repository;

import org.skypro.starbank.marketing.dto.dynamicrule.DynamicRule;
import org.skypro.starbank.marketing.dto.dynamicrule.QueryType;
import org.skypro.starbank.marketing.mapper.ProductRowMapper;
import org.skypro.starbank.marketing.mapper.RuleRowMapper;
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

    public DynamicRepositoryImpl(JdbcTemplate jdbcTemplatePostgres, DynamicStatisticRepository dynamicStatisticRepo) {
        this.jdbcTemplatePostgres = jdbcTemplatePostgres;
    }

    @Override
    public DynamicRule addRule(DynamicRule product) {
        String productSql = "INSERT INTO recommendation_products (id, product_id, product_name, product_text) VALUES (?, ?, ?, ?) returning id";
        UUID id = jdbcTemplatePostgres.queryForObject(productSql, UUID.class, UUID.randomUUID(), product.productId(), product.productName(), product.productText());

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
                product.productName(),
                product.productId(),
                product.productText(),
                product.rule()
        );
    }

    @Override
    public Collection<DynamicRule> getRules() {
        String productSql = "SELECT * FROM recommendation_products";
        List<DynamicRule> products = jdbcTemplatePostgres.query(productSql, new ProductRowMapper());

        String rulesSql = "SELECT * FROM recommendation_rules WHERE recommendation_id = ?";
        products.forEach(product -> {
            List<QueryType> rules = jdbcTemplatePostgres.query(
                    rulesSql,
                    new RuleRowMapper(),
                    product.id()
            );
            product.rule().addAll(rules);
        });

        return products;
    }

    @Override
    public void deleteRule(UUID recommendationUuid) {
        String sql = "DELETE FROM recommendation_products WHERE product_id = ?";
        jdbcTemplatePostgres.update(sql, recommendationUuid);
    }
}
