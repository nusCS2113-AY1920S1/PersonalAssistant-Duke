package duke.logic.parser.commons;

import duke.commons.core.LogsCenter;
import duke.logic.command.Command;
import duke.logic.parser.exceptions.ParseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

/**
 * A logic component that auto-completes user inputs based on pre-defined command words and prefixes.
 */
public class AutoCompleter {
    private List<Class<? extends Command>> commandClasses;

    /**
     * All the suggestions.
     */
    private List<UserInputState> suggestions;

    /**
     * A pointer to the index of the currently proposed suggestion.
     */
    private int suggestionPointer;

    private static final Logger logger = LogsCenter.getLogger(AutoCompleter.class);

    /**
     * Creates an {@code AutoCompleter}.
     */
    public AutoCompleter() {
        logger.info("Initializing AutoCompleter...");
        commandClasses = new ArrayList<>();

        suggestions = new ArrayList<>();
        suggestionPointer = 0;
    }

    /**
     * Returns true if the current state has one or more auto-complete suggestion(s).
     * @param currentState the detail of current input state.
     */
    public Boolean isAutoCompletable(UserInputState currentState) {
        requireNonNull(currentState);

        String commandText = currentState.userInputString;
        int caretPosition = currentState.caretPosition;
        String currentWord = getCurrentWord(commandText, caretPosition);

        //Blank text cannot be auto-completed.
        if (commandText.isBlank() || currentWord.isBlank()) {
            return false;
        }

        if (!suggestions.isEmpty() && currentState.equals(suggestions.get(suggestionPointer))) {
            return true;
        }

        List<String> suggestions = generateSuggestions(commandText, currentWord);

        //If there is no available suggestion words
        if (suggestions.isEmpty()) {
            return false;
        } else {
            //Convert the suggestion words to a user input state and set pointer to zero
            this.suggestions = suggestions.stream().distinct().map(
                suggestionWord -> new UserInputState(replaceWord(commandText, caretPosition, suggestionWord),
                    getNewCaretPosition(caretPosition, currentWord, suggestionWord)))
                    .collect(Collectors.toList());
            suggestionPointer = 0;

            return true;
        }
    }

    /**
     * Returns possible suggestion(s) in the form of {@code UserInputState}.
     *
     * <p>
     * If there is only one available suggestion, returns that state.
     * If there are multiple available suggestions, returns the next possible one,
     * and goes cyclic to the first one if there are no more new suggestions.
     * For example, if the current input state has two suggestions "add" and "all",
     * first call to the method returns the "add" state and second call returns "all" state.
     * The third call returns "add" state again.
     *</p>
     *
     * @throws ParseException if there is no suggestion
     */
    public UserInputState complete() throws ParseException {
        if (suggestions.isEmpty()) {
            logger.warning("No suggestions are available for user input.");
            throw new ParseException();
        }

        logger.info(String.format("New suggestion [%s] found for user input.",
            suggestions.get(suggestionPointer).userInputString
        ));

        suggestionPointer = (suggestionPointer + 1) % suggestions.size();
        return suggestions.get(suggestionPointer);
    }

    /**
     * Returns the available suggested words for {@code currentWord} based on {@code userInput}.
     * If there is no available suggestions, returns an empty list.
     */
    private List<String> generateSuggestions(String userInput, String currentWord) {
        List<String> suggestions = new ArrayList<>();
        Optional<Class<? extends Command>> matchedCommandClass = getMatchedCommandClass(userInput);
        if (matchedCommandClass.isPresent()) {
            suggestions.addAll(generateParameterSuggestions(currentWord, matchedCommandClass.get()));
        } else {
            suggestions.addAll(generateCommandWordSuggestions(currentWord));
        }
        return suggestions;
    }

    /**
     * Adds a command class to auto-complete its command word and arguments.
     * Duplicate command classes are not allowed.
     * <p>
     * To auto-complete arguments, the command should have fields {@code String AUTO_COMPLETION_INDICATOR} and
     * {@code Prefix[] AUTO_COMPLETION_ARGUMENTS}. The namings and types should be precise for auto-completer to
     * function properly.
     * </p>
     * <p>
     * {@code String AUTO_COMPLETION_INDICATOR} specifies when auto-completer should complete the arguments.
     *                                          For example, "order add", "order remove".
     *                                          Auto-completer only completes the arguments when this field is present
     *                                          in the beginning of user input.
     * {@code Prefix[] AUTO_COMPLETION_ARGUMENTS} are prefixes used by the command that you wish to auto-complete.
     * </p>
     *
     * @throws ParseException if duplicate classes are added.
     */
    public void addCommandClass(Class<? extends Command> commandClass) throws ParseException {
        if (commandClasses.contains(commandClass)) {
            logger.warning(String.format("Could not add duplicate %s to AutoCompleter", commandClass.toString()));
            throw new ParseException();
        }

        this.commandClasses.add(commandClass);

    }

    /**
     * Remove all command classes.
     */
    public void clearCommandClasses() {
        this.commandClasses.clear();
    }

