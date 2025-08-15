package org.skypro.starbank.marketing.model.user;

import java.util.UUID;

public final class User {
    private final UUID userId;
    private final String userName;
    private final String firstName;
    private final String lastName;

    public User(UUID userId, String userName, String firstName, String lastName) {
        this.userId = userId;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return String.format("User: ID=%s, name=\"%s\", firstName=\"%s\", lastName=\"%s\"",
                userId, userName, firstName, lastName);
    }
}
