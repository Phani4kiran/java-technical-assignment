package kata.supermarket;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

public class DiscountByWeight {
    private List<ItemByWeight> itemByWeight;
    private Function<List<ItemByWeight>, BigDecimal> discountFunction;

    public DiscountByWeight(List<ItemByWeight> itemByWeight, Function<List<ItemByWeight>, BigDecimal> discountFunction) {
        this.discountFunction = discountFunction;
        this.itemByWeight = itemByWeight;
    }

    public BigDecimal apply() {
        return discountFunction.apply(itemByWeight);
    }
}
