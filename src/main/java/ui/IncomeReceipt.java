package ui;

import java.time.LocalDate;
import java.util.ArrayList;

public class IncomeReceipt extends Receipt {

    // Constructors
    public IncomeReceipt(Double cashGained, LocalDate date, ArrayList<String> tags) {
        super(-cashGained, date, tags);
    }

    public IncomeReceipt(Double cashGained, LocalDate date) {
        super(-cashGained, date);
    }

    public IncomeReceipt(Double cashGained) {
        super(-cashGained);
    }

    // -- Setters & Getters
    /**
     * Sets the cashGained by setting the negative as the cashSpent property of this Receipt Object.
     * @param cashGained Double to be set
     */
    public void setCashGained(Double cashGained) {
        this.setCashSpent(-cashGained);
    }

    /**
     * Get the cashGained by negating the cashSpent property of this Receipt Object.
     * @return Double representing the cashGained.
     */
    public Double getCashGained() {
        return -this.getCashSpent();
    }
}
