package org.skypro.starbank.marketing.dynamicrule.dto;

import java.util.List;

public record QueryType(String query, List<String> arguments, Boolean negate) {
}
