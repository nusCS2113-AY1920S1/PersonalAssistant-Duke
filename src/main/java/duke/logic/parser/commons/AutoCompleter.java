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
 * A logic component that auto-completes user inputs.
 * It is able to complete two types of entities: a) command words, such as "order" and "add";
 * b) prefixes, such as "-by" and "-item".
 *
 * <p>To add AutoCompleter support for your command, first, add you command class to AutoCompleter using
 * {@link #addCommandClass(Class)}.
 * Then, check if the {@link Input} is auto-completable by calling {@link #isAutoCompletable(Input)} ()}.
 * Finally, if the input is completable, get completion result by calling {@link #complete()}. If there are multiple
 * results available, the subsequent invocation of {@link #complete()} will return the next possible result.
 * If all results have been returned, invocation of {@link #complete()} will go cyclic to return the first result.
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
     * Attempts to find auto-complete suggestions for the {@code input}.
     * @return true if {@code input} has one or more auto-complete suggestion(s).
     */
    public Boolean isAutoCompletable(Input input) {
        requireNonNull(input);

        //Blank text cannot be auto-completed.
        if (isBlank(input)) {
            return false;
        }

        //If the current input is the same as the input pointed by suggestionPointer, it is guaranteed
        //to be auto-completable.
        if (isSameAsPrevious(input)) {
            return true;
        }

        List<String> suggestionWords = generateSuggestedWords(input.text, input.getCurrentWord());

        logger.info(String.format("Found %s suggestion(s)", suggestions.size()));

        //No available suggestions.
        if (suggestionWords.isEmpty()) {
            return false;
        }

        generateSuggestedInputs(input, suggestionWords);

        return true;
    }

    /**
     * Returns a {@code Input} object representing an auto-complete result.
     *
     * <p>If there is only one available completion result, returns that result.
     * If there are multiple results available, first invocation  of this method returns the
     * first result. The subsequent invocation will return the next one.
     * If all results have been returned, invocation of this method will go cyclic to return the first result.
     *
     * <p>For example, if the current input has two suggestions "add" and "all" (caret positions omitted),
     * first call to the method returns the "add" input and second call returns "all" input.
     * The third call returns "add" input again.
     *
     * @throws ParseException if there is no suggestion available.
     */
    public Input complete() throws ParseException {
        if (suggestions.isEmpty()) {
            logger.warning("No suggestions are available for user input.");
            throw new ParseException();
        }

        logger.info(String.format("New suggestion [%s] found for user input.",
            suggestions.get(suggestionPointer).text
        ));

        moveForwardSuggestionPointer();

        return suggestions.get(suggestionPointer);
    }

    /**
     * Adds a command class to auto-complete its command word and prefixes.
     * Duplicate command classes are not allowed.
     *
     * <p>To auto-complete arguments, the command should have fields {@code String AUTO_COMPLETION_INDICATOR} and
     * {@code Prefix[] AUTO_COMPLETION_ARGUMENTS}. The namings and types should be precise for auto-completer to
     * function properly.
     *
     * <p>{@code String AUTO_COMPLETION_INDICATOR} specifies when auto-completer should complete the arguments.
     *                                          For example, "order add", "order remove".
     *                                          Auto-completer only completes the arguments when this field is present
     *                                          in the beginning of user input.
     * {@code Prefix[] AUTO_COMPLETION_ARGUMENTS} are prefixes used by the command that you wish to auto-complete.
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

    private void moveForwardSuggestionPointer() {
        suggestionPointer = (suggestionPointer + 1) % suggestions.size();
    }

    private boolean isBlank(Input input) {
        return input.text.isBlank()
            || input.getCurrentWord().isBlank();
    }

    private boolean isSameAsPrevious(Input input) {
        return !suggestions.isEmpty() && input.equals(suggestions.get(suggestionPointer));
    }

    //======== Methods to generate suggestions ========
    /*
      Methods below are used to generate prefix and command word suggestions for user inputs.
     */

    /**
     * Returns all the possible suggested inputs based on {@code suggestedWords} for {@code input}.
     */
    private void generateSuggestedInputs(Input input, List<String> suggestedWords) {
        this.suggestions = suggestedWords.stream().distinct().map(
            input::replaceCurrentWord)
            .collect(Collectors.toList());
        suggestionPointer = 0;
    }

    /**
     * Returns all the possible suggested words for {@code currentWord} based on {@code userInput}.
     * A suggested word can be a command word, such as "order", or a prefix, such as "-item".
     * If there is no available suggested word, returns an empty list.
     */
    private List<String> generateSuggestedWords(String userInput, String currentWord) {
        assert (userInput != null);

        List<String> suggestions = new ArrayList<>();
        Optional<Class<? extends Command>> matchedCommandClass = getMatchedCommandClass(userInput);
        matchedCommandClass.ifPresent(matchedClass ->
            suggestions.addAll(generatePrefixSuggestions(currentWord, matchedClass)));

        suggestions.addAll(generateCommandWordSuggestions(currentWord));

        return suggestions;
    }

    /**
     * Returns a list of suggested command words based on the incomplete {@code toComplete}.
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
     * Returns a list of suggested prefixes based on the incomplete {@code toComplete}.
     */
    private List<String> generatePrefixSuggestions(String toComplete, Class<? extends Command> commandClass) {
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
     * Returns the longest matched command class if found a match; otherwise returns {@code Optional.empty()}.
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

    //=============== Field utilities ================
    /*
      Methods below are used to retrieve field values from command classes using Reflection API.
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
     * Represents the details of user input.
     * An {@code Input} object encapsulates the details of user input in command box,
     * including the text and caret position.
     * It also contains some utility methods.
     * Guarantees: immutable; is valid as declared in {@link #isValidInput(String, int)}
     */
    public static class Input {
        public static final String MESSAGE_CONSTRAINTS = "Input text should be non-null "
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
                throw new ParseException(MESSAGE_CONSTRAINTS);
            }

            this.text = text;
            this.caretPosition = caretPosition;
        }

        /**
         * Returns true if {@code text} is non-null and {@code caretPosition} is
         * between 0 and text's length.
         */
        public boolean isValidInput(String text, int caretPosition) {
            requireNonNull(text);
            return caretPosition >= 0 && caretPosition <= text.length();
        }

        /**
         * Returns the word at {@code caretPosition} in input text. If no word is present, returns an empty string.
         *
         * <p>Words are substrings of the inout text separated by one or more spaces. They can contain any characters
         * (including escape characters) except spaces.
         *
         * <p>For example, "order add   -name    " is made up of three words: "order", "add" and "-name".
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
