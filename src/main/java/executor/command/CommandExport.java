package executor.command;

import com.opencsv.CSVWriter;
import duke.exception.DukeException;
import storage.StorageManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CommandExport extends Command {

    public CommandExport(String userInput) {
        this.userInput = userInput;
        this.commandType = CommandType.EXPORT;
        this.description = "Exports txt into CSV\n"
                            + "FORMAT : export \n";
    }

    @Override
    public void execute(StorageManager storageManager) {
        try{
            File csv = new File("data.csv");
            // create FileWriter object with file as parameter
            FileWriter outputFile = new FileWriter(csv);
            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputFile);
            String[] header = {"ID", "Tag", "Amount", "Date"};
            writer.writeNext(header);
            storageManager.saveAllData();
            String receipts = storageManager.getWallet().getReceipts().getPrintableReceipts();
            String[] AllRowOfData  = receipts.split("\n");
            for( String row : AllRowOfData) {
                convertReceiptsToCsv(row, writer);
            }
            writer.close();
            this.infoCapsule.setCodeCli();
            this.infoCapsule.setOutputStr("data.csv has been created ! Please check the project folder \n");
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
}
