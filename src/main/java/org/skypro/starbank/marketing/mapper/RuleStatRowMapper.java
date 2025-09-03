package org.skypro.starbank.marketing.mapper;

import org.skypro.starbank.marketing.dto.dynamicrule.RuleStatInfo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class RuleStatRowMapper implements RowMapper<RuleStatInfo> {
    @Override
    public RuleStatInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new RuleStatInfo(
                UUID.fromString(rs.getString("rule_id")),
                rs.getInt("count"));
    }
}
