package storage;

import duke.exception.DukeException;
import executor.command.CommandAddIncomeReceipt;
import executor.command.CommandAddReceipt;
import executor.command.CommandAddSpendingReceipt;
import executor.command.CommandType;
import interpreter.Parser;
import storage.wallet.IncomeReceipt;
import storage.wallet.Receipt;
import storage.wallet.Wallet;
import ui.gui.MainWindow;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Scanner;

public class StorageWallet {
    protected String filePath;

    /**
     * Constrctor for the 'StorageWallet' Class.
     * @param filePath The file path to be used to store and load data
     */
    public StorageWallet(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Method to save the current list of receipts.
     * @param wallet Wallet Object that stores all the Receipts
     */
    public void saveData(Wallet wallet) throws DukeException {
        try {
            FileWriter writer = new FileWriter(this.filePath);
            writer.write(wallet.getBalance().toString() + "\n");

            StringBuilder folderNames = new StringBuilder();
            for (String folderName : wallet.getFolders().keySet()) {
                if (!folderName.equals("Income") && !folderName.equals("Expenses")) {
                    folderNames.append(folderName).append("-");
                }
            }
            writer.write(folderNames.toString() + "\n");
            for (Receipt receipt : wallet.getReceipts()) {
                String strSave = Parser.encodeReceipt(receipt);
                writer.write(strSave);
            }
            writer.close();
        } catch (Exception e) {
            throw new DukeException("Unable to save Wallet Data.\n");
        }
    }

    /**
     * Method to load previously saved list of receipts.
     * @param wallet Wallet Object to be used to house all the Receipts
     */
    public void loadData(Wallet wallet) throws DukeException {
        try {
            File file = new File(this.filePath);
            Scanner scanner = new Scanner(file);
            String storedBalanceStr = scanner.nextLine();
            double storedBalanceDouble = 0.0;
            try {
                storedBalanceDouble = Double.parseDouble(storedBalanceStr);
            } catch (Exception e) {
                throw new DukeException("Balance cannot be read");
            }
            wallet.setBalance(storedBalanceDouble);
            String folderNames = scanner.nextLine();
            while (scanner.hasNextLine()) {
                String loadedInput = scanner.nextLine();
                if (loadedInput.equals("")) {
                    break;
                }
                parseAddReceiptFromStorageString(wallet, loadedInput);
            }
            for (String folderName : folderNames.split("-")) {
                wallet.addFolder(folderName);
            }
        } catch (Exception e) {
            throw new DukeException("No Previously Saved Wallet Data.");
        }
    }

    void loadTestData(Wallet wallet) throws DukeException {
        InputStream testerWalletData = MainWindow.class
                .getResourceAsStream("/testers/testersWallet.txt");
        if (testerWalletData == null) {
            throw new DukeException("No file detected.");
        }
        Scanner s = new Scanner(testerWalletData).useDelimiter("\\A");
        String storedBalanceStr = s.nextLine();
        double storedBalanceDouble = 0.0;
        try {
            storedBalanceDouble = Double.parseDouble(storedBalanceStr);
        } catch (Exception e) {
            throw new DukeException("Balance cannot be read");
        }
        wallet.setBalance(storedBalanceDouble);
        String folderNames = s.nextLine();
        while (s.hasNextLine()) {
            String loadedInput = s.nextLine();
            if (loadedInput.equals("")) {
                break;
            }
            parseAddReceiptFromStorageString(wallet, loadedInput);
        }
        for (String folderName : folderNames.split("-")) {
            wallet.addFolder(folderName);
        }
    }

    /**
     * Converts saved String in StorageWallet to actual Receipt object and saves in Wallet Object.
     * @param wallet Wallet Object for the Receipt to be added in.
     * @param loadedInput The saved String to be converted
     */
    private void parseAddReceiptFromStorageString(Wallet wallet, String loadedInput) {
        CommandType commandtype = Parser.parseForCommandType(loadedInput);
        Receipt r = null;
        if (commandtype == CommandType.OUT) {
            CommandAddReceipt c = new CommandAddSpendingReceipt(loadedInput);
            r = new Receipt(c.getCash(), c.getDate(), c.getTags());
        } else if (commandtype == CommandType.IN) {
            CommandAddIncomeReceipt c = new CommandAddIncomeReceipt(loadedInput);
            r = new IncomeReceipt(c.getCash(), c.getDate(), c.getTags());
        }
        if (r != null) {
            wallet.addReceipt(r);
        }
    }
}
