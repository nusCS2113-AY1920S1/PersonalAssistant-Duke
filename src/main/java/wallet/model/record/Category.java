package wallet.model.record;

public enum Category {
    FOOD,
    TRANSPORT,
    BILLS,
    SHOPPING,
    OTHERS;

    /**
     * Parses string input into corresponding category.
     * @param category String input of category.
     * @return Category enum.
     */
    public static Category getCategory(String category) {
        switch (category.toLowerCase()) {
        case "food":
            return Category.FOOD;
        case "transport":
            return Category.TRANSPORT;
        case "bills":
            return Category.BILLS;
        case "shopping":
            return Category.SHOPPING;
        case "others":
            return Category.OTHERS;
        default:
            return null;
        }
    }
}
