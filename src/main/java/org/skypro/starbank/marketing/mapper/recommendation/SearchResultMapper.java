package org.skypro.starbank.marketing.mapper.recommendation;

import org.skypro.starbank.marketing.dto.recommendation.SearchResult;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchResultMapper implements RowMapper<SearchResult> {
    @Override
    public SearchResult mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new SearchResult(
                rs.getBoolean("result"));
    }
}
