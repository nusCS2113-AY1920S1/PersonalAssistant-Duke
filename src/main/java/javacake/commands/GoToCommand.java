package javacake.commands;

import javacake.DukeException;
import javacake.ProgressStack;
import javacake.Profile;
import javacake.Ui;
import javacake.Storage;
import javacake.topics.ListIndex1;
import javacake.topics.ListIndex2;
import javacake.topics.ListIndex3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GoToCommand extends Command {

    private String index;

    public GoToCommand(String inputCommand) {
        String[] buffer = inputCommand.split("\\s+");
        index = buffer[1];
    }

    public String goToIndex() {
        return index;
    }

    /**
     * Execute jumping to given index.
     * @param progressStack TaskList containing current tasks
     * @param ui the Ui responsible for outputting messages
     * @param storage Storage needed to write the updated data
     * @param profile Profile of the user
     * @throws DukeException Error thrown when unable to close reader
     */
    public void execute(ProgressStack progressStack, Ui ui, Storage storage, Profile profile) throws DukeException {
        try {
            if (progressStack.checkProgress() == 1 && index.equals("1")) {
                progressStack.mainListToListIndex1();
                ListIndex1 listIndex1 = new ListIndex1();
                listIndex1.printList();
            } else if (progressStack.checkProgress() == 1 && index.equals("2")) {
                progressStack.mainListToListIndex2();
                ListIndex2 listIndex2 = new ListIndex2();
                listIndex2.printList();
            } else if (progressStack.checkProgress() == 1 && index.equals("3")) {
                progressStack.mainListToListIndex3();
                ListIndex3 listIndex3 = new ListIndex3();
                listIndex3.printList();
            } else if (progressStack.checkProgress() == 2 && index.equals("1.1")) {
                progressStack.listIndex3ToSubList1();
                BufferedReader reader = new BufferedReader(
                        new FileReader("content/MainList/ListIndex1/javabasics/1.txt"));
                ui.displayTextFile(reader);
            } else if (progressStack.checkProgress() == 2 && index.equals("1.2")) {
                progressStack.listIndex3ToSubList1();
                BufferedReader reader = new BufferedReader(
                        new FileReader("content/MainList/ListIndex1/javabasics/2.txt"));
                ui.displayTextFile(reader);

            } else if (progressStack.checkProgress() == 2 && index.equals("1.3")) {
                progressStack.listIndex3ToSubList1();
                BufferedReader reader = new BufferedReader(
                        new FileReader("content/MainList/ListIndex1/javabasics/3.txt"));
                ui.displayTextFile(reader);

            } else if (progressStack.checkProgress() == 2 && index.equals("2.1")) {
                progressStack.listIndex3ToSubList1();
                BufferedReader reader = new BufferedReader(
                        new FileReader("content/MainList/ListIndex2/oop/1.txt"));
                ui.displayTextFile(reader);

            } else if (progressStack.checkProgress() == 2 && index.equals("2.2")) {
                progressStack.listIndex3ToSubList1();
                BufferedReader reader = new BufferedReader(
                        new FileReader("content/MainList/ListIndex2/oop/2.txt"));
                ui.displayTextFile(reader);

            } else if (progressStack.checkProgress() == 2 && index.equals("2.3")) {
                progressStack.listIndex3ToSubList1();
                BufferedReader reader = new BufferedReader(
                        new FileReader("content/MainList/ListIndex2/oop/3.txt"));
                ui.displayTextFile(reader);

            } else if (progressStack.checkProgress() == 2 && index.equals("2.4")) {
                progressStack.listIndex3ToSubList1();
                BufferedReader reader = new BufferedReader(
                        new FileReader("content/MainList/ListIndex2/content/oop/4.txt"));
                ui.displayTextFile(reader);

            } else if (progressStack.checkProgress() == 2 && index.equals("3.1")) {
                progressStack.listIndex3ToSubList1();
                BufferedReader reader = new BufferedReader(
                        new FileReader("content/MainList/ListIndex3/Enumerations/Enumerations.txt"));
                ui.displayTextFile(reader);
            } else if (progressStack.checkProgress() == 2 && index.equals("3.2")) {
                progressStack.listIndex3ToSubList2();
                BufferedReader reader = new BufferedReader(
                        new FileReader("content/MainList/ListIndex3/Varargs/Varargs.txt"));
                ui.displayTextFile(reader);
            } else if (progressStack.checkProgress() == 2 && index.equals("3.3")) {
                progressStack.listIndex3ToSubList3();
                BufferedReader reader = new BufferedReader(
                        new FileReader("content/MainList/ListIndex3/Exceptions/Exceptions.txt"));
                ui.displayTextFile(reader);
            }
        } catch (IOException e) {
            throw new DukeException("File does not exists");
        }
    }
}
