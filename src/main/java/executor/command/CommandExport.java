package executor.command;

import com.opencsv.CSVWriter;
import duke.exception.DukeException;
import interpreter.Parser;
import storage.StorageManager;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class CommandExport extends Command {

    // filepath of either taskData or WalletData
    private String filePath;

    public CommandExport(String userInput) {
        this.userInput = userInput;
        this.commandType = CommandType.EXPORT;
        this.description = "Exports txt into CSV\n"
                            + "FORMAT : export <wallet or task>\n";
    }

    @Override
    public void execute(StorageManager storageManager) {
        try{
            String fileUserWants = getWhichFileUserWants(this.userInput);
            convertTxtToCsv(fileUserWants);
            this.infoCapsule.setCodeCli();
            this.infoCapsule.setOutputStr("data.csv has been created ! Please check the project folder \n");
        } catch (DukeException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr(e.getMessage());
        }
    }

    private String getWhichFileUserWants (String userInput) throws DukeException {
        try {
            return Parser.parseForPrimaryInput(this.commandType, userInput);
        } catch (Exception e) {
            throw new DukeException("Please enter a valid choice : either task or wallet\n");
        }
    }

    private void convertTxtToCsv(String dataWanted) throws DukeException {
        if(dataWanted.toLowerCase().equals("wallet")){
            this.filePath = "testWalletDataSave.txt";
        } else {
            this.filePath = "savedTask.txt";
        }
        try{
            // access the file for which user wants the csv
            File txtFile = new File(this.filePath);
            Scanner scanner = new Scanner(txtFile);

            //Now create the csv
            // first create file object for file placed at location
            // specified by filepath
            File csv = new File("data.csv");
            // create FileWriter object with file as parameter
            FileWriter outputFile = new FileWriter(csv);
            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputFile);
            while (scanner.hasNextLine()) {
                String loadedInput = scanner.nextLine();
                if (loadedInput.equals("")) {
                    break;
                }
                String[] eachRowOfData = {loadedInput.replace(" ", ",")};
                writer.writeNext(eachRowOfData);
            }
            writer.close();
        } catch (Exception e) {
            throw new DukeException("Please enter a choice either : export <wallet> or <task>");
        }
    }
}
