package executor.command;

import com.opencsv.CSVWriter;
import duke.exception.DukeException;
import interpreter.Parser;
import storage.StorageManager;
import ui.Receipt;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CommandExport extends Command {

    /**
     * CommandExport helps to export the wallet data as csv with useful headers.
     * @param userInput String is the user entered input
     */
    public CommandExport(String userInput) {
        this.userInput = userInput;
        this.commandType = CommandType.EXPORT;
        this.description = "Exports txt into CSV\n"
                            + "FORMAT : export \n";
    }

    @Override
    public void execute(StorageManager storageManager) {
        String userMessageForEnteringExtraFields = " ";

        if(this.isThereExtraInputByUser(this.userInput)){
            userMessageForEnteringExtraFields = "Incorrect Command but DUKE$$$ understands"
                    + " you would want to export to csv !\n";
        }

        try{
            File csv = new File("data.csv");
            FileWriter outputFile = new FileWriter(csv);
            CSVWriter writer = new CSVWriter(outputFile);
            String[] header = {"ID", "Tag", "Amount", "Date"};
            writer.writeNext(header);
            storageManager.saveAllData();
            int i = 0;
            for(Receipt receipt :storageManager.getWallet().getReceipts()){
                String eachRowOfData = (i + 1) + ". "
                        + receipt.getTags().toString().replaceAll(" ", "") + " "
                        + receipt.getCashSpent() + " "
                        + receipt.getDate();
                convertReceiptsToCsv(eachRowOfData,writer);
                i++;
            }
            writer.close();
            this.infoCapsule.setCodeCli();
            this.infoCapsule.setOutputStr("data.csv has been created ! Please check the project folder \n"
            + userMessageForEnteringExtraFields);
        } catch (DukeException | IOException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr(e.getMessage());
        }
    }


    private void convertReceiptsToCsv(String dataTobeAdded, CSVWriter writer) throws DukeException {
        try{
            String[] entries = dataTobeAdded.split(" ");
            writer.writeNext(entries);
        } catch (Exception e) {
            throw new DukeException("Unable to write to csv");
        }
    }

    private boolean isThereExtraInputByUser(String userInput){
        String additionalEntriesOfUser = Parser.parseForPrimaryInput(this.commandType, userInput);
        return !additionalEntriesOfUser.isEmpty();
    }
}
