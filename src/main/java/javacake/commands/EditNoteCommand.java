package javacake.commands;

import javacake.Duke;
import javacake.ProgressStack;
import javacake.exceptions.DukeException;
import javacake.storage.Profile;
import javacake.storage.Storage;
import javacake.ui.Ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class EditNoteCommand extends Command {

    private String defaultDirectoryPath = "data/notes/";

    private static String nameOfEditFile;
    private static String currentFilePath;

    private static String headingMessage = "Write your notes below!\n"
            + "To save edited content, type '/save' and enter!\n";

    private static String endingMessage = "Edited file is saved!\n";

    /**
     * Constructor for EditNoteCommand.
     * Checks if command has two parameters - "editnote" and "name of file to be edited".
     * Checks if "name of file to be edited" exists.
     * If former checks passed, update nameOfEditFile and currentFilePath variables.
     * Else inform user to either provide valid file name or valid EditNoteCommand.
     * @param inputCommand Input command from the user.
     * @throws DukeException if invalid command or invalid file name.
     */
    public EditNoteCommand(String inputCommand) throws DukeException {
        type = CmdType.EDITNOTE;
        String[] wordsInInputCommand = inputCommand.split("\\s+");
        if (wordsInInputCommand.length == 2) {
            if (fileExist(wordsInInputCommand[1])) {
                nameOfEditFile = wordsInInputCommand[1];
                createCurrentFilePath();
            } else {
                throw new DukeException("Pls enter a valid file name! Type 'listnote' to view available notes!");
            }
        } else {
            throw new DukeException("Pls enter a valid editnote command:"
                    + " 'editnote - [name of the file you wish you edit]'");
        }
    }

    /**
     * Checks if name of the file exists in directory path.
     * @param fileName Name of the file to be checked and edited.
     * @return True if the file exists in directory path, false otherwise.
     */
    private boolean fileExist(String fileName) {
        File file = new File(defaultDirectoryPath + fileName + ".txt");
        if (file.exists()) {
            return true;
        }
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
     * @throws DukeException if the file does not exist.
     */
    private String displayContentInFile() throws DukeException {
        try {
            File file = new File(currentFilePath);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();
            return sb.toString();
        } catch (IOException e) {
            throw new DukeException(e.getMessage());
        }
    }

    /**
     * For CLI only.
     * Read input from user and write into specified note file.
     * If '/save' is called, file will be saved.
     * @throws DukeException File does not exist.
     */
    private void readAndSaveNewContent() throws DukeException {
        BufferedWriter bw;
        try {
            Ui ui = new Ui();
            bw = new BufferedWriter(new FileWriter(new File(currentFilePath)));
            String lineRead;
            while (!(lineRead = ui.readCommand()).equals("/save")) {
                bw.write(lineRead);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            throw new DukeException(e.getMessage());
        }
    }

    /**
     * Executes the EditNoteCommand accordingly depends on CLI or GUI.
     * If CLI, use ui and readAndSaveNewContent method to generate message for user.
     * If GUI, return !@#_EDIT_NOTE to notify MainWindow class to call GUI methods.
     * @param progressStack tracks current location in program
     * @param ui the Ui responsible for outputting messages
     * @param storage Storage needed to write the updated data
     * @param profile Profile of the user
     * @return endingMessage if CLI is used, else return !@#_EDIT_NOTE to request MainWindow class to handle.
     * @throws DukeException File does not exist.
     */
    public String execute(ProgressStack progressStack, Ui ui, Storage storage, Profile profile) throws DukeException {

        if (Duke.isCliMode()) {
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
     * Inform user if the file to be edited is empty.
     * If file is empty, print headingMessage.
     * Else, print secondHeadingMessage and the content of the edit file.
     * @return String containing heading message and content if available.
     * @throws DukeException if the file does not exists.
     */
    public static String getHeadingMessage() throws DukeException {
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
     * @throws DukeException File does not exist.
     */
    public static void clearTextFileContent() throws DukeException {
        try {
            PrintWriter pw = new PrintWriter(currentFilePath);
            pw.close();
        } catch (FileNotFoundException e) {
            throw new DukeException(e.getMessage());
        }
    }

    /**
     * Read content in the text file to be edited.
     * @return String of all the content in file to be edited.
     * @throws DukeException File does not exist.
     */
    private static String readTextFileContent() throws DukeException {
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
            throw new DukeException(e.getMessage());
        }
    }

    /**
     * Method used for GUI.
     * Write input content from user into file to be edited.
     * Read new content in the file to be edited.
     * @param input notes from the user.
     * @return heading message and new content in text file.
     * @throws DukeException File does not exist.
     */
    public static String writeSaveGui(String input) throws DukeException {
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(new File(currentFilePath), true));
            bw.write(input);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            throw new DukeException(e.getMessage());
        }

        return headingMessage + readTextFileContent();
    }


    /**
     * Generates formatted message when the file is saved successfully.
     * @return String of message when file is saved.
     */
    public static String successSaveMessage() {
        return nameOfEditFile + ".txt : " + endingMessage;
    }
}
