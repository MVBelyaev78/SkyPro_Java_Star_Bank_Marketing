package org.skypro.starbank.marketing.dto.dynamicrule;


import java.util.Collection;

public record QueryType(String query, Collection<String> arguments, Boolean negate) {
}
