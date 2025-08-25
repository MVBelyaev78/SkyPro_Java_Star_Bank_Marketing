package org.skypro.starbank.marketing.dto.dynamicrule;

import java.util.List;

public record QueryType(String query, List<String> arguments, Boolean negate) {
}
