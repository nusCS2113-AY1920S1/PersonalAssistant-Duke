package duke.model;

import duke.exception.DukeException;
import duke.logic.Parser.Parser;

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
     * The time of the income.
     */
    private final LocalDateTime time;

    /**
     * {@inheritDoc}
     */
    public static class Builder extends DukeItem.Builder<Builder> {
        private BigDecimal amount = BigDecimal.ZERO;
        private String description = "";
        private LocalDateTime time = LocalDateTime.now();

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
            time = income.time;
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
            if (mappedStorageString.containsKey("time")) {
                setTime(Parser.parseTime(mappedStorageString.get("time")));
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
         * Sets the time of the income using a string.
         *
         * @param time the time of the income as a string.
         * @return this builder.
         * @throws DukeException if the time string cannot be parsed into a {@code LocalDateTime} object.
         * @see #setTime(LocalDateTime)
         */
        public Builder setTime(String time) throws DukeException {
            try {
                return setTime(Parser.parseTime(time));
            } catch (DukeException e) {
                throw new DukeException(String.format(DukeException.MESSAGE_INCOME_TIME_INVALID, time));
            }
        }

        /**
         * Sets the time of the income.
         *
         * @param time the time of the income.
         * @return this builder.
         */
        public Builder setTime(LocalDateTime time) {
            this.time = time;
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
        time = builder.time;
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
     * Returns the date of the income.
     *
     * @return {@link #time}.
     */
    public LocalDateTime getTime() {
        return time;
    }

    /**
     * Return the formatted time.
     *
     * @return String of time that is formatted
     */
    public String getTimeString() {
        return Parser.formatTime(time);
    }

    /**
     * Converts the income into a string.
     *
     * @return the income as a string.
     */
    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(" ");
        stringJoiner.add((amount.compareTo(BigDecimal.valueOf(0)) < 0 ? "-$" + amount.abs() : "$" + amount));
        stringJoiner.add(description);
        stringJoiner.add(Parser.formatTime(time));

        return stringJoiner.toString();
    }

    /**
     * Converts the income into a storage string.
     *
     * @return the income as a storage string.
     */
    @Override
    public String toStorageString() {
        StringJoiner stringJoiner = new StringJoiner(STORAGE_FIELD_DELIMITER);
        stringJoiner.add(super.toStorageString());
        stringJoiner.add("amount" + STORAGE_NAME_SEPARATOR + amount);
        stringJoiner.add("description" + STORAGE_NAME_SEPARATOR + description);
        stringJoiner.add("time" + STORAGE_NAME_SEPARATOR + Parser.formatTime(time));

        return stringJoiner.toString();
    }
}

