package parser;

import command.AddCommand;
import command.AddSynonymCommand;
import command.AddTagCommand;
import command.BadCommand;
import command.Command;
import command.DeleteCommand;
import command.EditCommand;
import command.ExitCommand;
import command.HelpCommand;
import command.HistoryCommand;
import command.ListCommand;
import command.ListTagCommand;
import command.QuizCommand;
import command.SearchBeginCommand;
import command.SearchCommand;
import command.SearchFrequencyCommand;
import command.SearchTagCommand;
import command.SearchSynonymCommand;
import command.SetReminderCommand;
import dictionary.Word;

import exception.CommandInvalidException;
import exception.EmptyTagException;
import exception.EmptyWordException;
import exception.EmptySynonymException;
import exception.InvalidCharacterException;
import exception.InvalidHistoryIndexException;
import exception.ReminderWrongDateFormatException;
import exception.WordUpException;
import exception.WrongAddFormatException;
import exception.WrongAddSynonymFormatException;
import exception.WrongAddTagFormatException;
import exception.WrongDeleteFormatException;
import exception.WrongEditFormatException;
import exception.WrongHistoryFormatException;
import exception.WrongListFormatException;
import exception.WrongListTagFormatException;
import exception.WrongQuizFormatException;
import exception.WrongReminderFormatException;
import exception.WrongSearchBeginFormatException;
import exception.WrongSearchFormatException;
import exception.WrongSearchFrequencyFormatException;
import exception.WrongSearchTagFormatException;
import exception.WrongSearchSynonymFormatException;
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
     * Valid string for input word, which contains a-z, A-Z.
     */
    private static final String VALID_REGEX = "^[a-zA-Z ]*$";

    /**
     * Extracts the command specified in the user input and creates the respective command objects.
     *
     * @param input user input from command line
     * @return respective Command class objects
     */
    public static Command parse(String input) {
        try {
            String[] taskInfo = input.trim().split(" ", 2);
            for (int i = 0; i < taskInfo.length; i++) {
                taskInfo[i] = taskInfo[i].toLowerCase();
                taskInfo[i] = taskInfo[i].trim().replaceAll(" +", " ");
            }
            String userCommand = taskInfo[0];
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
            } else if (userCommand.equals("list_tags")) {
                command = parseListTags(taskInfo);
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
            } else if (userCommand.equals("search_tag")) {
                command = parseSearchTag(taskInfo);
            } else if (userCommand.equals("search_syn")) {
                command = parseSearchSynonym(taskInfo);
            }
            else {
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

    private static Command parseListTags(String[] taskInfo) throws WrongListTagFormatException {
        if (taskInfo.length > 1) {
            throw new WrongListTagFormatException();
        }
        return new ListTagCommand();
    }

    private static Command parseSearchTag(String[] taskInfo) throws WrongSearchTagFormatException,
            EmptyTagException, EmptyWordException, InvalidCharacterException {
        if (taskInfo.length == 1 || (!taskInfo[1].startsWith("t/") && !taskInfo[1].startsWith("w/"))) {
            throw new WrongSearchTagFormatException();
        }
        if (taskInfo[1].startsWith("t/")) {
            String tag = taskInfo[1].substring(2).trim();
            if (tag.length() == 0) {
                throw new EmptyTagException();
            }
            if (!isValidInputWord(tag)) {
                throw new InvalidCharacterException();
            }
            return new SearchTagCommand(tag, "tag");
        } else {
            String word = taskInfo[1].substring(2).trim();
            if (word.length() == 0) {
                throw new EmptyWordException();
            }
            if (!isValidInputWord(word)) {
                throw new InvalidCharacterException();
            }
            return new SearchTagCommand(word, "word");
        }
    }

    private static Command parseSearchSynonym(String[] taskInfo) throws WrongSearchSynonymFormatException,
            EmptySynonymException, EmptyWordException, InvalidCharacterException {
        if (taskInfo.length == 1 || !taskInfo[1].startsWith("w/")) {
            throw new WrongSearchSynonymFormatException();
        }
        if (taskInfo[1].startsWith("w/")) {
            String synonym = taskInfo[1].substring(2).trim();
            if (synonym.length() == 0) {
                throw new EmptyWordException();
            }
            if (!isValidInputWord(synonym)) {
                throw new InvalidCharacterException();
            }
            return new SearchSynonymCommand(synonym,"synonym");
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
            return new HelpCommand();
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
            EmptyWordException, EmptyTagException, InvalidCharacterException {
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
        if (!isValidInputWord(wordDescription)) {
            throw new InvalidCharacterException();
        }
        String[] meaningAndTag = wordDetail[1].split("t/");
        String meaning = meaningAndTag[0].trim();
        if (!isValidInputWord(meaning)) {
            throw new InvalidCharacterException();
        }
        if (meaning.length() == 0) {
            throw new EmptyWordException();
        }
        Word word;
        if (meaningAndTag.length > 1) {
            HashSet<String> tags = new HashSet<>();
            for (int j = 1; j < meaningAndTag.length; ++j) {
                if (meaningAndTag[j].trim().length() == 0) {
                    throw new EmptyTagException();
                }
                if (isValidInputWord(meaningAndTag[j])) {
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
    protected static Command parseDelete(String[] taskInfo)
            throws WrongDeleteFormatException, EmptyWordException, EmptyTagException, InvalidCharacterException {
        if (taskInfo.length == 1 || !taskInfo[1].startsWith("w/") || taskInfo[1].split("w/").length != 2) {
            throw new WrongDeleteFormatException();
        }
        String[] wordAndTags = taskInfo[1].split("t/");
        if (wordAndTags.length == 1) {
            String deletedWord = taskInfo[1].substring(2).trim();
            if (deletedWord.length() == 0) {
                throw new EmptyWordException();
            }
            if (!isValidInputWord(deletedWord)) {
                throw new InvalidCharacterException();
            }
            return new DeleteCommand(deletedWord);
        } else {
            String wordDescription = wordAndTags[0].substring(2).trim();
            if (wordDescription.length() == 0) {
                throw new EmptyWordException();
            }
            ArrayList<String> tags = new ArrayList<>();
            for (int i = 1; i < wordAndTags.length; ++i) {
                if (wordAndTags[i].trim().length() == 0) {
                    throw new EmptyTagException();
                }
                tags.add(wordAndTags[i].trim());
            }
            return new DeleteCommand(wordDescription, tags,1);
        }
    }

    /**
     * Parses a search command.
     * @param taskInfo String array containing first stage parsed user input
     * @return a SearchCommand object
     * @throws WrongSearchFormatException when the format of the search command does not match required format
     */
    protected static Command parseSearch(String[] taskInfo)
            throws WrongSearchFormatException, EmptyWordException, InvalidCharacterException {
        if (taskInfo.length == 1 || !taskInfo[1].startsWith("w/")) {
            throw new WrongSearchFormatException();
        }
        String word = taskInfo[1].substring(2).trim();
        if (word.length() == 0) {
            throw new EmptyWordException();
        }
        if (!isValidInputWord(word)) {
            throw new InvalidCharacterException();
        }
        return new SearchCommand(word);
    }

    protected static Command parseSearchBegin(String[] taskInfo)
            throws WrongSearchBeginFormatException, EmptyWordException, InvalidCharacterException {
        if (taskInfo.length == 1 || !taskInfo[1].startsWith("w/")) {
            throw new WrongSearchBeginFormatException();
        }
        String word = taskInfo[1].substring(2).trim();
        if (word.length() == 0) {
            throw new EmptyWordException();
        }
        if (!isValidInputWord(word)) {
            throw new InvalidCharacterException();
        }
        return new SearchBeginCommand(word);
    }

    /**
     * Parses a list command.
     * @param taskInfo String array containing first stage parsed user input
     * @return a ListCommand object
     * @throws WrongListFormatException when the format of the list command does not match required format
     */
    protected static Command parseList(String[] taskInfo) throws WrongListFormatException {
        String order = "";
        if (taskInfo.length > 1) {
            if (!taskInfo[1].startsWith("o/")) {
                throw new WrongListFormatException();
            }
            order = taskInfo[1].substring(2);
            if (!order.equals("asc") && !order.equals("desc")) {
                throw new WrongListFormatException();
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
            throws WrongHistoryFormatException, ZeroHistoryRequestException, InvalidHistoryIndexException {
        int numberOfWordsToDisplay;
        if (taskInfo.length == 1) {
            throw new WrongHistoryFormatException();
        }
        try {
            numberOfWordsToDisplay = Integer.parseInt(taskInfo[1]);
        } catch (NumberFormatException nfe) {
            throw new WrongHistoryFormatException();
        }
        if (numberOfWordsToDisplay < 0) {
            throw new InvalidHistoryIndexException();
        }
        if (numberOfWordsToDisplay == 0) {
            throw new ZeroHistoryRequestException();
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
        if (taskInfo.length == 1 || !taskInfo[1].startsWith("w/")) {
            throw new WrongAddSynonymFormatException();
        }
        String[] wordAndSynonyms = taskInfo[1].split("s/");
        if (wordAndSynonyms.length == 1) {
            throw new WrongAddSynonymFormatException();
        }
        String wordDescription = wordAndSynonyms[0].substring(2).trim(); //drink
        String[] synonymsString = wordAndSynonyms[1].trim().split(" ");
        ArrayList<String> synonyms = new ArrayList<>();
        for (String synonym : synonymsString){
            synonyms.add(synonym.trim());
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
     * Checks if an input word is in valid form.
     * Valid input is a word that contains a-z, A-Z
     * @param word string represents word input by user
     * @return true if input is valid
     */
    protected static boolean isValidInputWord(String word) {
        return ((word != null)
                && (!word.equals(""))
                && (word.matches(VALID_REGEX)));
    }
}