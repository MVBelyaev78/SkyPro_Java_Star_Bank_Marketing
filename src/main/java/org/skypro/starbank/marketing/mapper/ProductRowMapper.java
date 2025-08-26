package org.skypro.starbank.marketing.mapper;


import org.skypro.starbank.marketing.dto.dynamicrule.DynamicRule;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class ProductRowMapper  implements RowMapper<DynamicRule> {
    @Override
    public DynamicRule mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new DynamicRule(
                rs.getString("product_name"),
                UUID.fromString(rs.getString("product_id")),
                rs.getString("product_text"),
                new ArrayList<>()
        );
    }
}
