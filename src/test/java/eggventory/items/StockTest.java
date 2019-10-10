package eggventory.items;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StockTest {
    Stock testStock = new Stock("Test", "R0000", 100, "Test Stock");
    OutputStream os = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(os);

    @Test
    void getDescription() {
        assertEquals("Test Stock", testStock.getDescription());
    }

    @Test
    void setDescription() {
        testStock.setDescription("SetDescription() Test");
        assertEquals("SetDescription() Test", testStock.getDescription());
    }

    @Test
    void getQuantity() {
        assertEquals(100,testStock.getQuantity());
    }

    @Test
    void setQuantity() {
        testStock.setQuantity(500);
        assertEquals(500,testStock.getQuantity());
    }

    @Test
    void getLoaned() {
        assertEquals(0,testStock.getLoaned());
    }

    @Test
    void setLoaned() {
        testStock.setLoaned(50);
        assertEquals(50,testStock.getLoaned());
    }

    @Test
    void getLost() {
        assertEquals(0,testStock.getLost());
    }

    @Test
    void setLost() {
        testStock.setLost(50);
        assertEquals(50,testStock.getLost());
    }

    @Test
    void numAvailable() {
        assertEquals(100,testStock.numAvailable());
        testStock.setLoaned(50);
        testStock.setLost(50);
        assertEquals(0,testStock.numAvailable());
    }

    @Test
    void testToString() {
        assertEquals("Test | R0000 | 100 | Test Stock", testStock.toString());
    }

    @Test
    void saveDetailsString() {
        assertEquals("Test/R0000/100/Test Stock/0", testStock.saveDetailsString());
    }

    @Test
    void printAll() {
        System.setOut(ps);
        testStock.printAll();
        assertEquals("Test Stock: 100 available. 0 on loan. 0 lost. (100 total.)\n", os.toString());
    }

    @Test
    void printAvailable() {
        System.setOut(ps);
        testStock.printAvailable();
        assertEquals("Test Stock: 100 available.\n", os.toString());
    }

    @Test
    void printLoan() {
        System.setOut(ps);
        testStock.setLoaned(50);
        testStock.printLoan();
        assertEquals("Test Stock: 50 on loan.\n", os.toString());
    }

    @Test
    void printLost() {
        System.setOut(ps);
        testStock.setLost(50);
        testStock.printLost();
        assertEquals("Test Stock: 50 lost.\n", os.toString());
    }
}