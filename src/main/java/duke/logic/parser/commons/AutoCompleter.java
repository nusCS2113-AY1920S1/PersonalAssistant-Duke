package duke.logic.parser.commons;

import duke.logic.command.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A logic component that auto-completes user inputs based on pre-defined command words and prefixes.
 */
public class AutoCompleter {
    private List<Class<? extends Command>> commandClasses;
    private List<Prefix> prefixes;

    private Boolean completable;
    private UserInputState previousUserInputState;
    private List<String> autoCompletionResults;
    private int resultPointer;

    /**
     * Creates an {@code AutoCompleter}.
     */
    public AutoCompleter() {
        commandClasses = new ArrayList<>();
        prefixes = new ArrayList<>();

        previousUserInputState = new UserInputState("$", -1);
        autoCompletionResults = new ArrayList<>();
        resultPointer = 0;
    }

    /**
     * Returns a {@code UserInputState} specifying the state after auto completion based on {@code CurrentState}
     * If the current state is not auto-completable, returns the same state as {@code currentState};
     * If the current state is the same as the previous state,
     * returns a new {@code UserInputState} with the next possible completion word.
     */
    public UserInputState getAutoCompletion(UserInputState currentState) {
        String commandText = currentState.userInputString;
        int caretPosition = currentState.caretPosition;
        if (commandText.isBlank()) {
            return currentState;
        }

        String[] words = splitCommand(commandText);
        int currentWordIndex = getCurrentWordIndex(words, caretPosition);
        String currentWord = words[currentWordIndex];

        String completedWord;
        if (currentState.equals(previousUserInputState)) {
            completedWord = autoCompletionResults.get(resultPointer);
        } else {
            completeWord(currentWord);
            resultPointer = 0;
            completedWord = autoCompletionResults.get(0);
        }
        resultPointer = (resultPointer + 1) % autoCompletionResults.size();

        UserInputState newState = new UserInputState(
                replaceWord(words, currentWordIndex, completedWord),
                getNewCaretPosition(caretPosition, currentWord, completedWord)
        );

        previousUserInputState = new UserInputState(newState);

        return newState;
    }

    public void addCommand(Class<? extends Command> commandClass) {
        if (!commandClasses.contains(commandClass)) {
            this.commandClasses.add(commandClass);
        }
    }

    public void addPrefix(Prefix prefix) {
        if (!this.prefixes.contains(prefix)) {
            this.prefixes.add(prefix);
        }
    }

    private String[] splitCommand(String commandText) {
        return commandText.split(" ");
    }

    private int getCurrentWordIndex(String[] commandWords, int caretPosition) {
        int length = 0;
        for (int i = 0; i < commandWords.length; i++) {
            String word = commandWords[i];
            length = length + word.length() + 1;
            if (length > caretPosition) {
                return i;
            }
        }
        return -1;
    }

    private void completeWord(String toComplete) {
        autoCompletionResults.clear();

        for (Class<? extends Command> commandClass : commandClasses) {
            String commandWord;
            try {
                commandWord = (String) commandClass.getField("COMMAND_WORD").get(null);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                autoCompletionResults.add(toComplete);
                return;
            }
            assert commandWord != null;
            if (commandWord.indexOf(toComplete) == 0) {
                autoCompletionResults.add(commandWord);
            }
        }

        for (Prefix prefix : prefixes) {
            if ((prefix.getPrefix()).indexOf(toComplete) == 0) {
                autoCompletionResults.add(prefix.getPrefix());
            }
        }

        if (autoCompletionResults.isEmpty()) {
            autoCompletionResults.add(toComplete);
        }
    }

    private String replaceWord(String[] words, int toReplaceIndex, String newWord) {
        words[toReplaceIndex] = newWord;
        StringBuilder stringBuilder = new StringBuilder();
        for (String word : words) {
            stringBuilder.append(word);
            stringBuilder.append(" ");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    private int getNewCaretPosition(int oldPosition, String oldWord, String newWord) {
        return oldPosition - oldWord.length() + newWord.length();
    }

    /**
     * Represents the details of a user input.
     */
    public static class UserInputState {
        public final String userInputString;
        public final int caretPosition;

        public UserInputState(String userInputString, int caretPosition) {
            this.userInputString = userInputString;
            this.caretPosition = caretPosition;
        }

        public UserInputState(UserInputState toCopy) {
            this.userInputString = toCopy.userInputString;
            this.caretPosition = toCopy.caretPosition;
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
