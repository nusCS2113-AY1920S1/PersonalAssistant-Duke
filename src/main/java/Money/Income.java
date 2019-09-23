package Money;

import java.util.Date;

public class Income extends Item {
    private Date payday;

    public Income(float price, String description, Date payday) {
        super(price, description);
        this.payday = payday;
    }

    public Date getPayday() {
        return payday;
    }
}
