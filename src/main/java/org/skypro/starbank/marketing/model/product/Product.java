package org.skypro.starbank.marketing.model.product;

import java.util.UUID;

public final class Product {
    private final UUID productId;
    private final String productType;
    private final String productName;

    public Product(UUID productId, String productType, String productName) {
        this.productId = productId;
        this.productType = productType;
        this.productName = productName;
    }

    public UUID getProductId() {
        return productId;
    }

    public String getProductType() {
        return productType;
    }

    public String getProductName() {
        return productName;
    }

    @Override
    public String toString() {
        return String.format("Product: ID=%s, type=\"%s\", name=\"%s\"", productId, productType, productName);
    }
}
