package parser;

import dictionary.Word;
import command.Command;
import command.QuizCommand;
import command.BadCommand;
import command.DeleteCommand;
import command.AddTagCommand;
import command.ListCommand;
import command.ExitCommand;
import command.AddCommand;
import command.SearchCommand;
import command.RecentlyAddedCommand;
import command.SearchFrequencyCommand;
import command.EditCommand;

import exception.CommandInvalidException;
import exception.EmptyWordException;
import exception.WordUpException;
import exception.WrongAddFormatException;
import exception.WrongDeleteFormatException;
import exception.WrongSearchFormatException;
import exception.WrongListFormatDescription;
import exception.WrongHistoryFormatException;
import exception.ZeroHistoryRequestException;
import exception.WrongSearchFrequencyFormatException;
import exception.WrongEditFormatException;
import exception.WrongAddTagFormatException;
import exception.WrongQuizFormatException;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Creates a Command object after extracting information needed.
 */
public class Parser {

    /**
     * Extracts the command specified in the user input and creates the respective command objects.
     *
     * @param input user input from command line
     * @return respective Command class objects
     */
    public static Command parse(String input) {
        try {
            String[] taskInfo = input.split(" ", 2);
            String userCommand = taskInfo[0];
            Command command;
            if (userCommand.equals("exit")) {
                return new ExitCommand();
            } else if (userCommand.equals("help")) {
                // CREATE A HELP COMMAND TO SHOW THE AVAILABLE INSTRUCTION
                return null;
            } else if (userCommand.equals("add")) {
                command = parseAdd(taskInfo);
            } else if (userCommand.equals("delete")) {
                command = parseDelete(taskInfo);
            } else if (userCommand.equals("search")) {
                command = parseSearch(taskInfo);
            } else if (userCommand.equals("list")) {
                command = parseList(taskInfo);
            } else if (userCommand.equals("history")) {
                command = parseHistory(taskInfo);
            } else if (userCommand.equals("freq")) {
                command = parseSearchFrequency(taskInfo);
            } else if (userCommand.equals("edit")) {
                command = parseEdit(taskInfo);
            } else if (userCommand.equals("tag")) {
                command = parseTag(taskInfo);
            } else if (userCommand.equals("quiz")) {
                command = parseQuiz(taskInfo);
            } else {
                try {
                    throw new CommandInvalidException(input);
                } catch (CommandInvalidException e) {
                    command = new BadCommand(e.showError());
                }
            }
            return command;
        } catch (WordUpException e) {
            return new BadCommand(e.showError());
        }
    }

