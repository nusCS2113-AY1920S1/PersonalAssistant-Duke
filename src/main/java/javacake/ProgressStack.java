package javacake;

import javacake.topics.SubListTopic;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class ProgressStack {
    private File f1 = null;
    private String defaultFilePath = null;
    private String currentFilePath = null;

    private static ArrayList<String> filePathQueries = new ArrayList<>();
    private File[] listOfFiles;
    private static boolean isDirectory = true;

    private Stack<Integer> currentProgress = new Stack<Integer>();
    public ProgressStack() throws DukeException {
        try {
            f1 = new File(getClass().getResource("/content/MainList").toURI());
        } catch (URISyntaxException e) {
            throw new DukeException("Unable to load file directory");
        }
        System.out.println(f1.getAbsolutePath());
        defaultFilePath = f1.getPath();
        currentFilePath = f1.getPath();
    }

    /**
     * Stores all files in the filePath into listOfFiles.
     * @param filePath path to the root directory.
     */
    public void loadFiles(String filePath) throws DukeException {
        File folder = new File(filePath);
        try {
            listOfFiles = folder.listFiles();
            assert listOfFiles != null;
            Arrays.sort(listOfFiles); //in case the files stored locally are not in alphabetical order
        } catch (NullPointerException e) {
            throw new DukeException("Content not found!" + "\nPls key 'back' or 'list' to view previous content!");
        }
    }

    /**
     * Method is only invoked when List command is called.
     */
    public void setDefaultFilePath() {
        currentFilePath = defaultFilePath;
    }

    public String getFullFilePath() {
        return currentFilePath;
    }

    /**
     * Method is invoked when GoTo command is called.
     * Based on the index, return the particular filePath.
     * @param index Index of the new path found in filePathQueries.
     * @return the particular filePath based on the input index.
     */
    public String gotoFilePath(int index) throws  DukeException {
        //printFiles();
        try {
            return filePathQueries.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException(e.getMessage() + "\n Pls key 'back' or 'list' to view previous content");
        }
    }

    /**
     * Update the currentFilePath by concatenating the updatedPath.
     * updatedPath is given by gotoFilePath method.
     * @param updatedPath particular path to be updated into currentFilePath.
     */
    public void updateFilePath(String updatedPath) {
        currentFilePath += ("/" + updatedPath);
    }

    public void backToPreviousPath() {
        File currentFile = new File(currentFilePath);
        if (!currentFilePath.equals(defaultFilePath)) {
            if (currentFile.isDirectory()) {
                currentFilePath = gotoParentFilePath(currentFilePath);
            } else {
                currentFilePath = gotoParentFilePath(gotoParentFilePath(currentFilePath));
            }
        }
    }

    public String gotoParentFilePath(String filePath) {
        String[] filesCapture = filePath.split("/");
        StringBuilder reducedFilePath = new StringBuilder();
        for (int i = 0; i < filesCapture.length -1; i++) {
            reducedFilePath.append(filesCapture[i]).append("/");
        }
        String finalTrim = reducedFilePath.toString();
        finalTrim = finalTrim.substring(0, finalTrim.length() -1);
        return finalTrim;
    }

    public void printFiles() {
        int y = 1;
        for (String x : filePathQueries) {
            System.out.println(y + ". " + x);
            y++;
        }
    }

    /**
     * Displays the all directories found in currentFilePath.
     */
    public String displayDirectories() {
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the ").append(filePathQueries.size()).append(" subtopics available!\n");
        for (String queries : filePathQueries) {
            sb.append(queries).append("\n");
        }
        sb.append("Key in the index to learn more about the topic!").append("\n");
        return sb.toString();
    }

    /**
     * Reads the content in content text file.
     * @throws IOException When the text file in currentFilePath is not found.
     */
    public String readQuery() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(currentFilePath));
        StringBuilder sb = new StringBuilder();
        String sentenceRead;
        while ((sentenceRead = br.readLine()) != null) {
            sb.append(sentenceRead).append("\n");
        }
        return sb.toString();
    }

    /**
     * Checks current directory contains directory or text files.
     * @return true if current directory contains directory.
     */
    public boolean containsDirectory() {
        if (isDirectory) return true;
        return false;
    }


    public String processQueries() throws DukeException {
        insertQueries();
        try {
            if (isDirectory) return displayDirectories();
            else {
                return readQuery();
            }
        } catch (IOException e) {
            throw new DukeException(e.getMessage());
        }
    }

    /**
     * Clears all file paths in filePathQueries.
     * Load all files in currentFilePath.
     * Update isDirectory if current directory contains directories.
     * Adds new list of file names in filePathQueries to be processed.
     */
    public void insertQueries() throws DukeException {
        clearQueries();
        loadFiles(currentFilePath);
        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                isDirectory = false;
                filePathQueries.add(listOfFile.getName());
            } else if (listOfFile.isDirectory()) {
                isDirectory = true;
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