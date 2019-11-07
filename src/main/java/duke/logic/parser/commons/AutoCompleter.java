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

    //All the suggestions generated.
    private List<Input> suggestions;

    //A pointer to the currently used suggestion.
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
     * Returns true if the current input has one or more auto-complete suggestion(s).
     * @param input the detail of current input.
     */
    public Boolean isAutoCompletable(Input input) {
        requireNonNull(input);

        //Blank text cannot be auto-completed.
        if (isBlank(input)) {
            return false;
        }

        //If the current input is the same as the input pointed by suggestionPointer, it is guaranteed
        //to be auto-completable.
        if (!suggestions.isEmpty() && input.equals(suggestions.get(suggestionPointer))) {
            return true;
        }

        List<String> suggestionWords = generateSuggestedWords(input.text, input.getCurrentWord());

        logger.info(String.format("Found %s suggestion(s)", suggestions.size()));

        //No available suggestions.
        if (suggestionWords.isEmpty()) {
            return false;
        }

        generateSuggestionInputs(input, suggestionWords);
        return true;
    }

    /**
     * Returns possible suggestion(s) in the form of {@code Input}.
     *
     * <p>
     * If there is only one available suggestion, returns that suggestion.
     * If there are multiple available suggestions, returns the next possible one,
     * and goes cyclic to the first one if there are no more new suggestions.
     *
     * For example, if the current input has two suggestions "add" and "all" (caret positions omitted),
     * first call to the method returns the "add" input and second call returns "all" input.
     * The third call returns "add" input again.
     *</p>
     *
     * @throws ParseException if there is no suggestion
     */
    public Input complete() throws ParseException {
        if (suggestions.isEmpty()) {
            logger.warning("No suggestions are available for user input.");
            throw new ParseException();
        }

        logger.info(String.format("New suggestion [%s] found for user input.",
            suggestions.get(suggestionPointer).text
        ));

        suggestionPointer = (suggestionPointer + 1) % suggestions.size();

        return suggestions.get(suggestionPointer);
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
        requireNonNull(commandClass);

        if (commandClasses.contains(commandClass)) {
            logger.warning(String.format("Could not add duplicate %s to AutoCompleter", commandClass.toString()));
            throw new ParseException();
        }

        this.commandClasses.add(commandClass);
    }

    /**
     * Removes all command classes.
     */
    public void clearCommandClasses() {
        this.commandClasses.clear();
    }

    /**
     * Returns the available suggested words for {@code currentWord} based on {@code userInput}.
     * If there is no available suggestions, returns an empty list.
     */
    private List<String> generateSuggestedWords(String userInput, String currentWord) {
        assert (userInput != null);

        List<String> suggestions = new ArrayList<>();
        Optional<Class<? extends Command>> matchedCommandClass = getMatchedCommandClass(userInput);
        matchedCommandClass.ifPresent(matchedClass ->
            suggestions.addAll(generateParameterSuggestions(currentWord, matchedClass)));

        suggestions.addAll(generateCommandWordSuggestions(currentWord));

        return suggestions;
    }

    private void generateSuggestionInputs(Input input, List<String> suggestionWords) {
        this.suggestions = suggestionWords.stream().distinct().map(
            input::replaceCurrentWord)
            .collect(Collectors.toList());
        suggestionPointer = 0;
    }

    /**
     * Returns a list of suggested commands based on the incomplete {@code toComplete}.
     */
    private List<String> generateCommandWordSuggestions(String toComplete) {
        requireNonNull(toComplete);

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

    private boolean isBlank(Input input) {
        return input.text.isBlank()
            || input.getCurrentWord().isBlank();
    }

    //=============== Field utilities ================
    /*
      Used to retrieve field values from command classes using Reflection API.
     */

    private String getCommandIndicator(Class<? extends Command> commandClass) {
        try {
            return (String) commandClass.getField("AUTO_COMPLETE_INDICATOR").get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return "";
        }
    }

    private Optional<String> getCommandWord(Class<? extends Command> commandClass) {
        try {
            return Optional.of((String) commandClass.getField("COMMAND_WORD").get(null));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return Optional.empty();
        }
    }

    private Optional<Prefix[]> getCommandParameters(Class<? extends Command> commandClass) {
        try {
            return Optional.of((Prefix[]) commandClass
                    .getField("AUTO_COMPLETE_PARAMETERS")
                    .get(null));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return Optional.empty();
        }
    }


    /**
     * Returns the longest matched command class if found a match; otherwise returns {@code Optional.empty}.
     * Matching is based on {@code AUTO_COMPLETER_INDICATOR} field defined in command classes.
     * For example, for user input "order add", if OrderCommand class and AddOrderCommand classes have
     * indicator "order" and "order add" respectively, returns AddOrderCommand class because it has a longer match.
     *
     * @param userInput to find a match.
     */
    private Optional<Class<? extends Command>> getMatchedCommandClass(String userInput) {
        String userInputWithoutConsecutiveSpaces = userInput.strip().replaceAll(" +", " ");

        Class<? extends Command> maxMatchedCommandClass = null;
        int maxLength = 0;
        for (Class<? extends Command> commandClass : commandClasses) {
            String indicator = getCommandIndicator(commandClass);
            if (indicator.isEmpty()) {
                continue;
            }

            if (userInputWithoutConsecutiveSpaces.startsWith(indicator) && indicator.length() > maxLength) {
                maxLength = indicator.length();
                maxMatchedCommandClass = commandClass;
            }
        }


        if (maxMatchedCommandClass != null) {
            return Optional.of(maxMatchedCommandClass);
        } else {
            return Optional.empty();
        }


    }

    /**
     * Represents the details of a user input, including the text and caret position. Also contains some
     * utility methods.
     * Guarantees: immutable; is valid as declared in {@link #isValidInput(String, int)}
     */
    public static class Input {
        public static final String MESSAGE_CONSTRAINS = "Input text should be non-null "
            + "and caret position should be between 0 and input text's length";

        /**
         * The text input from user.
         */
        public final String text;

        /**
         * The position of the text insertion point.
         */
        public final int caretPosition;

        /**
         * Creates a {@code Input}.
         *
         * @param text The text input from user. Empty input is acceptable.
         * @param caretPosition An integer between 0 and the length of {@code userInputString}。
         * @throws ParseException if caret position is invalid。
         */
        public Input(String text, int caretPosition) throws ParseException {
            requireNonNull(text);
            if (!isValidInput(text, caretPosition)) {
                throw new ParseException(MESSAGE_CONSTRAINS);
            }

            this.text = text;
            this.caretPosition = caretPosition;
        }

        /**
         * Returns true if the given {@code text} and {@code caretPosition} is a valid {@code Input}.
         */
        public boolean isValidInput(String text, int caretPosition) {
            requireNonNull(text);
            return caretPosition >= 0 && caretPosition <= text.length();
        }

        /**
         * Returns the word at {@code caretPosition} in input text. If no word is present, returns an empty string.
         * <p>
         * Words are substrings of the inout text separated by one or more spaces. They can contain any characters
         * (including escape characters) except spaces.
         * <p>
         * For example, "order add   -name    " is made up of three words: "order", "add" and "-name".
         * At caret positions 0~5, returns "order";
         * At caret positions 6~9, returns "add"
         * At caret positions 12~17, returns "-name",
         * At other positions, returns "".
         */
        public String getCurrentWord() {
            return text.substring(getSelectionStart(text, caretPosition),
                getSelectionEnd(text, caretPosition) + 1);
        }

        /**
         * Returns a {@code Input} that replaces the word in {@link #getCurrentWord()} with {@code newWord}.
         * Also updates caret position to the end of the new word.
         */
        public Input replaceCurrentWord(String newWord) {
            requireNonNull(newWord);
            String beforeCurrent = text.substring(0, getSelectionStart(text, caretPosition));
            String afterCurrent = text.substring(getSelectionEnd(text, caretPosition) + 1);
            return new Input(beforeCurrent + newWord + afterCurrent,
                caretPosition - getCurrentWord().length() + newWord.length());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Input that = (Input) o;
            return caretPosition == that.caretPosition
                && Objects.equals(text, that.text);
        }

        @Override
        public int hashCode() {
            return Objects.hash(text, caretPosition);
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
    }
}