    /**
     * Parses an add command.
     * @param taskInfo String array containing first stage parsed user input
     * @return an AddCommand object
     * @throws WrongAddFormatException when the format of the delete command does not match the required format
     * @throws EmptyWordException when there is no word entered with the command
     */
    protected static Command parseAdd(String[] taskInfo) throws WrongAddFormatException, EmptyWordException {
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
            HashSet<String> tags = new HashSet<>();
            for (int j = 1; j < meaningAndTag.length; ++j) {
                tags.add(meaningAndTag[j]);
            }
            word = new Word(wordDescription, meaning, tags);
        } else {
            word = new Word(wordDescription, meaning);
        }
        return new AddCommand(word);
    }

    /**
     * Parses a delete command.
     * @param taskInfo String array containing first stage parsed user input
     * @return a DeleteCommand object
     * @throws WrongDeleteFormatException when the format of the delete command does not match required format
     */
    protected static Command parseDelete(String[] taskInfo) throws WrongDeleteFormatException {
        if (taskInfo.length == 1 || !taskInfo[1].startsWith("w/")) {
            throw new WrongDeleteFormatException();
        }
        String[] wordAndTags = taskInfo[1].split("t/");
        if (wordAndTags.length == 1) {
            return new DeleteCommand(taskInfo[1].substring(2));
        } else {
            String wordDescription = wordAndTags[0].substring(2).trim();
            ArrayList<String> tags = new ArrayList<>();
            for (int i = 1; i < wordAndTags.length; ++i) {
                tags.add(wordAndTags[i].trim());
            }
            return new DeleteCommand(wordDescription, tags);
        }
    }

    /**
     * Parses a search command.
     * @param taskInfo String array containing first stage parsed user input
     * @return a SearchCommand object
     * @throws WrongSearchFormatException when the format of the search command does not match required format
     */
    protected static Command parseSearch(String[] taskInfo) throws WrongSearchFormatException {
        if (taskInfo.length == 1 || !taskInfo[1].startsWith("w/")) {
            throw new WrongSearchFormatException();
        }
        return new SearchCommand(taskInfo[1].substring(2).trim());
    }

    /**
     * Parses a list command.
     * @param taskInfo String array containing first stage parsed user input
     * @return a ListCommand object
     * @throws WrongListFormatDescription when the format of the list command does not match required format
     */
    protected static Command parseList(String[] taskInfo) throws WrongListFormatDescription {
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
    }

    /**
     * Parses a history command which requests for the list of words in order of entry.
     * @param taskInfo String array containing first stage parsed user input
     * @return a HistoryCommand object
     * @throws WrongHistoryFormatException when the format of the history command does not match required format
     * @throws ZeroHistoryRequestException when the requested number of entries to be shown is zero
     */
    protected static Command parseHistory(String[] taskInfo)
            throws WrongHistoryFormatException, ZeroHistoryRequestException {
        int numberOfWordsToDisplay;
        if (taskInfo.length == 1) {
            throw new WrongHistoryFormatException();
        }
        if (taskInfo[1].equals("0")) {
            throw new ZeroHistoryRequestException();
        }
        try {
            numberOfWordsToDisplay = Integer.parseInt(taskInfo[1]);
        } catch (NumberFormatException nfe) {
            throw new WrongHistoryFormatException();
        }
        return new RecentlyAddedCommand(numberOfWordsToDisplay);
    }

    /**
     * Parses a frequency command which requests for a list of the number of searches for each word.
     * @param taskInfo String array containing first stage parsed user input
     * @return a SearchFrequencyCommand object
     * @throws WrongSearchFrequencyFormatException when the format of the frequency command does not match required
     * format
     */
    protected static Command parseSearchFrequency(String[] taskInfo) throws WrongSearchFrequencyFormatException {
        String order = "";
        if (taskInfo.length > 1) {
            if (!taskInfo[1].startsWith("o/")) {
                throw new WrongSearchFrequencyFormatException();
            }
            order = taskInfo[1].substring(2);
            if (!order.equals("asc") && !order.equals("desc")) {
                throw new WrongSearchFrequencyFormatException();
            }
        }
        return new SearchFrequencyCommand(order);
    }

    /**
     * Parses an edit command.
     * @param taskInfo String array containing first stage parsed user input
     * @return an EditCommand object
     * @throws WrongEditFormatException when the format of the edit command does not match required format
     */
    protected static Command parseEdit(String[] taskInfo) throws WrongEditFormatException {
        if (taskInfo.length == 1 || !taskInfo[1].startsWith("w/")) {
            throw new WrongEditFormatException();
        }
        String[] wordAndMeanings = taskInfo[1].split("m/");
        if (wordAndMeanings.length != 2) {
            throw new WrongEditFormatException();
        }
        String wordDescription = wordAndMeanings[0].substring(2).trim();
        String meaning = wordAndMeanings[1];
        return new EditCommand(wordDescription, meaning);
    }

    /**
     * Parses an add tag command.
     * @param taskInfo String array containing first stage parsed user input
     * @return a AddTagCommand object
     * @throws WrongAddTagFormatException when the format of the add tag command does not match required format
     */
    protected static Command parseTag(String[] taskInfo) throws WrongAddTagFormatException {
        if (taskInfo.length == 1 || !taskInfo[1].startsWith("w/")) {
            throw new WrongAddTagFormatException();
        }
        String[] wordAndTags = taskInfo[1].split("t/");
        if (wordAndTags.length == 1) {
            throw new WrongAddTagFormatException();
        }
        String wordDescription = wordAndTags[0].substring(2).trim();
        ArrayList<String> tags = new ArrayList<>();
        for (int i = 1; i < wordAndTags.length; ++i) {
            tags.add(wordAndTags[i].trim());
        }
        return new AddTagCommand(wordDescription, tags);
    }

    /**
     * Parses a quiz command.
     * @param taskInfo String array containing first stage parsed user input
     * @return a QuizCommand object
     * @throws WrongQuizFormatException when the format of the quiz command does not match required format
     */
    protected static Command parseQuiz(String[] taskInfo) throws WrongQuizFormatException {
        if (taskInfo.length > 1) {
            throw new WrongQuizFormatException();
        }
        return new QuizCommand();
    }

}