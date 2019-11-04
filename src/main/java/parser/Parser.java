package parser;

import command.AddCommand;
import command.AddTagCommand;
import command.AddSynonymCommand;
import command.BadCommand;
import command.Command;
import command.DeleteCommand;
import command.EditCommand;
import command.ExitCommand;
import command.HelpCommand;
import command.HistoryCommand;
import command.ListCommand;
import command.QuizCommand;
import command.SearchBeginCommand;
import command.SearchCommand;
import command.SearchFrequencyCommand;
import command.SetReminderCommand;
import dictionary.Word;
import exception.CommandInvalidException;
import exception.EmptyWordException;
import exception.InvalidCharacterException;
import exception.WordUpException;
import exception.WrongAddFormatException;
import exception.WrongAddSynonymFormatException;
import exception.WrongAddTagFormatException;
import exception.ReminderWrongDateFormatException;
import exception.WrongDeleteFormatException;
import exception.WrongEditFormatException;
import exception.WrongHistoryFormatException;
import exception.WrongListFormatDescription;
import exception.WrongQuizFormatException;
import exception.WrongReminderFormatException;
import exception.WrongSearchBeginFormatException;
import exception.WrongSearchFormatException;
import exception.WrongSearchFrequencyFormatException;
import exception.ZeroHistoryRequestException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
            String[] taskInfo = input.trim().split(" ", 2);
            String userCommand = taskInfo[0].trim();
            Command command;
            if (userCommand.equals("exit")) {
                command = new ExitCommand();
            } else if (userCommand.equals("help")) {
                command = parseHelp(taskInfo);
            } else if (userCommand.equals("add")) {
                command = parseAdd(taskInfo);
            } else if (userCommand.equals("delete")) {
                command = parseDelete(taskInfo);
            } else if (userCommand.equals("search")) {
                command = parseSearch(taskInfo);
            } else if (userCommand.equals("search_begin")) {
                command = parseSearchBegin(taskInfo);
            } else if (userCommand.equals("list")) {
                command = parseList(taskInfo);
            } else if (userCommand.equals("history")) {
                command = parseHistory(taskInfo);
            } else if (userCommand.equals("freq")) {
                command = parseSearchFrequency(taskInfo);
            } else if (userCommand.equals("schedule")) {
                command = parseReminder(taskInfo);
            } else if (userCommand.equals("edit")) {
                command = parseEdit(taskInfo);
            } else if (userCommand.equals("tag")) {
                command = parseTag(taskInfo);
            } else if (userCommand.equals("addsyn")) {
                command = parseSynonym(taskInfo);
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

    protected static Command parseReminder(String[] taskInfo) throws WrongReminderFormatException {
        if (taskInfo.length != 1) {
            throw new WrongReminderFormatException();
        }
        return new SetReminderCommand(1); //first state for reminder; start setup
    }

    /**
     * Parses a help command.
     * @param taskInfo String array containing first stage parsed user input
     * @return an HelpCommand object
     */
    protected static Command parseHelp(String[] taskInfo) {
        if (taskInfo.length == 1) {
            return new HelpCommand("");
        }
        return new HelpCommand(taskInfo[1]);
    }

    /**
     * Parses an add command.
     * @param taskInfo String array containing first stage parsed user input
     * @return an AddCommand object
     * @throws WrongAddFormatException when the format of the delete command does not match the required format
     * @throws EmptyWordException when there is no word entered with the command
     */
    protected static Command parseAdd(String[] taskInfo) throws WrongAddFormatException,
            EmptyWordException, InvalidCharacterException {
        if (taskInfo.length == 1) {
            throw new WrongAddFormatException();
        }
        String[] wordDetail = taskInfo[1].split("w/");
        if (wordDetail.length != 2) {
            throw new WrongAddFormatException();
        }
        wordDetail = wordDetail[1].split("m/");
        if (wordDetail.length != 2) {
            throw new WrongAddFormatException();
        }
        String wordDescription = wordDetail[0].trim();
        if (wordDescription.length() == 0) {
            throw new EmptyWordException();
        }
        if (isAlphabetString(wordDescription) == false) {
            throw new InvalidCharacterException();
        }
        String[] meaningAndTag = wordDetail[1].split("t/");
        String meaning = meaningAndTag[0].trim();
        if (isAlphabetString(meaning) == false) {
            throw new InvalidCharacterException();
        }
        if (meaning.length() == 0) {
            throw new EmptyWordException();
        }
        Word word;
        if (meaningAndTag.length > 1) {
            HashSet<String> tags = new HashSet<>();
            for (int j = 1; j < meaningAndTag.length; ++j) {
                if (isAlphabetString(meaningAndTag[j])) {
                    tags.add(meaningAndTag[j]);
                } else {
                    throw new InvalidCharacterException();
                }
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

    protected static Command parseSearchBegin(String[] taskInfo) throws WrongSearchBeginFormatException {
        if (taskInfo.length == 1 || !taskInfo[1].startsWith("w/")) {
            throw new WrongSearchBeginFormatException();
        }
        return new SearchBeginCommand(taskInfo[1].substring(2).trim());
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
     * Parses a history command which requests for the list of words in displayOrder of entry.
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
        return new HistoryCommand(numberOfWordsToDisplay);
    }

    /**
     * Parses a frequency command which requests for a list of the number of searches for each word.
     * @param taskInfo String array containing first stage parsed user input
     * @return a SearchFrequencyCommand object
     * @throws WrongSearchFrequencyFormatException when format of the frequency command does not match required format
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
     * Parses a date String.
     * @param dateInput String value of date from user input
     * @return Date object containing date values from date String input
     */
    public static Date parseDate(String dateInput) throws ReminderWrongDateFormatException {
        String pattern = "dd-MM-yyyy HHmm";
        SimpleDateFormat formattedDate = new SimpleDateFormat(pattern);
        Date date = new Date();

        try {
            date = formattedDate.parse(dateInput);
        } catch (java.text.ParseException e) {
            throw new ReminderWrongDateFormatException();
        }
        return date;
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
     * Parses an add synonym command.
     * @param taskInfo String array containing first stage parsed user input
     * @return a AddSynonymCommand object
     * @throws WrongAddSynonymFormatException when the format of the add synonym command does not match required format
     * @author Ng Jian Wei
     */
    protected static Command parseSynonym(String[] taskInfo) throws WrongAddSynonymFormatException {
        if (taskInfo.length == 1 || !taskInfo[1].startsWith("w/")) { //taskInfo[1] is w/drink s/beverage
            throw new WrongAddSynonymFormatException();
        }
        String[] wordAndSynonyms = taskInfo[1].split("s/"); // "w/drink" and "beverage syno2 syno3"
        if (wordAndSynonyms.length == 1) {
            throw new WrongAddSynonymFormatException();
        }
        String wordDescription = wordAndSynonyms[0].substring(2).trim(); //drink
        String[] synonymsString = wordAndSynonyms[1].trim().split(" ");
        ArrayList<String> synonyms = new ArrayList<>();
        for (String s : synonymsString) {
            synonyms.add(s.trim());
        }
        return new AddSynonymCommand(wordDescription, synonyms);
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

    /**
     * Checks a string to ensure it contains only alphabets and spaces
     * @param s
     * @return true or false
     */
    protected static boolean isAlphabetString(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }
        char[] chars = s.toCharArray();
        for (int index = 0; index < chars.length; index++) {
            int codePoint = Character.codePointAt(chars, index);
            if (!Character.isLetter(codePoint) && !Character.isSpaceChar(codePoint)) {
                return false;
            }
        }
        return true;
    }
}