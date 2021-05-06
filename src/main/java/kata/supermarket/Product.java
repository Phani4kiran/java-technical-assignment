package kata.supermarket;

import java.math.BigDecimal;

public class Product {
    private final String id;
    private final BigDecimal pricePerUnit;

    public Product(String id, final BigDecimal pricePerUnit) {
        this.id = id;
        this.pricePerUnit = pricePerUnit;
    }

    String id() {
        return id;
    }
    BigDecimal pricePerUnit() {
        return pricePerUnit;
    }

    public Item oneOf() {
        return new ItemByUnit(this);
    }
}
