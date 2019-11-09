package dolla.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author tatayu
public class BillListTest {

    private ArrayList<String> nameList = new ArrayList<>();

    private Bill createNewBill1() {
        nameList.add("tata");
        nameList.add("xx");
        return new Bill("bill", 2, 10, nameList);
    }

    private Bill createNewBill2() {
        nameList.add("aa");
        nameList.add("bb");
        nameList.add("cc");
        return new Bill("bill", 3, 18, nameList);
    }

    private Bill createNewBill3() {
        nameList.add("aa");
        nameList.add("bb");
        nameList.add("cc");
        nameList.add("dd");
        return new Bill("bill", 4, 18, nameList);
    }

    private BillList newBillList() {
        ArrayList<Record> newRecordList = new ArrayList<Record>();
        newRecordList.add(createNewBill1());
        newRecordList.add(createNewBill2());
        newRecordList.add(createNewBill3());
        return new BillList(newRecordList);
    }

    @Test
    public void sizeOfList() {
        BillList newBillList = newBillList();
        assertEquals(3, newBillList.size());
    }
}
