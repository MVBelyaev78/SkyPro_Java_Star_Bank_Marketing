package org.skypro.starbank.marketing.model.transaction;

import java.util.UUID;

public final class Transaction {
    private final UUID transactionId;
    private final UUID productId;
    private final UUID userId;
    private final String transactionType;
    private final Integer transactionAmount;

    public Transaction(UUID transactionId, UUID productId, UUID userId, String transactionType, Integer transactionAmount) {
        this.transactionId = transactionId;
        this.productId = productId;
        this.userId = userId;
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public UUID getProductId() {
        return productId;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public Integer getTransactionAmount() {
        return transactionAmount;
    }

    @Override
    public String toString() {
        return String.format("Transaction: ID=%s, productID=%s, userID=%s, type=\"%s\", amount=%s",
                transactionId, productId, userId, transactionType, transactionAmount);
    }
}
