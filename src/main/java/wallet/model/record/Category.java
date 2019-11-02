package wallet.model.record;

public enum Category {
    BILLS,
    FOOD,
    OTHERS,
    SHOPPING,
    TRANSPORT;

    /**
     * Parses string input into corresponding category.
     * @param category String input of category.
     * @return Category enum.
     */
    public static Category getCategory(String category) {
        switch (category.toLowerCase()) {
        case "bills":
            return Category.BILLS;
        case "food":
            return Category.FOOD;
        case "others":
            return Category.OTHERS;
        case "shopping":
            return Category.SHOPPING;
        case "transport":
            return Category.TRANSPORT;
        default:
            return null;
        }
    }
}
