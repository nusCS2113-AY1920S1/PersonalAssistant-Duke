package wallet.model.record;

import java.time.LocalDate;

public abstract class Record {
    private String description;
    private LocalDate date;

    public Record(String description, LocalDate date) {
        this.description = description;
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate createdDate) {
        this.date = createdDate;
    }

}
