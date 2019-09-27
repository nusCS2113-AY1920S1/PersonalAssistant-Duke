package duke.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Calendar;
import java.util.Date;

public class Sale {
    private long id = System.currentTimeMillis();
    private String description = "description";
    private double value = 0.0;
    private Date saleDate = Calendar.getInstance().getTime();
    private String remarks = "N/A";

    public Sale() {

    }

    public Sale(@JsonProperty("description") String description,
                 @JsonProperty("value") double value,
                 @JsonProperty("saleDate") Date saleDate) {
        this.description = description;
        this.value = value;
        this.saleDate = saleDate;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getValue() {
        return value;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}