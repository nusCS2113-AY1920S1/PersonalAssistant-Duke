package Money;

import java.util.Date;

public class Installment extends Expenditure {
    private Date endTime;

    public Installment(float price, String description, String category, Date boughtTime) {
        super(price, description, category, boughtTime);
        this.endTime = null;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
