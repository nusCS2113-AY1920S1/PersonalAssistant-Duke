package duke.model;

import duke.exception.DukeException;
import duke.logic.parser.Parser;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.StringJoiner;

public class Income extends DukeItem {
    /**
     * The amount of money of the income.
     */
    private final BigDecimal amount;
    /**
     * The description of the income.
     */
    private final String description;

    /**
     * {@inheritDoc}
     */
    public static class Builder extends DukeItem.Builder<Builder> {
        private BigDecimal amount = BigDecimal.ZERO;
        private String description = "";

        public Builder() {
        }

        /**
         * Constructs a builder from an existing income.
         *
         * @param income the income whose values to use as the builder's default values.
         */
        public Builder(Income income) {
            super(income);
            amount = income.amount;
            description = income.description;
        }

        /**
         * {@inheritDoc}
         */
        Builder(String storageString) throws DukeException {
            this(storageStringToMap(storageString));
        }

        /**
         * {@inheritDoc}
         */
        Builder(Map<String, String> mappedStorageString) throws DukeException {
            super(mappedStorageString);
            if (mappedStorageString.containsKey("amount")) {
                setAmount(mappedStorageString.get("amount"));
            }
            if (mappedStorageString.containsKey("description")) {
                setDescription(mappedStorageString.get("description"));
            }
        }

        /**
         * Sets the amount of the income using a string.
         *
         * @param amount the amount of the income as a string.
         * @return this builder.
         * @throws DukeException if the value in amount cannot be converted into a {@code BigDecimal},
         *                       or if the {@code BigDecimal} does not represent a valid amount.
         * @see #setAmount(BigDecimal)
         */
        public Builder setAmount(String amount) throws DukeException {
            try {
                return setAmount(new BigDecimal(amount));
            } catch (NumberFormatException e) {
                throw new DukeException(String.format(DukeException.MESSAGE_INCOME_AMOUNT_INVALID, amount));
            }
        }

        /**
         * Sets the amount of the income.
         *
         * @param amount the amount of the income.
         * @return this builder.
         * @throws DukeException if the {@code BigDecimal} does not represent a valid amount.
         */
        public Builder setAmount(BigDecimal amount) throws DukeException {
            if (amount.scale() > 2) {
                throw new DukeException(
                        String.format(DukeException.MESSAGE_INCOME_AMOUNT_INVALID, amount.toPlainString()));
            }
            this.amount = amount.setScale(2, RoundingMode.UNNECESSARY);
            return this;
        }

        /**
         * Sets the description of the income.
         *
         * @param description the description of the income.
         * @return this builder.
         */
        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        /**
         * Builds the income.
         *
         * @return the income.
         */
        public Income build() {
            return new Income(this);
        }
    }

    /**
     * Constructs an income from the income builder.
     *
     * @param builder the income builder.
     */
    private Income(Builder builder) {
        super(builder);
        amount = builder.amount;
        description = builder.description;
    }

    /**
     * Returns the amount of the income.
     *
     * @return {@link #amount}.
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Returns the description of the income.
     *
     * @return {@link #description}.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Converts the income into a storage string.
     *
     * @return the income as a storage string.
     */
    @Override
    public String toStorageString() {
        StringJoiner stringJoiner = new StringJoiner(STORAGE_FIELD_DELIMITER);
        stringJoiner.add("amount" + STORAGE_NAME_SEPARATOR + amount);
        stringJoiner.add("description" + STORAGE_NAME_SEPARATOR + description);

        return stringJoiner.toString();
    }
}

