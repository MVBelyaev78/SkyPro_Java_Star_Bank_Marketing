package org.skypro.starbank.marketing.dynamicrule.dto;

import java.util.Collection;

public record QueryType(String query, Collection<String> arguments, Boolean negate) {
}
