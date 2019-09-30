package javacake.commands;

import javacake.*;
import javacake.topics.ListIndex3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GoToCommand extends Command {

    private String index;

    public GoToCommand(String inputCommand) {
        String buffer[] = inputCommand.split("\\s+");
        index = buffer[1];
    }

    public String goToIndex() {
        return index;
    }

    public void execute(ProgressStack progressStack, Ui ui, Storage storage, Profile profile) throws IOException, DukeException {
        try {
            if (progressStack.checkProgress() == 1 && index.equals("1")) {
                progressStack.mainListToListIndex1();
            } else if (progressStack.checkProgress() == 1 && index.equals("2")) {
                progressStack.mainListToListIndex2();
            } else if (progressStack.checkProgress() == 1 && index.equals("3")) {
                progressStack.mainListToListIndex3();
                ListIndex3 listIndex3 = new ListIndex3();
                listIndex3.printList();
            } else if (progressStack.checkProgress() == 2 && index.equals("1")) {
                progressStack.listIndex3ToSubList1();
                BufferedReader reader = new BufferedReader(new FileReader("data/MainList/ListIndex3/Enumerations/Enumerations.txt"));
                ui.displayTextFile(reader);
            } else if (progressStack.checkProgress() == 2 && index.equals("2")) {
                progressStack.listIndex3ToSubList2();
                BufferedReader reader = new BufferedReader(new FileReader("data/MainList/ListIndex3/Varargs/Varargs.txt"));
                ui.displayTextFile(reader);
            } else if (progressStack.checkProgress() == 2 && index.equals("3")) {
                progressStack.listIndex3ToSubList3();
                BufferedReader reader = new BufferedReader(new FileReader("data/MainList/ListIndex3/Exceptions/Exceptions.txt"));
                ui.displayTextFile(reader);
            }
        } catch (IOException e) {
            throw new DukeException("File does not exists");
        }
    }
}
