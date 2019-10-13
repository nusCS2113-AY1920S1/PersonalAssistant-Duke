package moomoo.task;

import moomoo.command.BudgetCommand;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StorageTest {
    @Test
    public void testFileLoad() throws MooMooException, IOException {
        File tempFile = File.createTempFile("moomoo", ".txt");
        tempFile.deleteOnExit();

        TransactionList newTransList = new TransactionList();
        CategoryList newCatList = new CategoryList();
        Budget newBudget = new Budget();
        Ui newUi = new Ui();
        Storage newStorage = new Storage(tempFile.getPath());

        BudgetCommand budgetCommand = new BudgetCommand(false, "budget set c/sweets b/500 c/laptop b/1500");
        budgetCommand.execute(newBudget, newCatList, newTransList, newUi, newStorage);

        HashMap<String, Double> newHashMap = newStorage.loadBudget();
        assertEquals(500, newHashMap.get("sweets"));
        assertEquals(1500, newHashMap.get("laptop"));
    }
}