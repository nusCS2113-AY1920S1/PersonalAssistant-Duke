package dolla.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author tatayu
public class BillTest {
    private  ArrayList<String> nameList = new ArrayList<>();

    private Bill createNewBill() {
        nameList.add("tata");
        nameList.add("xx");
        return new Bill("bill", 2, 10, nameList);
    }

    @Test
    public void getType() {
        Bill newBill = createNewBill();
        assertEquals("bill", newBill.getType());
    }

    @Test
    public void getPeople() {
        Bill newBill = createNewBill();
        assertEquals(2, newBill.getPeople());
    }

    @Test
    public void getBillAmount() {
        Bill newBill = createNewBill();
        assertEquals(10.0, newBill.getBillAmount());
    }

    @Test
    public void getNameList() {
        Bill newBill = createNewBill();
        assertEquals(nameList, newBill.getNameList());
    }
}
