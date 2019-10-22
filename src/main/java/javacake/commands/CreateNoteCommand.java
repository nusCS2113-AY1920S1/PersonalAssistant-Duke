package javacake.commands;

import javacake.ProgressStack;
import javacake.exceptions.DukeException;
import javacake.storage.Profile;
import javacake.storage.Storage;
import javacake.ui.Ui;

import java.io.File;
import java.io.IOException;

public class CreateNoteCommand extends Command {

    private String defaultFileName = "Notes";
    private static int defaultFileNameCounter = 1;
    private String defaultDirectoryPath = "src/main/resources/notes/";
    private static String userGivenFileName = "Notes";

    public CreateNoteCommand(String inputCommand) throws DukeException{
        type = CmdType.CREATENOTE;
        String[] wordsInInputCommand = inputCommand.split("\\s+");
        if (createNoteCommandHasSpecifiedFileName(inputCommand)) {
            userGivenFileName = wordsInInputCommand[1];
        } else if (validCommandWithNoSpecifiedFileName(inputCommand)) {
            while (checkNotesFileExist(defaultDirectoryPath + userGivenFileName + ".txt")) {
                generateNewDefaultFileName();
            }
        } else {
            throw new DukeException("Invalid command: To write notes, type 'createnote' followed by desired (optional) filename.");
        }
    }

    private boolean createNoteCommandHasSpecifiedFileName(String inputCommand) {
        String[] wordsInInputCommand = inputCommand.split("\\s+");
        if (wordsInInputCommand.length == 2 && wordsInInputCommand[0].equals("createnote")) {
            return true;
        }
        return false;
    }

    private boolean validCommandWithNoSpecifiedFileName(String inputCommand) {
        String[] wordsInInputCommand = inputCommand.split("\\s+");
        if (wordsInInputCommand.length == 1 && wordsInInputCommand[0].equals("createnote")) {
            return true;
        }
        return false;
    }


    private void generateNewDefaultFileName() {
        userGivenFileName = defaultFileName + defaultFileNameCounter;
        defaultFileNameCounter++;
    }


    public String execute(ProgressStack progressStack, Ui ui, Storage storage, Profile profile) throws DukeException {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(userGivenFileName).append(".txt");
            String formattedFileName = sb.toString();
            sb.insert(0, defaultDirectoryPath);
            String newFilePath = sb.toString();
            //System.out.println(newFilePath);
            if (checkNotesFileExist(newFilePath)) {
                throw new DukeException("File already exists, please type 'editnote " + formattedFileName + "' to edit the file instead");
            } else  {
                File file = new File(newFilePath);
                if (!file.createNewFile()) {
                    throw new DukeException("File '" + formattedFileName + "' was not created! Pls try again!");
                }
                return "File '" + formattedFileName + "'has been created successfully!\n";
            }
        } catch (IOException e) {
            throw new DukeException(e.getMessage());
        }

    }

    private boolean checkNotesFileExist(String filePath) {
        File tempFile = new File(filePath);
        return tempFile.exists();
    }
}
