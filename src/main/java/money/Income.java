package money;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Income extends Item {
    private Date payday;
    private SimpleDateFormat simpleDateFormat;

    public Income(float price, String description, Date payday) {
        super(price, description);
        this.payday = payday;
        simpleDateFormat  = new SimpleDateFormat("d/M/yyyy");
    }

    public Date getPayday() {
        return payday;
    }

    @Override
    public String toString() {
        return "[I]" + " " + super.getDescription() + "(salary: $" + super.getPrice() + ") (Paid On: " +
                getPaidTime() + ")";
    }

    public String getPaidTime() {
        return simpleDateFormat.format(payday);
    }
}
