package dolla.command;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddBillCommandTest {
    private ArrayList<String> nameList = new ArrayList<>();

    @Test
    public void addBillCommandTest1() {
        nameList.add("tata");
        nameList.add("xx");
        Command commandTest = new AddBillCommand("bill", 2, 30, nameList);
        String expected = "bill 2 30.0 [tata, xx]";
        assertEquals(expected, commandTest.getCommandInfo());
    }

    @Test
    public void addBillCommandTest2() {
        nameList.add("aa");
        nameList.add("bb");
        nameList.add("cc");
        Command commandTest = new AddBillCommand("bill", 3, 24, nameList);
        String expected = "bill 3 24.0 [aa, bb, cc]";
        assertEquals(expected, commandTest.getCommandInfo());
    }
}
