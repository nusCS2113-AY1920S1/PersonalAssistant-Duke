package duke.logic.command.shopping;

import java.util.Optional;

public class ShoppingDescriptor {
    private String name;
    private Double quantity;
    private String remarks;
    private Double unitCost;

    public ShoppingDescriptor() {}

    public ShoppingDescriptor(ShoppingDescriptor toCopy) {
        setName(toCopy.name);
        setQuantity(toCopy.quantity);
        setRemarks(toCopy.remarks);
        setUnitCost(toCopy.unitCost);
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<Double> getQuantity() {
        return Optional.ofNullable(quantity);
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Optional<String> getRemarks() {
        return Optional.ofNullable(remarks);
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Optional<Double> getUnitCost() {
        return Optional.ofNullable(unitCost);
    }

    public void setUnitCost(Double unitCost) {
        this.unitCost = unitCost;
    }
}
