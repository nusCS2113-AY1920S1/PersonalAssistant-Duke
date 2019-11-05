package storage;

import duke.exception.DukeException;
import executor.command.CommandAddReceipt;
import executor.command.CommandAddSpendingReceipt;
import executor.command.CommandType;
import ui.IncomeReceipt;
import ui.Wallet;
import ui.Receipt;
import interpreter.Parser;
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
    public Wallet loadData() throws DukeException {
        Wallet wallet = new Wallet();
        try {
            File file = new File(this.filePath);
            Scanner scanner = new Scanner(file);
            Receipt newReceipt;
            while (scanner.hasNextLine()) {
                String loadedInput = scanner.nextLine();
                if (loadedInput.equals("")) {
                    break;
                }
                wallet = parseAddReceiptFromStorageString(wallet, loadedInput);
            }
        } catch (Exception e) {
            throw new DukeException("No Previously Saved Wallet Data.");
        }
        return wallet;
    }

    /**
     * Converts saved String in StorageWallet to actual Receipt object and saves in Wallet Object.
     * @param loadedInput The saved String to be converted
     */
    private Wallet parseAddReceiptFromStorageString(Wallet wallet, String loadedInput) {
        CommandType commandtype = Parser.parseForCommandType(loadedInput);
        Receipt r = null;
        if (commandtype == CommandType.OUT) {
            CommandAddReceipt c = new CommandAddSpendingReceipt(loadedInput);
            r = new Receipt(c.getCash(), c.getDate(), c.getTags());
        } else if (commandtype == CommandType.IN) {
            CommandAddReceipt c = new CommandAddSpendingReceipt(loadedInput);
            r = new IncomeReceipt(c.getCash(), c.getDate(), c.getTags());
        }
        if (r != null) {
            wallet.addReceipt(r);
        }
        return wallet;
    }
}
