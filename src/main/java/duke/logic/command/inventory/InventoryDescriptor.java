package duke.logic.command.inventory;

import java.util.Optional;

public class InventoryDescriptor {
    private String name;
    private Double quantity;
    private String remarks;

    public InventoryDescriptor() {}

    public InventoryDescriptor(InventoryDescriptor toCopy) {
        setName(toCopy.name);
        setQuantity(toCopy.quantity);
        setRemarks(toCopy.remarks);
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
}
