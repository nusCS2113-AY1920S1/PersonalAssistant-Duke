package javacake.commands;

import javacake.JavaCake;
import javacake.Logic;
import javacake.exceptions.CakeException;
import javacake.storage.Storage;
import javacake.storage.StorageManager;
import javacake.ui.Ui;
import javacake.utilities.IFileUtilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;

public class EditNoteCommand extends Command implements IFileUtilities {

    private static String defaultDirectoryPath = "data/notes/";

    private static String nameOfEditFile;
    private static String currentFilePath;

    private static String headingMessage = "Write your notes below!\n"
            + "To save edited content, type '/save' and enter!\n";

    private static String endingMessage = "has been saved!\n";

    /**
     * Constructor for EditNoteCommand.
     * Checks if command has two parameters - "editnote" and "name of file to be edited".
     * Checks if "name of file to be edited" exists.
     * If former checks passed, update nameOfEditFile and currentFilePath variables.
     * Else inform user to either provide valid file name or valid EditNoteCommand.
     * @param inputCommand Input command from the user.
     * @throws CakeException if invalid command or invalid file name.
     */
    public EditNoteCommand(String inputCommand) throws CakeException {
        JavaCake.logger.log(Level.INFO, "Processing EditNoteCommand: " + inputCommand);
        type = CmdType.EDIT_NOTE;
        updateDefaultDirectoryPath();
        verifyCommand(inputCommand);
    }

    /**
     * Updates default directory path according the storage.
     */
    private void updateDefaultDirectoryPath() {
        defaultDirectoryPath = Storage.returnNotesDefaultFilePath();
    }

    /**
     * Verifies if command contains parameter.
     * @param inputCommand Editnote command from user.
     * @throws CakeException If user did not specify file name.
     */
    private void verifyCommand(String inputCommand) throws CakeException {

        String bySpaces = "\\s+";
        String[] wordsInInputCommand = inputCommand.split(bySpaces);

        if (wordsInInputCommand.length == 2) {
            String fileName = wordsInInputCommand[1];
            checkIfFileExist(fileName);
        } else {
            JavaCake.logger.log(Level.INFO, inputCommand + " invalid EditNoteCommand.");
            throw new CakeException("Pls enter a valid editnote command:"
                    + " 'editnote - [name of the file you wish you edit]'");
        }
    }

    /**
     * Checks if file exist.
     * Updates current file path and nameOfEditFile if file exists.
     * @param fileName Name of the file to be edited.
     * @throws CakeException If input file name contains illegal characters.
     */
    private void checkIfFileExist(String fileName) throws CakeException {
        if (fileExist(fileName)) {
            nameOfEditFile = IFileUtilities.returnOriginalFileName(defaultDirectoryPath, fileName);
            createCurrentFilePath();
        } else {
            JavaCake.logger.log(Level.INFO, fileName + " contains illegal file name.");
            throw new CakeException("Pls enter a valid file name! Type 'listnote' to view available notes!");
        }
    }

    /**
     * Checks if name of the file exists in directory path.
     * @param fileName Name of the file to be checked and edited.
     * @return True if the file exists in directory path, false otherwise.
     */
    private boolean fileExist(String fileName) {
        File file = new File(defaultDirectoryPath + fileName + ".txt");
        JavaCake.logger.log(Level.INFO, "Checking if file: " + fileName + " exist.");
        if (file.exists()) {
            JavaCake.logger.log(Level.INFO, fileName + " exist.");
            return true;
        }
        JavaCake.logger.log(Level.INFO, fileName + " does not exist.");
        return false;
    }

    /**
     * Updates currentFilePath with file path to the file to be edited.
     */
    private void createCurrentFilePath() {
        currentFilePath = defaultDirectoryPath + nameOfEditFile + ".txt";
    }

    /**
     * Checks if file in currentFilePath is empty by checking its size.
     * @param currentFilePath file path to the current file to be edited.
     * @return true if the file is empty, false otherwise.
     */
    private static boolean checkFileIsEmpty(String currentFilePath) {
        File file = new File(currentFilePath);
        return file.length() == 0;
    }

    /**
     * Read content stored in text file to be edited.
     * @return String of the content stored in the text file.
     * @throws CakeException if the file does not exist.
     */
    private String displayContentInFile() throws CakeException {
        return IFileUtilities.readFile(currentFilePath);
    }

