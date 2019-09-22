package wallet.model.record;

import java.time.LocalDate;

public abstract class Record {
    private String description;
    private LocalDate createdDate;

    public Record(String description, LocalDate createdDate) {
        this.description = description;
        this.createdDate = createdDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

}
