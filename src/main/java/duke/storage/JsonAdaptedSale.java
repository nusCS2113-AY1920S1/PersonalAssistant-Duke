package duke.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import duke.model.sale.Sale;

import java.util.Date;

/**
 * Jackson-friendly version of {@link Sale}.
 */
public class JsonAdaptedSale {

    private final long id;
    private final String description;
    private final double value;
    private final boolean isSpend;
    private final Date saleDate;
    private final String remarks;

    /**
     * Constructs a {@code JsonAdaptedSale} with the given sale details.
     */
    @JsonCreator
    public JsonAdaptedSale(@JsonProperty("id") long id,
                           @JsonProperty("description")  String description,
                           @JsonProperty("value")  double value,
                           @JsonProperty("isSpend") boolean isSpend,
                           @JsonProperty("saleDate") Date saleDate,
                           @JsonProperty("remarks")  String remarks) {
        this.id = id;
        this.description = description;
        this.saleDate = saleDate;
        this.value = value;
        this.isSpend = isSpend;
        this.remarks = remarks;
    }

    /**
     * Converts a given {@code Sale} into this class for Jackson use.
     */
    public JsonAdaptedSale(Sale source) {
        this.id = source.getId();
        this.remarks = source.getRemarks();
        this.value = source.getValue();
        this.isSpend = source.isSpend();
        this.saleDate = source.getSaleDate();
        this.description = source.getDescription();
    }

    /**
     * Converts this Jackson-friendly adapted sale object into the model's {@code Sale} object.
     */
    public Sale toModelType() {
        return new Sale(id, description, value, isSpend, saleDate, remarks);
    }
}