    /**
     * Returns the starting index of the word at {@code caretPosition} in {@code commandText}.
     * For example, if the text is "I am a bad guy" and caret is at position 3, the method returns 2,
     * the starting index of "am"
     */
    private int getSelectionStart(String commandText, int caretPosition) {
        int selectionStart = commandText.lastIndexOf(" ", caretPosition - 1);
        if (selectionStart == -1) {
            selectionStart = 0;
        } else {
            selectionStart += 1;
        }
        return selectionStart;
    }

    /**
     * Returns the ending index of the word at {@code caretPosition} in {@code commandText}.
     * For example, if the text is "I am a bad guy" and caret is at position 3, the method returns 3,
     * the ending index of "am"
     */
    private int getSelectionEnd(String commandText, int caretPosition) {
        int selectionEnd = commandText.indexOf(" ", caretPosition);
        if (selectionEnd == -1) {
            selectionEnd = commandText.length() - 1;
        } else {
            selectionEnd -= 1;
        }
        return selectionEnd;
    }


    private String getCurrentWord(String commandText, int caretPosition) {
        return commandText.substring(getSelectionStart(commandText, caretPosition),
                getSelectionEnd(commandText, caretPosition) + 1);
    }

    /**
     * Returns a list of suggested commands based on the incomplete {@code toComplete}.
     */
    private List<String> generateCommandWordSuggestions(String toComplete) {
        List<String> suggestions = new ArrayList<>();
        for (Class<? extends Command> commandClass : commandClasses) {
            getCommandWord(commandClass).ifPresent(commandWordString -> {
                if (commandWordString.startsWith(toComplete)
                        && !commandWordString.equals(toComplete)) {
                    suggestions.add(commandWordString);
                }
            });
        }
        return suggestions;
    }

    /**
     * Returns a list of suggested argument names based on the incomplete {@code toComplete}.
     */
    private List<String> generateParameterSuggestions(String toComplete, Class<? extends Command> commandClass) {
        List<String> suggestions = new ArrayList<>();

        getCommandParameters(commandClass).ifPresent(
            prefixes -> suggestions.addAll(Arrays.stream(prefixes)
                    .map(Prefix::getPrefix)
                    .filter(prefixString -> prefixString.startsWith(toComplete)
                        && !prefixString.equals(toComplete))
                    .collect(Collectors.toList())
                )
        );

        return suggestions;
    }

    /**
     * Replaces the word at {code caretPosition} in {@code cmmandText} with {@code newWord}.
     * @return the new command text after replacement.
     */
    private String replaceWord(String commandText, int caretPosition, String newWord) {
        String beforeCurrent = commandText.substring(0, getSelectionStart(commandText, caretPosition));
        String afterCurrent = commandText.substring(getSelectionEnd(commandText, caretPosition) + 1);
        return beforeCurrent + newWord + afterCurrent;
    }

    private int getNewCaretPosition(int oldPosition, String oldWord, String newWord) {
        return oldPosition - oldWord.length() + newWord.length();
    }

    private String getCommandIndicator(Class<? extends Command> commandClass) {
        try {
            return (String) commandClass.getField("AUTO_COMPLETE_INDICATOR").get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            logger.warning(String.format("Field AUTO_COMPLETE_INDICATOR is not declared in %s",
                commandClass.toString()));

            return "";
        }
    }

    private Optional<String> getCommandWord(Class<? extends Command> commandClass) {
        try {
            return Optional.of((String) commandClass.getField("COMMAND_WORD").get(null));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            logger.warning(String.format("Field COMMAND_WORD is not declared in %s",
                commandClass.toString()));

            return Optional.empty();
        }
    }

    private Optional<Prefix[]> getCommandParameters(Class<? extends Command> commandClass) {
        try {
            return Optional.of((Prefix[]) commandClass
                    .getField("AUTO_COMPLETE_PARAMETERS")
                    .get(null));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            logger.warning(String.format("Field AUTO_COMPLETE_PARAMETERS is not declared in %s",
                commandClass.toString()));

            return Optional.empty();
        }
    }

    private Optional<Class<? extends Command>> getMatchedCommandClass(String userInput) {
        String userInputWithoutConsecutiveSpaces = userInput.strip().replaceAll(" +", " ");

        for (Class<? extends Command> commandClass : commandClasses) {
            String indicator = getCommandIndicator(commandClass);
            if (indicator.isEmpty()) {
                continue;
            }

            if (userInputWithoutConsecutiveSpaces.startsWith(indicator)) {
                return Optional.of(commandClass);
            }
        }

        return Optional.empty();
    }

    /**
     * Represents the details of a user input, including the text and caret position.
     */
    public static class UserInputState {
        /**
         * The text input from user.
         */
        public final String userInputString;

        /**
         * The position of the text insertion point.
         */
        public final int caretPosition;

        /**
         * Creates a {@code UserInputState}.
         *
         * @param userInputString The text input from user
         * @param caretPosition An integer between 0 and the length of {@code userInputString}
         */
        public UserInputState(String userInputString, int caretPosition) {
            this.userInputString = userInputString;
            this.caretPosition = caretPosition;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            UserInputState that = (UserInputState) o;
            return caretPosition == that.caretPosition
                    && Objects.equals(userInputString, that.userInputString);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userInputString, caretPosition);
        }
    }
}
