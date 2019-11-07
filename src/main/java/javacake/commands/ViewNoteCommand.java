package javacake.commands;

import javacake.Logic;
import javacake.exceptions.CakeException;
import javacake.storage.Storage;
import javacake.storage.StorageManager;
import javacake.ui.Ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ViewNoteCommand extends Command {

    private String fileName;
    private static String defaultFilePath = Storage.returnNotesDefaultFilePath();

    public ViewNoteCommand(String inputCommand) throws CakeException {
        validateCommand(inputCommand);
        type = CmdType.VIEW_NOTE;
        this.fileName = returnFileName(inputCommand);
    }

    private String returnFileName(String inputCommand) {
        String bySpaces = "\\s+";
        String[] parametersInCommand = inputCommand.split(bySpaces);
        return parametersInCommand[1];
    }

    private void validateCommand(String inputCommand) throws CakeException {
        if (doesNotContainFileName(inputCommand)) {
            throw new CakeException("Please input the name of the file you wish to view!");
        } else if (containsMultipleParameter(inputCommand)) {
            throw new CakeException("Please only enter one file name! E.g. viewnote - [name of file]");
        } else if (Command.containsIllegalCharacter(inputCommand)){
            throw new CakeException("Invalid file name: Illegal character in file name detected!");
        } else if (fileDoesNotExist(inputCommand)) {
            throw new CakeException("File specified does not exist! "
            + "Please refer to the notes window to view existing notes file!");
        }
    }

    private boolean fileDoesNotExist(String inputCommand) {
        String bySpaces = "\\s+";
        String[] parametersInCommand = inputCommand.split(bySpaces);
        String fileName = parametersInCommand[1];
        String filePath = defaultFilePath + fileName + ".txt";
        File tempFile = new File(filePath);
        return !tempFile.exists();
    }

    private boolean containsMultipleParameter(String inputCommand) {
        String bySpaces = "\\s+";
        String[] parametersInCommand = inputCommand.split(bySpaces);
        return (parametersInCommand.length > 2);
    }

    private boolean doesNotContainFileName(String inputCommand) {
        String bySpaces = "\\s+";
        String[] parametersInCommand = inputCommand.split(bySpaces);
        return (parametersInCommand.length == 1);
    }

    private String readFromFile() throws CakeException {
        String currentFilePath = defaultFilePath + fileName + ".txt";
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(currentFilePath)));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();
            return sb.toString();
        } catch (IOException e) {
            throw new CakeException(e.getMessage());
        }
    }

    @Override
    public String execute(Logic logic, Ui ui, StorageManager storageManager) throws CakeException {
        return readFromFile();
    }
}
