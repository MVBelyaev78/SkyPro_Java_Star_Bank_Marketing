package org.skypro.starbank.marketing.dto.recommendation;

import java.util.UUID;

public record UserInfo(
        UUID id,
        String firstName,
        String lastName
) {
    public UserInfo(UUID id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