    /**
     * For CLI only.
     * Read input from user and write into specified note file.
     * If '/save' is called, file will be saved.
     * @throws CakeException File does not exist.
     */
    private void readAndSaveNewContent() throws CakeException {
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(new File(currentFilePath)));
            writeNewLine(bw);
            bw.close();
        } catch (IOException e) {
            throw new CakeException(e.getMessage());
        }
    }

    /**
     * Writes new line into edit file.
     * @param bw BufferedWriter to write new line into edit file.
     * @throws CakeException If file does not exist.
     */
    private void writeNewLine(BufferedWriter bw) throws CakeException {
        try {
            Ui ui = new Ui();
            String lineRead;
            while (!(lineRead = ui.readCommand()).equals("/save")) {
                bw.write(lineRead);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new CakeException(e.getMessage());
        }
    }

    /**
     * Executes the EditNoteCommand accordingly depends on CLI or GUI.
     * If CLI, use ui and readAndSaveNewContent method to generate message for user.
     * If GUI, return !@#_EDIT_NOTE to notify MainWindow class to call GUI methods.
     * @param logic tracks current location in program
     * @param ui the Ui responsible for outputting messages
     * @param storageManager storage container
     * @return endingMessage if CLI is used, else return !@#_EDIT_NOTE to request MainWindow class to handle.
     * @throws CakeException File does not exist.
     */
    @Override
    public String execute(Logic logic, Ui ui, StorageManager storageManager) throws CakeException {

        if (storageManager.profile.isCli) {
            if (checkFileIsEmpty(currentFilePath)) {
                ui.showMessage("Write your notes below!\n");
                ui.showMessage("To save edited content, type '/save' and enter!\n");
                readAndSaveNewContent();
                return endingMessage;
            } else {
                ui.showMessage("Below is your previous saved content! "
                        + "Copy your previous content and edit accordingly\n");
                ui.showMessage("To save edited content, type '/save' and enter!\n");
                ui.showMessage(displayContentInFile());
                readAndSaveNewContent();
                return endingMessage;
            }
        } else {
            return "!@#_EDIT_NOTE"; // used for GUI
        }
    }

    /**
     * Informs user if the file to be edited is empty.
     * If file is empty, print headingMessage.
     * Else, print secondHeadingMessage and the content of the edit file.
     * @return String containing heading message and content if available.
     * @throws CakeException if the file does not exists.
     */
    public static String getHeadingMessage() throws CakeException {
        if (checkFileIsEmpty(currentFilePath)) {
            return headingMessage;
        } else {
            String secondHeadingMessage = "Below is your previous saved content! "
                    + "Copy your previous content and edit accordingly\n"
                    + "To save edited content, type '/save' and enter!\n";
            return secondHeadingMessage + readTextFileContent();
        }
    }

    /**
     * Removes all content of text file before user can edit file.
     * EditFileCommand does not append content to file.
     * @throws CakeException File does not exist.
     */
    public static void clearTextFileContent() throws CakeException {
        try {
            PrintWriter pw = new PrintWriter(currentFilePath);
            pw.close();
        } catch (FileNotFoundException e) {
            throw new CakeException(e.getMessage());
        }
    }

    /**
     * Read content in the text file to be edited.
     * @return String of all the content in file to be edited.
     * @throws CakeException File does not exist.
     */
    private static String readTextFileContent() throws CakeException {
        return IFileUtilities.readFile(currentFilePath);
    }

    /**
     * Method used for GUI.
     * Write input content from user into file to be edited.
     * Read new content in the file to be edited.
     * @param input notes from the user.
     * @return heading message and new content in text file.
     * @throws CakeException File does not exist.
     */
    public static String writeSaveGui(String input) throws CakeException {
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(new File(currentFilePath), true));
            bw.write(input);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            throw new CakeException(e.getMessage());
        }

        return headingMessage + readTextFileContent();
    }


    /**
     * Generates formatted message when the file is saved successfully.
     * @return String of message when file is saved.
     */
    public static String successSaveMessage() {
        return "File: [" + nameOfEditFile + "] " + endingMessage;
    }
}
