package storage;

import ui.Wallet;
import ui.ReceiptTracker;
import ui.Receipt;
import interpreter.Parser;
import executor.command.Executor;
import executor.command.CommandAddIncomeReceipt;
import executor.command.CommandAddSpendingReceipt;
import executor.command.CommandType;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class StorageWallet {
    protected String filePath;

    /**
     * * Constrctor for the 'StorageWallet' Class.
     *
     * @param filePath The file path to be used to store and load data
     */
    public StorageWallet(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Method to save the current list of receipts.
     *
     * @param wallet Wallet class
     */
    public void saveData(Wallet wallet) {
        try {
            FileWriter writer = new FileWriter(this.filePath);
            for (Receipt receipt : wallet.getReceipts()) {
                String strSave = Parser.encodeReceipt(receipt);
                writer.write(strSave);
                //writer.append(strSave);
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Method to load previously saved list of receipts.
     *
     * @return Wallet class
     */
    public Wallet loadData() {
        Wallet wallet = new Wallet();
        try {
            File file = new File(this.filePath);
            Scanner scanner = new Scanner(file);
            Receipt newReceipt;
            while (scanner.hasNextLine()) {
                try {
                    String loadedInput = scanner.nextLine();
                    wallet = parseAddReceiptFromStorageString(wallet, loadedInput);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            System.out.println("No Previously saved wallet Data.");
        }
        return wallet;
    }

    /**
     * Converts saved String in StorageWallet to actual Receipt object and saves in Wallet Object.
     * @param loadedInput The saved String to be converted
     */
    public Wallet parseAddReceiptFromStorageString(Wallet wallet, String loadedInput) {

        CommandType commandtype = Parser.parseForCommandType(loadedInput);
        Executor.runCommand(null, wallet, commandtype, loadedInput);
        return wallet;
    }
}
