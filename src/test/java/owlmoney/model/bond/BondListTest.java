package owlmoney.model.bond;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;

import org.junit.jupiter.api.Test;

import owlmoney.model.bond.exception.BondException;
import owlmoney.ui.Ui;

class BondListTest {

    @Test
    void bondListStubListBond_noBond_throwsBondException() {
        BondListStub bondListTest = new BondListStub();
        Ui uiTest = new Ui();
        BondException thrown = assertThrows(BondException.class, () ->
                bondListTest.listBond(30, uiTest),
                "Expected listBond to throw, but it didn't");
        String expectedMessage = "There are no bonds";
        String actualMessage = thrown.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void bondListStubAddBondToList_newBond_success() {
        BondListStub bondListTest = new BondListStub();
        Ui uiTest = new Ui();
        Bond testBond = new Bond("TEST BOND 0", 1000, 1.8,
                new Date("1/3/2019"), 3);
        bondListTest.addBondToList(testBond, uiTest);
        assertEquals(1, bondListTest.getSize());
        Bond testBondTwo = new Bond("TEST BOND 1", 1000, 1.8,
                new Date("1/3/2019"), 3);
        bondListTest.addBondToList(testBondTwo, uiTest);
        assertEquals(2, bondListTest.getSize());
    }

    @Test
    void bondListStubBondExist_newBondConflictExistingBond_throwsBondException() {
        BondListStub bondListTest = new BondListStub();
        Ui uiTest = new Ui();
        Bond testBond = new Bond("TEST BOND 0", 1000, 1.8,
                new Date("1/3/2019"), 3);
        bondListTest.addBondToList(testBond, uiTest);
        assertEquals(1, bondListTest.getSize());
        Bond testBondTwo = new Bond("TEST BOND 0", 1000, 1.8,
                new Date("1/3/2019"), 3);
        BondException thrown = assertThrows(BondException.class, () ->
                bondListTest.bondExist(testBondTwo), "Expected bondExist to throw, but it didn't");
        String expectedMessage = "Bond with the name: TEST BOND 0 already exists";
        String actualMessage = thrown.getMessage();
        assertEquals(expectedMessage, actualMessage);
        assertEquals(1, bondListTest.getSize());
    }

    @Test
    void bondListStubRemoveBondFromList_noBond_throwsBondException() {
        BondListStub bondListTest = new BondListStub();
        Ui uiTest = new Ui();
        assertEquals(0, bondListTest.getSize());
        BondException thrown = assertThrows(BondException.class, () ->
                        bondListTest.removeBondFromList("TEST BOND 0", uiTest),
                "Expected removeBondFromList to throw, but it didn't");
        String expectedMessage = "There are no bonds";
        String actualMessage = thrown.getMessage();
        assertEquals(expectedMessage, actualMessage);
        Bond testBond = new Bond("TEST BOND 0", 1000, 1.8,
                new Date("1/3/2019"), 3);
        bondListTest.addBondToList(testBond, uiTest);
        assertEquals(1, bondListTest.getSize());
    }

    @Test
    void bondListStubRemoveBondFromList_BondPresent_success() throws BondException {
        BondListStub bondListTest = new BondListStub();
        Ui uiTest = new Ui();
        assertEquals(0, bondListTest.getSize());
        Bond testBond = new Bond("TEST BOND 0", 1000, 1.8,
                new Date("1/3/2019"), 3);
        bondListTest.addBondToList(testBond, uiTest);
        assertEquals(1, bondListTest.getSize());
        bondListTest.removeBondFromList("TEST BOND 0", uiTest);
        assertEquals(0, bondListTest.getSize());
    }

    @Test
    void bondListStubGetBond_noBond_throwsBondException() {
        BondListStub bondListTest = new BondListStub();
        Ui uiTest = new Ui();
        assertEquals(0, bondListTest.getSize());
        BondException thrown = assertThrows(BondException.class, () ->
                bondListTest.getBond("TEST BOND 0"),
                "Expected getBond to throw, but it didn't");
        String expectedMessage = "There are no bonds with the name: TEST BOND 0";
        String actualMessage = thrown.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void bondListStubGetBond_bondPresent_success() throws BondException {
        BondListStub bondListTest = new BondListStub();
        Ui uiTest = new Ui();
        assertEquals(0, bondListTest.getSize());
        Bond testBond = new Bond("TEST BOND 0", 1000, 1.8,
                new Date("1/3/2019"), 3);
        bondListTest.addBondToList(testBond,uiTest);
        String expectedMessage = "TEST BOND 0";
        String actualMessage = bondListTest.getBond("TEST BOND 0").getName();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void bondListStubEditBond_noBond_throwsBondException() {
        BondListStub bondListTest = new BondListStub();
        Ui uiTest = new Ui();
        assertEquals(0, bondListTest.getSize());
        BondException thrown = assertThrows(BondException.class, () ->
                        bondListTest.getBond("TEST BOND 0"),
                "Expected editBond to throw, but it didn't");
        String expectedMessage = "There are no bonds with the name: TEST BOND 0";
        String actualMessage = thrown.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void bondListStubEditBond_bondPresent_success() throws BondException {
        BondListStub bondListTest = new BondListStub();
        Ui uiTest = new Ui();
        assertEquals(0, bondListTest.getSize());
        Bond testBond = new Bond("TEST BOND 0", 1000, 1.8,
                new Date("1/3/2019"), 3);
        bondListTest.addBondToList(testBond,uiTest);
        bondListTest.editBond("TEST BOND 0", "5","2.00",uiTest);
        double actualRate = bondListTest.getBond("TEST BOND 0").getYearlyCouponRate();
        double expectedRate = 2.0;
        assertEquals(expectedRate,actualRate);
        int actualYear = bondListTest.getBond("TEST BOND 0").getYear();
        int expectedYear = bondListTest.getBond("TEST BOND 0").getYear();
        assertEquals(expectedYear,actualYear);
    }

    @Test
    void listBond_noBond_throwsBondException() {
        BondList bondListTest = new BondList();
        Ui uiTest = new Ui();
        BondException thrown = assertThrows(BondException.class, () ->
                bondListTest.listBond(30, uiTest),
                "Expected listBond to throw, but it didn't");
        String expectedMessage = "There are no bonds";
        String actualMessage = thrown.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void listBond_BondListFilled_success() throws BondException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        BondList bondListTest = new BondList();
        Ui uiTest = new Ui();
        Bond testBond = new Bond("TEST BOND 0", 1000, 1.8,
                new Date("1/3/2019"), 3);
        bondListTest.addBondToList(testBond, uiTest);
        assertEquals(1, bondListTest.getSize());
        String expectedOutput = "Bond with the following details has been added: \r\n"
                + "Item No.             Bond Name                      Amount          Rate       "
                + "Date of Purchased    "
                + "Number of Years \r\n"
                + "-------------------------------------------------------------------------------"
                + "--------------------------------------------------\r\n"
                + "1                    TEST BOND 0                    $1000.00        1.80       "
                + "03 January 2019      "
                + "3          \r\n"
                + "-------------------------------------------------------------------------------"
                + "--------------------------------------------------\r\n";
        assertEquals(expectedOutput,outContent.toString());
        bondListTest.listBond(30,uiTest);
    }

    @Test
    void addBondToList_newBond_success() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        BondList bondListTest = new BondList();
        Ui uiTest = new Ui();
        Bond testBond = new Bond("TEST BOND 0", 1000, 1.8,
                new Date("1/3/2019"), 3);
        bondListTest.addBondToList(testBond, uiTest);
        assertEquals(1, bondListTest.getSize());
        Bond testBondTwo = new Bond("TEST BOND 1", 1000, 1.8,
                new Date("1/3/2019"), 3);
        bondListTest.addBondToList(testBondTwo, uiTest);
        assertEquals(2, bondListTest.getSize());
        String expectedOutput = "Bond with the following details has been added: \r\n" +
                "Item No.             Bond Name                      Amount          Rate       "
                + "Date of Purchased    "
                + "Number of Years \r\n"
                + "-------------------------------------------------------------------------------"
                + "--------------------------------------------------\r\n"
                + "1                    TEST BOND 0                    $1000.00        1.80       "
                + "03 January 2019      "
                + "3          \r\n"
                + "-------------------------------------------------------------------------------"
                + "--------------------------------------------------\r\n"
                + "Bond with the following details has been added: \r\n"
                + "Item No.             Bond Name                      Amount          Rate       "
                + "Date of Purchased    "
                + "Number of Years \r\n"
                + "-------------------------------------------------------------------------------"
                + "--------------------------------------------------\r\n"
                + "1                    TEST BOND 1                    $1000.00        1.80       "
                + "03 January 2019      "
                + "3          \r\n"
                + "-------------------------------------------------------------------------------"
                + "--------------------------------------------------\r\n";
        assertEquals(expectedOutput,outContent.toString());
    }

    @Test
    void bondExist_newBondConflictExistingBond_throwsBondException() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        BondList bondListTest = new BondList();
        Ui uiTest = new Ui();
        Bond testBond = new Bond("TEST BOND 0", 1000, 1.8,
                new Date("1/3/2019"), 3);
        bondListTest.addBondToList(testBond, uiTest);
        assertEquals(1, bondListTest.getSize());
        Bond testBondTwo = new Bond("TEST BOND 0", 1000, 1.8,
                new Date("1/3/2019"), 3);
        BondException thrown = assertThrows(BondException.class, () ->
                bondListTest.bondExist(testBondTwo), "Expected bondExist to throw, but it didn't");
        String expectedMessage = "Bond with the name: TEST BOND 0 already exists";
        String actualMessage = thrown.getMessage();
        assertEquals(expectedMessage, actualMessage);
        assertEquals(1, bondListTest.getSize());
        String expectedOutput = "Bond with the following details has been added: \r\n" +
                "Item No.             Bond Name                      Amount          Rate       "
                + "Date of Purchased    "
                + "Number of Years \r\n"
                + "-------------------------------------------------------------------------------"
                + "--------------------------------------------------\r\n"
                + "1                    TEST BOND 0                    $1000.00        1.80       "
                + "03 January 2019      "
                + "3          \r\n"
                + "-------------------------------------------------------------------------------"
                + "--------------------------------------------------\r\n";
        assertEquals(expectedOutput,outContent.toString());
    }

    @Test
    void bondExist_bondDoesNotExist_success() throws BondException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        BondList bondListTest = new BondList();
        Ui uiTest = new Ui();
        Bond testBond = new Bond("TEST BOND 0", 1000, 1.8,
                new Date("1/3/2019"), 3);
        bondListTest.addBondToList(testBond, uiTest);
        assertEquals(1, bondListTest.getSize());
        Bond testBondTwo = new Bond("TEST BOND 1", 1000, 1.8,
                new Date("1/3/2019"), 3);
        bondListTest.bondExist(testBondTwo);
        String expectedOutput = "Bond with the following details has been added: \r\n" +
                "Item No.             Bond Name                      Amount          Rate       "
                + "Date of Purchased    "
                + "Number of Years \r\n"
                + "-------------------------------------------------------------------------------"
                + "--------------------------------------------------\r\n"
                + "1                    TEST BOND 0                    $1000.00        1.80       "
                + "03 January 2019      "
                + "3          \r\n"
                + "-------------------------------------------------------------------------------"
                + "--------------------------------------------------\r\n";
        assertEquals(expectedOutput,outContent.toString());
    }

    @Test
    void removeBondFromList_noBond_throwsBondException() {
        BondList bondListTest = new BondList();
        Ui uiTest = new Ui();
        assertEquals(0, bondListTest.getSize());
        BondException thrown = assertThrows(BondException.class, () ->
                        bondListTest.removeBondFromList("TEST BOND 0", uiTest),
                "Expected removeBondFromList to throw, but it didn't");
        String expectedMessage = "There are no bonds";
        String actualMessage = thrown.getMessage();
        assertEquals(expectedMessage, actualMessage);
        Bond testBond = new Bond("TEST BOND 0", 1000, 1.8,
                new Date("1/3/2019"), 3);
        bondListTest.addBondToList(testBond, uiTest);
        assertEquals(1, bondListTest.getSize());
    }

    @Test
    void removeBondFromList_noBondAndBondListFilled_throwsBondException() {
        BondList bondListTest = new BondList();
        Ui uiTest = new Ui();
        assertEquals(0, bondListTest.getSize());
        Bond testBond = new Bond("TEST BOND 1", 1000, 1.8,
                new Date("1/3/2019"), 3);
        bondListTest.addBondToList(testBond, uiTest);
        assertEquals(1, bondListTest.getSize());
        BondException thrown = assertThrows(BondException.class, () ->
                        bondListTest.removeBondFromList("TEST BOND 0", uiTest),
                "Expected removeBondFromList to throw, but it didn't");
        String expectedMessage = "There are no bonds with the name: TEST BOND 0";
        String actualMessage = thrown.getMessage();
        assertEquals(expectedMessage, actualMessage);

    }

    @Test
    void removeBondFromList_BondPresent_success() throws BondException {
        BondList bondListTest = new BondList();
        Ui uiTest = new Ui();
        assertEquals(0, bondListTest.getSize());
        Bond testBond = new Bond("TEST BOND 0", 1000, 1.8,
                new Date("1/3/2019"), 3);
        bondListTest.addBondToList(testBond, uiTest);
        assertEquals(1, bondListTest.getSize());
        bondListTest.removeBondFromList("TEST BOND 0", uiTest);
        assertEquals(0, bondListTest.getSize());
    }

    @Test
    void getBond_noBond_throwsBondException() {
        BondList bondListTest = new BondList();
        Ui uiTest = new Ui();
        assertEquals(0, bondListTest.getSize());
        BondException thrown = assertThrows(BondException.class, () ->
                        bondListTest.getBond("TEST BOND 0"),
                "Expected getBond to throw, but it didn't");
        String expectedMessage = "There are no bonds with the name: TEST BOND 0";
        String actualMessage = thrown.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void getBond_bondPresent_success() throws BondException {
        BondList bondListTest = new BondList();
        Ui uiTest = new Ui();
        assertEquals(0, bondListTest.getSize());
        Bond testBond = new Bond("TEST BOND 0", 1000, 1.8,
                new Date("1/3/2019"), 3);
        bondListTest.addBondToList(testBond,uiTest);
        String expectedMessage = "TEST BOND 0";
        String actualMessage = bondListTest.getBond("TEST BOND 0").getName();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void editBond_noBondAndBondListEmpty_throwsBondException() {
        BondList bondListTest = new BondList();
        Ui uiTest = new Ui();
        assertEquals(0, bondListTest.getSize());
        BondException thrown = assertThrows(BondException.class, () ->
                        bondListTest.getBond("TEST BOND 0"),
                "Expected editBond to throw, but it didn't");
        String expectedMessage = "There are no bonds with the name: TEST BOND 0";
        String actualMessage = thrown.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void editBond_noBondAndBondListFilled_throwsBondException() {
        BondList bondListTest = new BondList();
        Ui uiTest = new Ui();
        assertEquals(0, bondListTest.getSize());
        Bond testBond = new Bond("TEST BOND 0", 1000, 1.8,
                new Date("1/3/2019"), 3);
        bondListTest.addBondToList(testBond,uiTest);
        BondException thrown = assertThrows(BondException.class, () ->
                        bondListTest.editBond("TEST BOND 1","9","3.14",uiTest),
                "Expected editBond to throw, but it didn't");
        String expectedMessage = "There are no bonds with the name: TEST BOND 1";
        String actualMessage = thrown.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void editBond_bondPresent_success() throws BondException {
        BondList bondListTest = new BondList();
        Ui uiTest = new Ui();
        assertEquals(0, bondListTest.getSize());
        Bond testBond = new Bond("TEST BOND 0", 1000, 1.8,
                new Date("1/3/2019"), 3);
        bondListTest.addBondToList(testBond,uiTest);
        bondListTest.editBond("TEST BOND 0", "5","2.00",uiTest);
        double actualRate = bondListTest.getBond("TEST BOND 0").getYearlyCouponRate();
        double expectedRate = 2.0;
        assertEquals(expectedRate,actualRate);
        int actualYear = bondListTest.getBond("TEST BOND 0").getYear();
        int expectedYear = bondListTest.getBond("TEST BOND 0").getYear();
        assertEquals(expectedYear,actualYear);
    }

    @Test
    void editBond_smallerYearThanOriginal_throwsBondException() {
        BondList bondListTest = new BondList();
        Ui uiTest = new Ui();
        assertEquals(0, bondListTest.getSize());
        Bond testBond = new Bond("TEST BOND 0", 1000, 1.8,
                new Date("1/3/2019"), 3);
        bondListTest.addBondToList(testBond,uiTest);
        BondException thrown = assertThrows(BondException.class, () ->
                        bondListTest.editBond("TEST BOND 0","1","3.14",uiTest),
                "Expected editBond to throw, but it didn't");
        String expectedMessage = "The year can only be larger than: 3";
        String actualMessage = thrown.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }
}
