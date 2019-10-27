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
    public ArrayList<String> updatedUserInputList = new ArrayList<>();
    @Override
    public String execute(LookupTable LT, TaskList events, TaskList deadlines, Ui ui, Storage storage) throws Exception {
        fullCommand = fullCommand.replace("show previous", "");
        fullCommand = fullCommand.trim();
        //System.out.println("full command: " + fullCommand);

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

        for (int i = 0; i < size; i ++) {
            if (i % 2 == 0) {
                String userInput = userInputsList.get(i);
                updatedUserInputList.add(userInput);
            }
        }

//        for (String userInput : updatedUserInputList) {
//            System.out.println("Updated user input is " + userInput);
//        }

        int sizeOfUpdatedList = updatedUserInputList.size();
        int sizeOfPreviousList = sizeOfUpdatedList - 1;

//        System.out.println("Size of userInputList: " + size);
//        System.out.println("Size of updated List: " + sizeOfUpdatedList);


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
            int startIndex = sizeOfPreviousList - 1;
            for (int i = 0; i < number; i ++) {
                //size -= 1;
                outputList.add(updatedUserInputList.get(startIndex) + " \n");
                startIndex -= 1;
            }
            result = outputList;

//            for (String output : outputList) {
//                System.out.println("output is: " + output);
//            }

        } else if (fullCommand.equals("add/d")) {
            result = previousCommandsHandler(updatedUserInputList, outputList,"add/d");
        } else if (fullCommand.equals("add/e")) {
            result = previousCommandsHandler(updatedUserInputList, outputList,"add/e");
        } else if (fullCommand.equals("delete/d")) {
            result = previousCommandsHandler(updatedUserInputList, outputList,"delete/d");
        } else if (fullCommand.equals("delete/e")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "delete/e");
        } else if (fullCommand.equals("recur/e")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "recur/e");
        } else if (fullCommand.equals("remind/set")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "remind/set");
        } else if (fullCommand.equals("remind/rm")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "remind/rm");
        } else if (fullCommand.equals("/show")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "/show");
        } else if (fullCommand.equals("filter")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "filter");
        } else if (fullCommand.equals("help")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "help");
        } else if (fullCommand.equals("list")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "list");
        } else if (fullCommand.equals("done")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "done");
        } else if (fullCommand.equals("Available")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "Available");
        } else if (fullCommand.equals("show previous")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "show previous");
        } else if (fullCommand.equals("Week")) {
            result = previousCommandsHandler(updatedUserInputList, outputList, "Week");
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

