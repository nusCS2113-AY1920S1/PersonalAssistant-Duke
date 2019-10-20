package javacake.commands;

import javacake.Duke;
import javacake.ProgressStack;
import javacake.exceptions.DukeException;
import javacake.storage.Profile;
import javacake.storage.Storage;
import javacake.ui.Ui;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class EditNoteCommand extends Command {

    private String defaultDirectoryPath = "src/main/resources/notes/";
    private static String nameOfEditFile;
    private String currentFilePath;

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

    private boolean checkFileIsEmpty(String currentFilePath) {
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
        } finally {

        }

    }

    private void readAndSaveNewContent() throws DukeException{
        BufferedWriter bw = null;
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
        String endingMessage = "";
        if (Duke.isCliMode()) {
            if (checkFileIsEmpty(currentFilePath)) {
                //System.out.println("hello");
                ui.showMessage("Write your notes below!\n");
                ui.showMessage("To save edited content, type '/save' and enter!\n");
                readAndSaveNewContent();
                endingMessage = "Edited file is saved!\n";
                return endingMessage;
            } else {
                ui.showMessage("Below is your previous saved content! Copy your previous content and edit accordingly\n");
                ui.showMessage("To save edited content, type '/save' and enter!\n");
                ui.showMessage(displayContentInFile());
                readAndSaveNewContent();
                endingMessage = "Edited file is saved!\n";
                return endingMessage;
            }
        } else {
            return "!@#_EDIT_NOTE";
        }
        return endingMessage;
    }


}
