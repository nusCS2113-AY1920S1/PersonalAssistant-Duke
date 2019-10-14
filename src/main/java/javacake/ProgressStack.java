package javacake;

import javacake.topics.SubListTopic;

import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Stack;

public class ProgressStack {
    private String defaultFilePath = "content/MainList";
    private static String currentFilePath = "content/MainList";
    private Stack<Integer> currentProgress = new Stack<Integer>();
    private static ArrayList<String> filePathQueries = new ArrayList<String>();
    private File folder;
    private File[] listOfFiles;
    private static boolean isDirectory = true;
    public ProgressStack() {

    }

    public void loadFiles(String filePath) {
        folder = new File(filePath);
        listOfFiles = folder.listFiles();
    }

    public void setDefaultFilePath() {
        currentFilePath = defaultFilePath;
    }

    public String gotoFilePath(int index) {
        printFiles();
        return filePathQueries.get(index);
    }

    public void updateFilePath(String updatedPath) {
        currentFilePath += ("/" + updatedPath);
    }

    public void printFiles() {
        int y = 1;
        for (String x : filePathQueries) {
            System.out.println(y + ". " + x);
            y++;
        }
    }



    public void displayQueries() {
        System.out.println("Here are the " + filePathQueries.size() + " subtopics available!");
        for (String queries : filePathQueries) {
            System.out.println(queries);
        }
        System.out.println("Key in the index to learn more about the topic!");
    }

    public void readQuery() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(currentFilePath));
        while (br.readLine() != null) {
            System.out.println(br.readLine());
        }
    }

    public boolean containsDirectory() {
        if (isDirectory) return true;
        return false;
    }
    public void processQueries() throws DukeException {
        insertQueries();
        try {
            if (isDirectory) displayQueries();
            else {
                readQuery();
            }
        } catch (IOException e) {
            throw new DukeException(e.getMessage());
        }
    }

    public void insertQueries() {
        clearQueries();
        loadFiles(currentFilePath);
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                filePathQueries.add(listOfFile.getName());
                isDirectory = false;
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
