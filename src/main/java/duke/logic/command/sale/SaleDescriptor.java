package duke.logic.command.sale;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * Stores the details a sale.
 */
public class SaleDescriptor {
    private String description;
    private double value;
    private Date saleDate;
    private String remarks;

    public SaleDescriptor() {
    }

    /**
     * Copy constructor.
     *
     * @param toCopy the SaleDescriptor to copy from
     */
    public SaleDescriptor(SaleDescriptor toCopy) {
        setDescription(toCopy.description);
        setValue(toCopy.value);
        setSaleDate(toCopy.saleDate);
        setRemarks(toCopy.remarks);
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Optional<Date> getSaleDate() {
        return Optional.ofNullable(saleDate);
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public Optional<String> getRemarks() {
        return Optional.ofNullable(remarks);
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SaleDescriptor that = (SaleDescriptor) o;
        return Objects.equals(description, that.description)
                && Objects.equals(value, that.value)
                && Objects.equals(saleDate, that.saleDate)
                && Objects.equals(remarks, that.remarks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, value, saleDate, remarks);
    }
}