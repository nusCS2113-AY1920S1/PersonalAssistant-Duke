package javacake.commands;

import javacake.Duke;
import javacake.ProgressStack;
import javacake.exceptions.DukeException;
import javacake.storage.Profile;
import javacake.storage.Storage;
import javacake.ui.Ui;

import java.io.*;

public class EditNoteCommand extends Command {

    private String defaultDirectoryPath = "src/main/resources/notes/";
    private static String nameOfEditFile;
    private static String currentFilePath;
    private static String headingMessage = "Write your notes below!\n" +
            "To save edited content, type '/save' and enter!\n";
    private static String secondHeadingMessage = "Below is your previous saved content! Copy your previous content and edit accordingly\n" +
                "To save edited content, type '/save' and enter!\n";
    private static String endingMessage = "Edited file is saved!\n";

    public EditNoteCommand(String inputCommand) throws DukeException {
        type = CmdType.EDITNOTE;
        String[] wordsInInputCommand = inputCommand.split("\\s+");
        if (wordsInInputCommand.length == 2 ) {
            if (fileExist(wordsInInputCommand[1])) {
                nameOfEditFile = wordsInInputCommand[1];
                createCurrentFilePath();
            } else {
                throw new DukeException("Pls enter a valid file name! Type 'listnote' to view available notes!");
            }
        } else {
            throw new DukeException("Pls enter a valid editnote command: 'editnote - [name of the file you wish you edit]'");
        }

    }

    private boolean fileExist(String fileName) {
        File file = new File(defaultDirectoryPath + fileName + ".txt");
        if (file.exists()) {
            return true;
        }
        return false;
    }

    private void createCurrentFilePath() {
        currentFilePath = defaultDirectoryPath + nameOfEditFile + ".txt";
    }

    private static boolean checkFileIsEmpty(String currentFilePath) {
        File file = new File(currentFilePath);
        return file.length() == 0;
    }

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

    private void readAndSaveNewContent() throws DukeException{
        BufferedWriter bw;
        try{
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

    public String execute(ProgressStack progressStack, Ui ui, Storage storage, Profile profile) throws DukeException {

        if (Duke.isCliMode()) {
            if (checkFileIsEmpty(currentFilePath)) {
                //System.out.println("hello");
                ui.showMessage("Write your notes below!\n");
                ui.showMessage("To save edited content, type '/save' and enter!\n");
                readAndSaveNewContent();
                return endingMessage;
            } else {
                ui.showMessage("Below is your previous saved content! Copy your previous content and edit accordingly\n");
                ui.showMessage("To save edited content, type '/save' and enter!\n");
                ui.showMessage(displayContentInFile());
                readAndSaveNewContent();
                return endingMessage;
            }
        } else {
            return "!@#_EDIT_NOTE";
        }
    }

    public static String getHeadingMessage() throws DukeException {
        if (checkFileIsEmpty(currentFilePath)) {
            //System.out.println("hello");
            return headingMessage;
        } else {
            return secondHeadingMessage + readTextFileContent();
        }
    }
    public static void clearTextFileContent() throws DukeException {
        try  {
            PrintWriter pw = new PrintWriter(currentFilePath);
            pw.close();
        } catch (FileNotFoundException e) {
            throw new DukeException(e.getMessage());
        }

    }

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

    public static String writeSaveGui(String input) throws DukeException {
        BufferedWriter bw;
        try{
            bw = new BufferedWriter(new FileWriter(new File(currentFilePath), true));
            bw.write(input);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            throw new DukeException(e.getMessage());
        }

        return headingMessage + readTextFileContent();
    }

    public static String successSaveMessage() {
        return nameOfEditFile + ".txt : " + endingMessage;
    }
}
