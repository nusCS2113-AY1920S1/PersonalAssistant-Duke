package eggventory.logic.commands.add;

import eggventory.commons.enums.CommandType;
import eggventory.commons.exceptions.BadInputException;
import eggventory.model.PersonList;
import eggventory.model.StockList;
import eggventory.model.loans.Person;
import eggventory.storage.Storage;
import eggventory.stubs.StorageStub;
import eggventory.stubs.UiStub;
import eggventory.ui.Ui;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

//@@author cyanoei

class AddLoanCommandTest {
    private StockList list = new StockList();
    private Ui testCli = new UiStub();
    private Storage testStorage = new StorageStub();

    private String stockType = "Uncategorised";
    private String stockCode = "R500";
    private int stockQuantity = 100;
    private int loanQuantity = 10;
    private String matric1 = "A01234";
    private String name1 = "testName";
    private String stockDescription = "desc";

    @BeforeEach
    void resetPersonList() throws BadInputException {
        if (PersonList.getSize() == 0) {
            return;
        }

        ArrayList<Person> persons = PersonList.getPersonList();
        ArrayList<String> matrics = new ArrayList<>();

        for (Person person : persons) {
            matrics.add(person.getMatricNo());
        }

        for (String matric : matrics) {
            PersonList.delete(matric);
        }
    }

    @Test
    void testAddLoan_StockAndPersonExists_ExpectedOutput() throws BadInputException {
        list.addStock(stockType, stockCode, stockQuantity, stockDescription);
        PersonList.add(matric1, name1);

        String output = new AddLoanCommand(CommandType.ADD, matric1, stockCode, 10)
                .execute(list, testCli, testStorage);
        String expected = String.format("Nice, I have added this loan for you: \n"
                + "Person: %s | Stock: %s | Quantity: %d", name1, stockDescription, loanQuantity);
        Assertions.assertEquals(output, expected);
    }

    @Test
    void testAddLoan_PersonDoesNotExist_OutputMessage() throws BadInputException {
        list.addStock(stockType, stockCode, stockQuantity, stockDescription);
        PersonList.add(matric1, name1);

        String output = new AddLoanCommand(CommandType.ADD, "notMatric", stockCode, 10)
                .execute(list, testCli, testStorage);
        String expected = String.format("Sorry, the person with matric number \"%s\" does not exist!", "notMatric");
        Assertions.assertEquals(output, expected);
    }

    @Test
    void testAddLoan_StockDoesNotExist_OutputMessage() throws BadInputException {
        PersonList.add(matric1, name1);

        String output = new AddLoanCommand(CommandType.ADD, matric1, "R20", 10)
                .execute(list, testCli, testStorage);
        String expected = String.format("Sorry, that stock with StockCode \"%s\" does not exist!", "R20");
        Assertions.assertEquals(output, expected);
    }

    @Test
    void testAddLoan_QuantityInsufficient_OutputMessage() throws BadInputException {
        list.addStock(stockType, stockCode, stockQuantity, stockDescription);
        PersonList.add(matric1, name1);

        String output = new AddLoanCommand(CommandType.ADD, matric1, stockCode, 1000)
                .execute(list, testCli, testStorage);
        String expected = "OOPS there is insufficient stock to loan out!";
        Assertions.assertEquals(output, expected);
    }

}