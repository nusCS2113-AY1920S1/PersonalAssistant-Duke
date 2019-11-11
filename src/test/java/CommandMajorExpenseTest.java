import executor.command.CommandMajorExpense;
import org.junit.jupiter.api.Test;
import storage.StorageManager;
import storage.wallet.IncomeReceipt;
import storage.wallet.Receipt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

public class CommandMajorExpenseTest {
    @Test
    void execute() {
        StorageManager storageManager = new StorageManager();

        Receipt receiptOne = new Receipt(40.0);
        receiptOne.addTag("transport");
        receiptOne.setDate(LocalDate.parse("2019-02-01"));
        storageManager.getWallet().addReceipt(receiptOne);

        Receipt receiptTwo = new Receipt(4.0);
        receiptTwo.addTag("food");
        receiptTwo.setDate(LocalDate.parse("2019-02-02"));
        storageManager.getWallet().addReceipt(receiptTwo);

        Receipt receiptThree = new Receipt(100.0);
        receiptThree.addTag("transport");
        receiptThree.setDate(LocalDate.parse("2019-05-02"));
        storageManager.getWallet().addReceipt(receiptThree);


        CommandMajorExpense m1 = new CommandMajorExpense("majorexpense 40");
        m1.execute(storageManager);
        String output = m1.getInfoCapsule().getOutputStr();
        assertEquals("These are your receipts above/equal to" + " " + "$" + 40 + "\n"
                + "1. [Expenses, transport] $40.00 2019-02-01\n"
                + "2. [Expenses, transport] $100.00 2019-05-02\n", output);

        CommandMajorExpense m2 = new CommandMajorExpense("majorexpense -5.0");
        m2.execute(storageManager);
        String result = m2.getInfoCapsule().getOutputStr();
        assertEquals("Input integer must be positive", result);

        CommandMajorExpense m3 = new CommandMajorExpense("majorexpense");
        m3.execute(storageManager);
        String result1 = m3.getInfoCapsule().getOutputStr();
        assertEquals("These are your receipts above/equal to $" + 100 + "\n"
                + "1. [Expenses, transport] $100.00 2019-05-02\n", result1);

        CommandMajorExpense m4 = new CommandMajorExpense("majorexpense 34df5");
        m4.execute(storageManager);
        String result2 = m4.getInfoCapsule().getOutputStr();
        assertEquals("Invalid cash input. Please enter integer", result2);

    }

    @Test
    void onlyIncomeReceipts() {
        StorageManager storageManager = new StorageManager();

        IncomeReceipt receipt = new IncomeReceipt(23.0);
        receipt.addTag("work");
        receipt.setDate(LocalDate.parse("2019-02-02"));
        storageManager.getWallet().addReceipt(receipt);

        CommandMajorExpense m5 = new CommandMajorExpense("majorexpense 20");
        m5.execute(storageManager);
        String output1 = m5.getInfoCapsule().getOutputStr();
        assertEquals("Unable to get major expenses for this case", output1);
    }

    @Test
    void noReceipts() {
        StorageManager storageManager = new StorageManager();

        CommandMajorExpense m6 = new CommandMajorExpense("majorexpense");
        m6.execute(storageManager);
        String output2 = m6.getInfoCapsule().getOutputStr();
        assertEquals("No major expenses above/equal to $100 was found", output2);

        CommandMajorExpense m7 = new CommandMajorExpense("majorexpense 20");
        m7.execute(storageManager);
        String output3 = m7.getInfoCapsule().getOutputStr();
        assertEquals("Unable to get major expenses for this case", output3);
    }
}