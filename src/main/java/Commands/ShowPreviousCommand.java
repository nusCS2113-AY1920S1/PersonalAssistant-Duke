package Commands;

import Interface.Duke;
import Interface.LookupTable;
import Interface.Storage;
import Interface.Ui;
import Tasks.TaskList;
import java.util.ArrayList;

public class ShowPreviousCommand extends Command{

    private String fullCommand;

    public ShowPreviousCommand(String fullCommand) {
        this.fullCommand = fullCommand;

    }

    public ArrayList<String> previousCommandsHandler(ArrayList<String> userInputList, ArrayList<String> outputList, String s) {
        int size = userInputList.size() - 1;
        String userInput;
        for (int j = 0; j < size; j ++) {
            userInput = userInputList.get(j);
             if (userInput.contains(s)) {
                outputList.add(userInput + " \n");
            }
        }
        return outputList;
    }

    public static ArrayList<String> result = new ArrayList<>();
    public ArrayList<String> userInputsList = new ArrayList<>();
    @Override
    public String execute(LookupTable LT, TaskList events, TaskList deadlines, Ui ui, Storage storage) throws Exception {
        fullCommand = fullCommand.replace("show previous", "");
        fullCommand = fullCommand.trim();
        System.out.println("full command: " + fullCommand);

        boolean isNumber = true;
        int number = 0;
        try {
            number = Integer.parseInt(fullCommand);
        } catch (NumberFormatException e) {
            isNumber = false;
        }

        ArrayList<String> outputList = new ArrayList<>();
        userInputsList = Duke.getUserInputs();
        int size = userInputsList.size();
        int sizeOfPreviousList = size - 1;

        /* PRINTING FOR CHECKING:
         * for (String userInput : userInputsList) {
         *    System.out.println("User input is " + userInput);
         * }
         * System.out.println("size: ");
         * System.out.println(size);
         * System.out.println(userInputsList);
         */
        if (sizeOfPreviousList < number) {
            return ui.showInvalidNumberErrorMessage(sizeOfPreviousList);
        }

        if (isNumber) {
            size = size -2;
            for (int i = 0; i < number; i ++) {
                //size -= 1;
                outputList.add(userInputsList.get(size) + " \n");
                size -= 1;
            }
            result = outputList;

//            for (String output : outputList) {
//                System.out.println("output is: " + output);
//            }

        } else if (fullCommand.equals("add/d")) {

            //System.out.println("the command is add/d");

            result = previousCommandsHandler(userInputsList, outputList,"add/d");
        } else if (fullCommand.equals("add/e")) {
            result = previousCommandsHandler(userInputsList, outputList,"add/e");
        } else if (fullCommand.equals("delete/d")) {
            result = previousCommandsHandler(userInputsList, outputList,"delete/d");
        } else if (fullCommand.equals("delete/e")) {
            result = previousCommandsHandler(userInputsList, outputList, "delete/e");
        } else if (fullCommand.equals("recur/e")) {
            result = previousCommandsHandler(userInputsList, outputList, "recur/e");
        } else if (fullCommand.equals("remind/set")) {
            result = previousCommandsHandler(userInputsList, outputList, "remind/set");
        } else if (fullCommand.equals("remind/rm")) {
            result = previousCommandsHandler(userInputsList, outputList, "remind/rm");
        } else if (fullCommand.equals("/show")) {
            result = previousCommandsHandler(userInputsList, outputList, "/show");
        } else if (fullCommand.equals("filter")) {
            result = previousCommandsHandler(userInputsList, outputList, "filter");
        } else if (fullCommand.equals("help")) {
            result = previousCommandsHandler(userInputsList, outputList, "help");
        } else if (fullCommand.equals("list")) {
            result = previousCommandsHandler(userInputsList, outputList, "list");
        } else if (fullCommand.equals("done")) {
            result = previousCommandsHandler(userInputsList, outputList, "done");
        } else if (fullCommand.equals("Available")) {
            result = previousCommandsHandler(userInputsList, outputList, "Available");
        } else if (fullCommand.equals("show previous")) {
            result = previousCommandsHandler(userInputsList, outputList, "show previous");
        }

//        for (String output : result) {
//            System.out.println(output);
//        }

        return ui.showPrevious(result);
    }

    public static ArrayList<String> getOutputList() {
        return result;
    }
}

