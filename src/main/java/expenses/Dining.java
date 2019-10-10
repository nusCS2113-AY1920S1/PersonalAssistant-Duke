package expenses;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Dining extends Expense {
    public LocalDate on ;
    public static DateTimeFormatter fmtD = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");//24h clock

    public Dining(String purchaseDescription, String on) {
        super(purchaseDescription);
        this.on = LocalDate.parse(on, fmtD);
    }

    @Override
    public String toString() {
        return super.purchaseDescription + "bought on" + on.format(fmtD);
    }

}