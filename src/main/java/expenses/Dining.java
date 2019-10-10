package expenses;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Dining {
    public LocalDate dateOfPurchase; //yyyy-MM-dd
    public ArrayList<String> diningList;
    public static DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Dining(String date, String purchaseDesccription) {
        dateOfPurchase = LocalDate.parse(date, fmt);
        diningList = new ArrayList<String>();
        diningList.add(purchaseDesccription);
    }
}