package org.skypro.starbank.marketing.mapper;

import org.skypro.starbank.marketing.dto.Recommendation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RecommendationMapper implements RowMapper<Recommendation> {
    @Override
    public Recommendation mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Recommendation(
                rs.getString("name"),
                rs.getString("id"),
                rs.getString("text"));
    }
}
