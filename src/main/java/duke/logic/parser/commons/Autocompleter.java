package duke.logic.parser.commons;

import duke.logic.command.Command;

import java.util.ArrayList;
import java.util.List;

/**
 * A logic component that auto-completes user inputs based on pre-defined command words and prefixes
 */
public class Autocompleter {
    private List<Class<? extends Command>> commandClasses;
    private List<Prefix> prefixes;

    public Autocompleter() {
        commandClasses = new ArrayList<>();
        prefixes = new ArrayList<>();
    }

    public CompletedUserInput getAutoCompletion(String commandText, int caretPosition) {
        String[] words = splitCommand(commandText);
        int currentWordIndex = getCurrentWordIndex(words, caretPosition);
        if (currentWordIndex == -1) {
            return new CompletedUserInput(commandText, caretPosition);
        }
        String currentWord = words[currentWordIndex];

        String completedWord = completeWord(currentWord);

        return new CompletedUserInput(
                replaceWord(words, currentWordIndex, completedWord),
                getNewCaretPosition(caretPosition, currentWord, completedWord)
        );
    }

    public void addCommand(Class<? extends Command> commandClass) {
        this.commandClasses.add(commandClass);
    }

    public void addPrefix(Prefix prefix) {
        this.prefixes.add(prefix);
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

    private String completeWord(String toComplete) {
        for (Class<? extends Command> commandClass : commandClasses) {
            String commandWord;
            try {
                commandWord = (String) commandClass.getField("COMMAND_WORD").get(null);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                return toComplete;
            }
            assert commandWord != null;
            if (commandWord.indexOf(toComplete) == 0) {
                return commandWord;
            }
        }

        for (Prefix prefix : prefixes) {
            if ((prefix.getPrefix()).indexOf(toComplete) == 0) {
                return prefix.getPrefix();
            }
        }

        return toComplete;
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

    public static class CompletedUserInput {
        public final String userInputString;
        public final int caretPosition;

        public CompletedUserInput(String userInputString, int caretPosition) {
            this.userInputString = userInputString;
            this.caretPosition = caretPosition;
        }
    }
}
