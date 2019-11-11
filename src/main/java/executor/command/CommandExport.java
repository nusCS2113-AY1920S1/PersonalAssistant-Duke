package executor.command;

import com.opencsv.CSVWriter;
import duke.exception.DukeException;
import interpreter.Parser;
import storage.StorageManager;
import storage.wallet.Receipt;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CommandExport extends Command {

    private String fileName;

    /**
     * CommandExport helps to export the wallet data as csv with useful headers.
     * @param userInput String is the user entered input
     */
    public CommandExport(String userInput) {
        this.userInput = userInput;
        this.commandType = CommandType.EXPORT;
        this.description = "Exports txt into CSV\n"
                            + "FORMAT : export \n";
        this.fileName = "data.csv";
    }

    @Override
    public void execute(StorageManager storageManager) {
        String userMessageForEnteringExtraFields = " ";

        if (this.isThereExtraInputByUser(this.userInput)) {
            userMessageForEnteringExtraFields = "Incorrect Command but DUKE$$$ understands"
                    + " you would want to export Wallet to csv !\n";
        }

        try {
            File csv = new File(getFileName());
            FileWriter outputFile = new FileWriter(csv);
            CSVWriter writer = new CSVWriter(outputFile);
            String[] header = {"ID", "Tag", "Expenditure", "Date"};
            writer.writeNext(header);
            int i = 0;
            for (Receipt receipt :storageManager.getWallet().getReceipts()) {
                String eachRowOfData = (i + 1) + ". "
                        + receipt.getTags().toString().replaceAll(" ", "") + " "
                        + receipt.getCashSpent() + " "
                        + receipt.getDate();
                convertReceiptsToCsv(eachRowOfData,writer);
                i++;
            }
            writer.close();
            this.infoCapsule.setCodeCli();
            this.infoCapsule.setOutputStr(getFileName() + " has been created ! Please check the project folder \n"
                    + userMessageForEnteringExtraFields);
        } catch (DukeException | IOException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr(e.getMessage());
        }
    }

    /**
     * convertReceiptsToCSV is the method used to change receipts into CSV.
     * @param dataTobeAdded String is the row of data which is to be changed to csv
     * @param writer CSVWriter is the library used to write to CSV
     * @throws DukeException Method throws Duke Exception if unable to write to csv
     */
    private void convertReceiptsToCsv(String dataTobeAdded, CSVWriter writer) throws DukeException {
        try {
            String[] entries = dataTobeAdded.split(" ");
            writer.writeNext(entries);
        } catch (Exception e) {
            throw new DukeException("Unable to write to csv");
        }
    }

    /**
     * isThereExtraInputByUser is the method used to check if the user has entered misleading entries.
     * @param userInput String is the user input entered in the GUI
     * @return false is returned if user enters the correct command for exporting into csv
     */
    private boolean isThereExtraInputByUser(String userInput) {
        String additionalEntriesOfUser = Parser.parseForPrimaryInput(this.commandType, userInput);
        return !additionalEntriesOfUser.isEmpty();
    }

    private String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
