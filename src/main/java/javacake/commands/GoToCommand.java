package javacake.commands;

import javacake.*;
import javacake.topics.ListIndex3;

public class GoToCommand extends Command {

    private String index;

    public GoToCommand(String inputCommand) {
        String buffer[] = inputCommand.split("\\s+");
        index = buffer[1];
    }

    public String goToIndex() {
        return index;
    }

    public void execute(ProgressStack progressStack, Ui ui, Storage storage, Profile profile) throws DukeException {
        if (progressStack.checkProgress() == 1 && index.equals("1")) {
            progressStack.mainListToListIndex1();
        }
        else if (progressStack.checkProgress() == 1 && index.equals("2")) {
            progressStack.mainListToListIndex2();
        }
        else if (progressStack.checkProgress() == 1 && index.equals("3")) {
            progressStack.mainListToListIndex3();
            ListIndex3 listIndex3 = new ListIndex3();
            listIndex3.printList();
        }
    }
}
