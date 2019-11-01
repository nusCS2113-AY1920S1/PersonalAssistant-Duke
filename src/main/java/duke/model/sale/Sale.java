package duke.model.sale;

import java.util.Date;
import java.util.Objects;

import static duke.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Sale in the sale list.
 */
public class Sale {

    //Identity field
    private final long id;

    //Data fields
    private final String description;
    private final double value;
    private final boolean isSpend;
    private final Date saleDate;
    private final String remarks;

    public Sale(String description, double value, boolean isSpend, Date saleDate, String remarks) {
        requireAllNonNull(description, saleDate, value, remarks);
        this.id = generateId();
        this.description = description;
        this.isSpend = isSpend;
        if (isSpend && value > 0.0) {
            this.value = -value;
        } else {
            this.value = value;
        }
        this.saleDate = saleDate;
        this.remarks = remarks;
    }

    /**
     * Creates a sale.
     * Every field must be present and not null.
     */
    public Sale(long id, String description, double value, boolean isSpend, Date saleDate, String remarks) {
        requireAllNonNull(id, description, value, isSpend, saleDate, remarks);

        this.id = id;
        this.description = description;
        this.isSpend = isSpend;
        if (isSpend && value > 0.0) {
            this.value = -value;
        } else {
            this.value = value;
        }
        this.saleDate = saleDate;
        this.remarks = remarks;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public double getValue() {
        return value;
    }

    public boolean isSpend() {
        return isSpend;
    }

    public String getRemarks() {
        return remarks;
    }

    private long generateId() {
        return System.currentTimeMillis();
    }

    @Override
    public boolean equals(Object s) {
        if (this == s) {
            return true;
        }
        if (s == null || getClass() != s.getClass()) {
            return false;
        }
        Sale sale = (Sale) s;
        return id == sale.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}