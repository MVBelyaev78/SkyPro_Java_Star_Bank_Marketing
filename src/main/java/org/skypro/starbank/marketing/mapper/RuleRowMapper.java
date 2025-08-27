package org.skypro.starbank.marketing.mapper;

import org.skypro.starbank.marketing.dto.dynamicrule.QueryType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class RuleRowMapper implements RowMapper<QueryType> {
    @Override
    public QueryType mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new QueryType(
                rs.getString("query"),
                Arrays.asList((String[]) rs.getArray("arguments").getArray()),
                rs.getBoolean("negate")
        );
    }
}
