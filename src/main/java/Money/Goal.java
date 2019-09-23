package Money;

import java.util.Date;

public class Goal extends Expenditure {
    private enum priority {
        LOW,
        MEDIUM,
        HIGH
    }

    public Goal(float price, String description, String category, Date boughtTime) {
        super(price, description, category, boughtTime);
    }

}
