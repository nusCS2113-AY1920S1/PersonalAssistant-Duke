import Dictionary.Word;
import command.*;
import exception.*;

import java.util.ArrayList;

/**
 * Creates a Command object after extracting information needed.
 */
public class Parser {

    /**
     * Extracts the command specified in the user input and creates the respective command objects.
     * @param input user input from command line
     * @return respective Command class objects
     */
    public static Command parse(String input) {
        try {
            String[] taskInfo = input.split(" ", 2);
            if (taskInfo[0].equals("exit")) {
                //create an ExitCommand
                return new ExitCommand();
            } else if (taskInfo[0].equals("help")) {
                // CREATE A HELP COMMAND TO SHOW THE AVAILABLE INSTRUCTION
                return null;
            } else if (taskInfo[0].equals("add")) {
                // FIX ADD COMMAND TO ADD TO DICTIONARY
                if (taskInfo.length == 1) {
                    throw new WrongAddFormatException();
                }
                String[] wordDetail = taskInfo[1].split("w/");
                if (wordDetail.length != 3) {
                    throw new WrongAddFormatException();
                }
                String wordDescription = wordDetail[1].trim();
                if (wordDescription.length() == 0) {
                    throw new EmptyWordException();
                }
                String[] meaningAndTag = wordDetail[2].split("t/");
                String meaning = meaningAndTag[0].trim();
                if (meaning.length() == 0) {
                    throw new EmptyWordException();
                }
                Word word;
                if (meaningAndTag.length > 1) {
                    ArrayList<String> tags = new ArrayList<>();
                    for (int j = 1; j < meaningAndTag.length; ++j) {
                        tags.add(meaningAndTag[j]);
                    }
                    word = new Word(wordDescription, meaning, tags);
                }
                else {
                    word = new Word(wordDescription, meaning);
                }
                return new AddCommand(word);
            } else if (taskInfo[0].equals("delete")) {
                // FIX DELETE COMMAND TO DELETE WORD FROM DICTIONARY (BOTH TAG AND WORD)
                if (taskInfo.length == 1 || !taskInfo[1].startsWith("w/")) {
                    throw new WrongDeleteFormatException();
                }
                return new DeleteCommand(taskInfo[1].substring(2));
            } else if (taskInfo[0].equals("search")) {
                // CREATE A SEARCH COMMAND TO SEARCH FOR A WORD
                if (taskInfo.length == 1 || !taskInfo[1].startsWith("w/")) {
                    throw new WrongSearchFormatException();
                }
                return new SearchCommand(taskInfo[1].substring(2));
            } else if (taskInfo[0].equals("list")) {
                //FIX LIST COMMAND TO MATCH THE TASK WE NEED TO DO
                String order = "";
                if (taskInfo.length > 1) {
                    if (!taskInfo[1].startsWith("o/")) {
                        throw new WrongListFormatDescription();
                    }
                    order = taskInfo[1].substring(2);
                    if (!order.equals("asc") && !order.equals("desc")) {
                        throw new WrongListFormatDescription();
                    }
                }
                return new ListCommand(order);
            } else if (taskInfo[0].equals("history")) {
                int numberOfWordsToDisplay;
                if (taskInfo.length == 1){
                    throw new WrongHistoryFormatException();
                }
                if (taskInfo[1].equals("0")) {
                    throw new ZeroHistoryRequestException();
                }
                try {
                    numberOfWordsToDisplay = Integer.parseInt(taskInfo[1]);
                } catch (NumberFormatException nfe){
                    throw new WrongHistoryFormatException();
                }
                return new HistoryCommand(numberOfWordsToDisplay);
            } else if (taskInfo[0].equals("edit")) {
                // CREATE AN EDIT COMMAND TO DEAL WITH EDIT WORD
                return null;
            } else if (taskInfo[0].equals("tag")) {
                //CREATE TAG COMMAND TO ADD TAG TO A WORD
                return null;
            } else {
                try {
                    throw new CommandInvalidException(input);
                } catch (CommandInvalidException e){
                    e.showError();
                    return new BadCommand();
                }
            }
        } catch (WordUpException e) {
            e.showError();
            return new BadCommand();
        }
    }
}