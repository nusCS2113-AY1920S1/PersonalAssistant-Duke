package duke.model.payment;

import duke.exception.DukeException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Payment {

    // private static final int DESCRIPTION_MAX_LENGTH = 500;
    private static final String NOT_ASSIGNED = "";
    private static final Priority DEFAULT_PRIORITY = Priority.MEDIUM;

    private String description;
    private String receiver;
    private LocalDate due;
    // private int daysToDue;
    private String tag;

    private BigDecimal amount;
    private Priority priority;

    private enum Priority {
        HIGH("High", 3),
        MEDIUM("Medium", 2),
        LOW("Low", 1);

        private String nameShowed;
        private int numeratedLevel;

        public String toString() {
            return nameShowed;
        }

        public int getNumeratedLevel() {
            return numeratedLevel;
        }

        Priority(String nameShowed, int numeratedLevel) {
            this.nameShowed = nameShowed;
            this.numeratedLevel = numeratedLevel;
        }
    }

    public static class Builder {
        private String description = NOT_ASSIGNED;
        private String receiver = NOT_ASSIGNED;
        private LocalDate due;
        private String tag = NOT_ASSIGNED;

        private BigDecimal amount;
        private Priority priority = DEFAULT_PRIORITY;

        public Builder() {

        }

        public Builder(Payment payment) {
            description = payment.description;
            receiver = payment.receiver;
            due = payment.due;
            tag = payment.tag;
            amount = payment.amount;
            priority = payment.priority;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setReceiver(String receiver) {
            this.receiver = receiver;
            return this;
        }

        public Builder setDue(String due) throws DukeException {
            try {
                this.due = LocalDate.parse(due, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException e) {
                throw new DukeException(String.format(DukeException.MESSAGE_PAYMENT_TIME_INVALID, due));
            }
            return this;
        }

        public Builder setTag(String note) {
            this.tag = note;
            return this;
        }

        public Builder setAmount(String amount) throws DukeException {
            try {
                this.amount = new BigDecimal(amount);
            } catch (NumberFormatException e) {
                throw new DukeException(String.format(DukeException.MESSAGE_PAYMENT_AMOUNT_INVALID, amount));
            }
            return this;
        }

        public Builder setPriority(String priority) throws DukeException {
            try {
                this.priority = Priority.valueOf(priority.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new DukeException(String.format(DukeException.MESSAGE_PRIORITY_NAME_INVALID, priority));
            }
            return this;
        }

        public Payment build() {
            return new Payment(this);
        }
    }

    public Payment(Builder builder) {
        description = builder.description;
        receiver = builder.receiver;
        due = builder.due;
        tag = builder.tag;
        amount = builder.amount;
        priority = builder.priority;
    }

    public String getDescription() {
        return description;
    }

    public String getReceiver() {
        return receiver;
    }

    public LocalDate getDue() {
        return due;
    }

    public String getTag() {
        return this.tag;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public String getPriority() {
        return priority.toString();
    }

    public int getNumeratedPriority() {
        return priority.getNumeratedLevel();
    }

    public boolean containsKeyword(String keyword) {
        return description.toLowerCase().contains(keyword.toLowerCase())
                || receiver.toLowerCase().contains(keyword.toLowerCase())
                || tag.toLowerCase().contains(keyword.toLowerCase());
    }

}
