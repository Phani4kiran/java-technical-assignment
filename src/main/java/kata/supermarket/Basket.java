package kata.supermarket;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Basket {
    private final List<Item> items;

    public Basket() {
        this.items = new ArrayList<>();
    }

    public void add(final Item item) {
        this.items.add(item);
    }

    List<Item> items() {
        return Collections.unmodifiableList(items);
    }

    public BigDecimal total() {
        return new TotalCalculator().calculate();
    }

    private class TotalCalculator {
        private final List<Item> items;

        TotalCalculator() {
            this.items = items();
        }

        private BigDecimal subtotal() {
            return items.stream().map(Item::price)
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO)
                    .setScale(2, RoundingMode.HALF_UP);
        }

        /**
         * TODO: This could be a good place to apply the results of
         * the discount calculations.
         * It is not likely to be the best place to do those calculations.
         * Think about how Basket could interact with something
         * which provides that functionality.
         */
        private BigDecimal discounts() {
            //  identify number of items by unit .
            //map(Item::id).sorted().count();
            BigDecimal itemByUnitDiscount = discountsForItemByUnitList();
            BigDecimal itemByWeightDiscount = discountsForItemByWeight();

            return itemByUnitDiscount.add(itemByWeightDiscount).setScale(2, RoundingMode.HALF_UP);//.add(BigDecimal.ZERO);
        }

        private BigDecimal discountsForItemByUnitList() {
            //  identify discount of items by unit .
            List<Item> itemByUnitList = items.stream().filter(item -> item instanceof ItemByUnit).collect(Collectors.toList());//collect(Collectors.toList());
            DiscountByUnit discountByUnit = new DiscountByUnit(itemByUnitList, unitList -> (unitList.size() > 5) ? BigDecimal.valueOf(0.2) : BigDecimal.valueOf(0.0));
            BigDecimal itemByUnitDiscount = discountByUnit.apply().setScale(2, RoundingMode.HALF_UP).multiply(items.stream().map(Item::price)
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO)
                    );

            return itemByUnitDiscount;//.add(BigDecimal.ZERO);
        }

        private BigDecimal discountsForItemByWeight() {
            //  identify discount items by weight
            List itemByWeightList = items.stream().filter(item -> item instanceof ItemByWeight).collect(Collectors.toList());//collect(Collectors.toList());
            DiscountByWeight discountByWeight = new DiscountByWeight(
                    itemByWeightList,
                    weightList -> (
                            weightList.stream().map(ItemByWeight::getWeightInKilos).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).setScale(2).doubleValue() > 1.00
                                    ? BigDecimal.valueOf(0.2) : BigDecimal.valueOf(0.00)));
            BigDecimal itemByUnitDiscount = discountByWeight.apply().multiply(items.stream().map(Item::price)
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO)
                    );

            return itemByUnitDiscount;//.add(BigDecimal.ZERO);
        }

        private BigDecimal calculate() {
            return subtotal().subtract(discounts());
        }
    }
}
