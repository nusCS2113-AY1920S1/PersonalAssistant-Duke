package javacake;

import javacake.topics.SubListTopic;

import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

public class ProgressStack {
    private String defaultFilePath = "content/MainList";
    private static String currentFilePath = "content/MainList";
    private Stack<Integer> currentProgress = new Stack<Integer>();
    private static ArrayList<String> filePathQueries = new ArrayList<String>();
    private File folder;
    private static File[] listOfFiles;
    public ProgressStack() {

    }

    public void loadFiles(String filePath) {
        folder = new File(filePath);
        listOfFiles = folder.listFiles();
    }

    public void setDefaultFilePath() {
        currentFilePath = defaultFilePath;
    }

    public void insertQueries(String currentFilePath) {
        clearQueries();
        loadFiles(currentFilePath);
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                filePathQueries.add(listOfFile.getName());
            } else if (listOfFile.isDirectory()) {
                filePathQueries.add(listOfFile.getName());
            }
        }

    }

    public void clearQueries() {
        filePathQueries.clear();
    }

    /**
     * Forces the progress to MainList.
     */
    public void forceToMainList() {
        if (checkProgress() == 0) {
            currentProgress.push(1);
        } else {
            // only push progress when current screen is at main screen.
            while (checkProgress() > 1) {
                forceClearProgress();
                // when list command is entered, progress is automatically cleared to main list.
                // checkProgress stack size is forced to 1.
            }
        }

    }

    /**
     * Goes to List Index 1.
     */
    public void mainListToListIndex1() {
        if (checkProgress() == 1) {
            currentProgress.push(1);
        }
    }

    /**
     * Goes to List Index 2.
     */
    public void mainListToListIndex2() {
        if (checkProgress() == 1) {
            currentProgress.push(2);
        }
    }

    /**
     * Goes to List Index 3.
     */
    public void mainListToListIndex3() {
        if (checkProgress() == 1) {
            currentProgress.push(3);
        }
    }

    /**
     * Goes to Main List.
     */
    public void listIndexToMainList() {
        if (checkProgress() == 2) {
            currentProgress.pop();
        }
    }

    /**
     * Goes to Sub List Index 1.
     */
    public void listIndexToSubList() {
        if (checkProgress() == 2) {
            currentProgress.push(1);
        }
    }

    /**
     * Clears progress from the stack.
     */
    public void forceClearProgress() {
        currentProgress.pop();
    }

    /**
     * Checks the current location in the programme stack.
     * @return the size of the stack which indicates the location.
     */
    public int checkProgress() {
        return currentProgress.size();
    }

    /**
     * Checks for the specific branch in the programme stack.
     * Each number indicates a branch based on the index of the list.
     * @return the number of the branch or the index of a particular list.
     */
    public int checkProgressState() {
        return currentProgress.peek();
    }

    /**
     * Used only for BACK command.
     * To check for the previous state stored in the stack.
     * @return the number of the branch or the index of a particular list
     *         in the previous state.
     */
    public int checkPreviousState() {
        clearCurrentState();
        return checkProgressState();
    }

    /**
     * Used only for BACK command.
     * To clear the current state of the stack.
     */
    public void clearCurrentState() {
        currentProgress.pop();
    }

}
