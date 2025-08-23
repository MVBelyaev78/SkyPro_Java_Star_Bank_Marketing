package org.skypro.starbank.marketing.temp_stage_2;


import java.util.Collection;

public record QueryType(String query, Collection<String> arguments, Boolean negate) {
}
