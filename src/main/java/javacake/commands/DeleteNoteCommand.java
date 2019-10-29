package javacake.commands;

import javacake.Logic;
import javacake.exceptions.DukeException;
import javacake.storage.StorageManager;
import javacake.ui.Ui;

import java.io.File;

public class DeleteNoteCommand extends Command {

    private static String fileName;
    private static String fullFilePath;

    /**
     * Constructor for DeleteNoteCommand.
     * Command used to delete a specific existing notes.
     * @param inputCommand Command input from the user.
     * @throws DukeException If user does not input parameter.
     */
    public DeleteNoteCommand(String inputCommand) throws DukeException {
        try {
            String[] parametersInCommand = inputCommand.split("\\s+");
            String inputFileName = parametersInCommand[1];
            processFile(inputFileName);
            type = CmdType.DELETENOTE;
        } catch (NullPointerException e) {
            throw new DukeException(e.getMessage());
        }
    }

    /**
     * Executes the deleting process.
     * Verifies if the file specified by the user has been deleted.
     * @param logic TaskList containing current tasks
     * @param ui the Ui responsible for outputting messages
     * @param storageManager storage container
     * @return Notification messages depending delete outcome.
     * @throws DukeException If file does not exist.
     */
    @Override
    public String execute(Logic logic, Ui ui, StorageManager storageManager) throws DukeException {
        try {
            File tempFile = new File(fullFilePath);
            if (tempFile.delete()) {
                return fileName + ".txt has been deleted succesfully!";
            } else {
                return "Unable to delete " + fileName + ".txt";
            }
        } catch (Exception e) {
            throw new DukeException(e.getMessage());
        }
    }

    /**
     * Method used for verification process.
     * Checks for illegal characters and if file exists.
     * Assigns inputFileName to fileName to prepare for deletion.
     * @param inputFileName Name of file user wants to delete.
     * @throws DukeException If illegal character or invalid file name detected.
     */
    private void processFile(String inputFileName) throws DukeException {
        if (containsIllegalCharacters(inputFileName)) {
            throw new DukeException("Invalid file name: Illegal character in file name detected!");
        } else if (fileDoesNotExist(inputFileName)) {
            throw new DukeException("Invalid file name: No such file!");
        } else {
            fileName = inputFileName;
        }
    }

    /**
     * Method to generate full file path to the file to be deleted.
     * Concatenates the desired file name with the default path to notes.
     * @param inputFileName name of file user wants to delete.
     * @return Full file path to the file to be deleted.
     */
    private String processFilePath(String inputFileName) {
        String filePath = "data/notes/";
        fullFilePath = filePath + inputFileName + ".txt" + "/";
        return fullFilePath;
    }

    /**
     * Checks for illegal characters in file name given by users.
     * Ensures no illegal access to other files in other directories.
     * @param inputFileName name of file user wants to delete.
     * @return true if inputFileName contains illegal characters.
     */
    private boolean containsIllegalCharacters(String inputFileName) {
        return CreateNoteCommand.containsIllegals(inputFileName);
    }

    /**
     * Checks if file exists in the note storage.
     * @param inputFileName name of file user wants to delete.
     * @return True if file does not exist.
     */
    private boolean fileDoesNotExist(String inputFileName) {
        String fullFilePath = processFilePath(inputFileName);
        File tempFile = new File(fullFilePath);
        return (!tempFile.exists());
    }

}
