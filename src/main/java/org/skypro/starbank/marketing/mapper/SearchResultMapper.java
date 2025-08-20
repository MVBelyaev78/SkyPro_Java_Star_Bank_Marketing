package org.skypro.starbank.marketing.mapper;

import org.skypro.starbank.marketing.result.SearchResult;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchResultMapper implements RowMapper<SearchResult> {
    @Override
    public SearchResult mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new SearchResult(
                rs.getBoolean("result"),
                rs.getBoolean("has_debit_product"),
                rs.getBoolean("debit_deposits_ge_50000"),
                rs.getBoolean("saving_deposits_ge_50000"),
                rs.getBoolean("deposits_gt_withdrawals"),
                rs.getBoolean("user_exists"));
    }
}
