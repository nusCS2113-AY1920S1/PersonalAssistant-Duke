// @@author parvathi14

package cube.model.promotion;

import java.util.Date;

public class Promotion {
    protected String foodName;
    protected double discount;
    protected double promotionalPrice;
    protected Date startDate;
    protected Date endDate;

    public Promotion(String foodName) {
        this.foodName = foodName;
    }

    /**
     * Gets the name of the food product under promotion.
     *
     * @return the name of the food product.
     */

    public String getName() {
        return foodName;
    }


    /**
     * Sets the discount on the product.
     *
     * @param discount The percentage of discount to be applied to the price of the product.
     */

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    /**
     * Gets the discount on the product.
     *
     * @return the discount on the product.
     */

    public double getDiscount() {
        return discount;
    }

    /**
     * Sets the new promotional price of the product.
     *
     * @param promotionalPrice The new promotional price of the product.
     */

    public void setPromotionalPrice(double promotionalPrice) {
        this.promotionalPrice = promotionalPrice;
    }

    /**
     * Gets the promotional price of the product.
     *
     * @return the promotional price of the product.
     */

    public double getPromotionalPrice() {
        return promotionalPrice;
    }


    /**
     * Sets the start date of the promotional period for the food item.
     *
     * @param startDate the start date of the promotional period.
     */

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the start date of the promotional period for the food item.
     *
     * @return the start date of the promotional period for the food item.
     */

    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the end date of the promotional period for the food item.
     *
     * @param endDate the end date of the promotional period.
     */

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the end date of the promotional period for the food item.
     *
     * @return the end date of the promotional period for the food item.
     */

    public Date getEndDate() {
        return endDate;
    }

    /**
     * Shows that the promotion already exists in the promotion list.
     *
     * @return true
     */
    /*

    public boolean exists(String foodName) {
        if (foodName == this.foodName) {
            return true;
        }
        return false;
    }*/

    /**
     * Casts the promotion to String type.
     *
     * @return the String printout of the promotion.
     */
    @Override
    public String toString() {
        return foodName + "\n  Discount: " + discount +
                "\n  Promotional Price: $" + promotionalPrice +
                "\n  Start Date: " + startDate +
                "\n  End Date: " + endDate;
    }

    /*@Override
    // for Junit test use
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof Promotion) {
            Promotion b = (Promotion) other;
            return foodName.equals(b.foodName)
                    //&& ((type == null && b.type == null) || (type != null && type.equals(b.type)))
                    && promotionalPrice == b.promotionalPrice
                    && discount == b.discount
                    && startDate == b.startDate
                    && endDate == b.endDate;
        } else {
            return false;
        }
    }*/

}
