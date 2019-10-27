package duke.model.payment;

import duke.exception.DukeException;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Payment {

    // private static final int DESCRIPTION_MAX_LENGTH = 500;
    private static final String NOT_ASSIGNED = "Not assigned yet..";

    private String description;
    private String receiver;
    private LocalDate due;
    // private int daysToDue;
    private String remark;

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
        private String description;
        private String receiver;
        private LocalDate due;
        // private int daysToDue;
        private String remark;

        private BigDecimal amount;
        private Priority priority;

        public Builder() {

        }

        public Builder(Payment payment) {
            description = payment.description;
            receiver = payment.receiver;
            due = payment.due;
            remark = payment.remark;
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

        public Builder setDue(LocalDate due) {
            this.due = due;
            return this;
        }

        public Builder setNote(String note) {
            this.remark = note;
            return this;
        }

        public Builder setAmount(BigDecimal amount) {
            this.amount = amount;
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
        remark = builder.remark;
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

    public String getRemark() {
        return this.remark;
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
        return description.contains(keyword)
                || receiver.contains(keyword)
                || remark.contains(keyword);
    }

}
