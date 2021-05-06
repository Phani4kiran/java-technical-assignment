package kata.supermarket;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

public class DiscountByUnit implements Discount {
    private List<Item> itemByUnits;
    private Function<List<?>, BigDecimal> discountFunction;

    public DiscountByUnit(List<Item> itemByUnits, Function<List<?>, BigDecimal> discountFunction) {
        this.discountFunction = discountFunction;
        this.itemByUnits = itemByUnits;
    }

    public BigDecimal apply() {
        return discountFunction.apply(itemByUnits);
    }


}
