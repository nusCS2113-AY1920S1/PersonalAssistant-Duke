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

        String currentWord = getCurrentWord(commandText, caretPosition);
        if (currentWord.isBlank()) {
            return currentState;
        }

        String completedWord = getCompletedWord(currentState);

        UserInputState newState = new UserInputState(
                replaceWord(commandText, caretPosition, completedWord),
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

    private String getCompletedWord(UserInputState state) {
        String completedWord;
        if (state.equals(previousUserInputState)) {
            completedWord = autoCompletionResults.get(resultPointer);
        } else {
            completeWord(getCurrentWord(state.userInputString, state.caretPosition));
            resultPointer = 0;
            completedWord = autoCompletionResults.get(0);
        }
        resultPointer = (resultPointer + 1) % autoCompletionResults.size();
        return completedWord;
    }

    private int getSelectionStart(String commandText, int caretPosition) {
        int selectionStart = commandText.lastIndexOf(" ", caretPosition - 1);
        if (selectionStart == -1) {
            selectionStart = 0;
        } else {
            selectionStart += 1;
        }
        return selectionStart;
    }

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
            if (commandWord.startsWith(toComplete)) {
                autoCompletionResults.add(commandWord);
            }
        }

        for (Prefix prefix : prefixes) {
            if (prefix.getPrefix().startsWith(toComplete)) {
                autoCompletionResults.add(prefix.getPrefix());
            }
        }

        if (autoCompletionResults.isEmpty()) {
            autoCompletionResults.add(toComplete);
        }
    }

    private String replaceWord(String commandText, int caretPosition, String newWord) {
        String beforeCurrent = commandText.substring(0, getSelectionStart(commandText, caretPosition));
        String afterCurrent = commandText.substring(getSelectionEnd(commandText, caretPosition) + 1);
        return beforeCurrent + newWord + afterCurrent;
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
