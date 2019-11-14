package ui;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import executor.command.CommandExport;
import executor.command.CommandType;
import org.junit.jupiter.api.Test;
import storage.StorageManager;
import storage.wallet.Receipt;

import java.io.FileReader;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandExportTest {

    @Test
    void execute() {
        StorageManager storageManager = new StorageManager();
        Receipt receiptOne = new Receipt(10.0);
        receiptOne.addTag("transport");
        receiptOne.setDate(LocalDate.parse("2019-02-01"));
        storageManager.getWallet().addReceipt(receiptOne);
        int i = 0;
        String data = "";
        //excerpt of method from CommandExport
        for (Receipt receipt :storageManager.getWallet().getReceipts()) {
            data = (i + 1) + ". "
                    + receipt.getTags().toString().replaceAll(" ", "") + " "
                    + receipt.getCashSpent() + " "
                    + receipt.getDate();
            i++;
        }

        data = data.replaceAll(" ", "");
        CommandExport c1 = new CommandExport("export");
        c1.setFileName("testData.csv");
        try {
            c1.execute(storageManager);
        } catch (Exception e) {
            e.getMessage();
        }

        assertEquals(CommandType.EXPORT, c1.getCommandType());
        String csvData = "";

        try {
            // Create an object of file reader
            // class with CSV file as a parameter
            FileReader filereader = new FileReader("testData.csv");
            // create csvReader object and skip first Line
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withSkipLines(1)
                    .build();
            List<String[]> allData = csvReader.readAll();
            // print Data
            for (String[] row : allData) {
                for (String cell : row) {
                    csvData += cell;
                    System.out.print(cell + "\t");
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }

        assertEquals(data,csvData);

        CommandExport c2 = new CommandExport("export hahaha");
        c2.setFileName("testData.csv");
        try {
            c2.execute(storageManager);
            String error1 = "Incorrect Command but DUKE$$$ understands"
                    + " you would want to export Wallet to csv !\n";
            assertTrue(c2.getInfoCapsule().getOutputStr().contains(
                    "testData.csv has been created ! Please check the project folder \n" + error1));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
