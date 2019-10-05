package dukeobjects;

import java.util.Map;
import java.util.StringJoiner;

public class Expense extends DukeItem {
    /**
     * The amount of money of the expense.
     */
    private final double amount;
    /**
     * The description of the expense.
     */
    private final String description;
    /**
     * Whether or not the expense is tentative.
     */
    private final boolean isTentative;

    /**
     * {@inheritDoc}
     */
    public static class Builder extends DukeItem.Builder<Builder> {
        private double amount = 0;
        private String description = "";
        private boolean isTentative = false;

        /**
         * {@inheritDoc}
         */
        public Builder() {}

        /**
         * {@inheritDoc}
         */
        public Builder(Expense expense) {
            super(expense);
            amount = expense.amount;
            description = expense.description;
            isTentative = expense.isTentative;
        }

        /**
         * {@inheritDoc}
         */
        Builder(String storageString) {
            this(storageStringToMap(storageString));
        }

        /**
         * {@inheritDoc}
         */
        Builder(Map<String, String> mappedStorageString) {
            super(mappedStorageString);
            amount = Double.parseDouble(mappedStorageString.get("amount"));
            description = mappedStorageString.get("description");
            isTentative = Boolean.parseBoolean(mappedStorageString.get("isTentative"));
        }

        /**
         * Sets the amount of the expense.
         *
         * @param amount the amount of the expense.
         * @return this builder.
         */
        public Builder setAmount(double amount) {
            this.amount = amount;
            return this;
        }

        /**
         * Sets the description of the expense.
         *
         * @param description the description of the expense.
         * @return this builder.
         */
        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        /**
         * Sets the tentativeness of the expense.
         *
         * @param tentative the description of the expense.
         * @return this builder.
         */
        public Builder setTentative(boolean tentative) {
            isTentative = tentative;
            return this;
        }

        /**
         * Builds the expense.
         *
         * @return the expense.
         */
        public Expense build() {
            return new Expense(this);
        }
    }

    /**
     * Constructs an expense from the expense builder.
     *
     * @param builder the expense builder.
     */
    private Expense(Builder builder) {
        super(builder);
        amount = builder.amount;
        description = builder.description;
        isTentative = builder.isTentative;
    }

    /**
     * Converts the expense into a string.
     *
     * @return the expense as a string.
     */
    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(" ");
        stringJoiner.add("$" + amount);
        stringJoiner.add(description);
        if (isTentative) {
            stringJoiner.add("(tentative)");
        }
        if (!tags.isEmpty()) {
            stringJoiner.add("Tags: " + tags.stream()
                    .reduce((string, tag) -> string + " " + tag)
                    .toString());
        }

        return stringJoiner.toString();
    }

    /**
     * Converts the expense into a storage string.
     *
     * @return the expense as a storage string.
     */
    @Override
    protected String toStorageString() {
        StringJoiner stringJoiner = new StringJoiner(STORAGE_FIELD_DELIMITER);
        stringJoiner.add(super.toStorageString());
        stringJoiner.add("amount" + STORAGE_NAME_SEPARATOR + amount);
        stringJoiner.add("description" + STORAGE_NAME_SEPARATOR + description);
        stringJoiner.add("isTentative" + STORAGE_NAME_SEPARATOR + isTentative);

        return stringJoiner.toString();
    }
}
