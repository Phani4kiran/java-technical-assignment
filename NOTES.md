# Notes

Please add here any notes, assumptions and design decisions that might help up understand your though process.
Assumption:
 item by unit discount rules: if unit size > 5, then 20% discount.
 item by Weight discount rules: if the weight > 1kg then 20% discount

Worked : Discount , DiscoiuntByUnit and DiscountByWeight class. Calculated discount rules in discountsForItemByWeight, discountsForItemByunit methods in Basket class
Tests: Added multipleSameItemsDiscountedPricedPerUnit, multipleSameItemsDiscountedPricedByWeight senarios
Result : Passed

Further 
if items as provided with ids, we can have rules mix and max discount rules as well.
