package duke.storage;

import duke.commons.file.FilePaths;
import duke.commons.file.FileUtil;
import duke.logic.autocorrect.Autocorrect;
import duke.commons.exceptions.DukeException;
import duke.model.meal.MealList;
import duke.model.user.User;
import duke.model.wallet.Wallet;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static duke.commons.file.FilePaths.FilePathNames;

/**
 * This object is in charge of all reading from save operations.
 */
public class Load {
    private BufferedReader bufferedReader = null;
    private String lineStr;
    private static FilePaths filePaths;
    // Flag to set if jar resource should be called if user file does not exist in host system.
    private static final boolean useResourceAsBackup = true;

    public Load() {
        filePaths = new FilePaths();
    }

    /**
     * The function will act to load txt file specified by the filepath, parse it and store it in a new task ArrayList
     * to be added in that MealList.
     * @throws DukeException if either the object is unable to open file or it is unable to read the file
     */
    public void loadFile(MealList meals, String fileStr) throws DukeException {
        bufferedReader = FileUtil.readFile(fileStr, useResourceAsBackup);
        try {
            List<String> lines = bufferedReader.lines().collect(Collectors.toList());
            LoadLineParser.parseMealList(meals, lines);
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            throw new DukeException("Unable to open file");
        } catch (IOException e) {
            throw new DukeException("Error reading file. Unable to close file.");
        }
    }

    public void loadTransactions(Wallet wallet) throws DukeException {
        String transactionFilePathStr = filePaths.getFilePathStr(FilePathNames.FILE_PATH_TRANSACTION_FILE);
        bufferedReader = FileUtil.readFile(transactionFilePathStr, useResourceAsBackup);
        try {
            lineStr = bufferedReader.readLine();
            wallet.setAccountBalance(lineStr);
            while ((lineStr = bufferedReader.readLine()) != null) {
                LoadLineParser.parseTransactions(lineStr, wallet);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User loadUser() throws DukeException {
        String userFileStr = filePaths.getFilePathStr(FilePathNames.FILE_PATH_USER_FILE);
        User tempUser;
        bufferedReader = FileUtil.readFile(userFileStr, useResourceAsBackup);
        try {
            lineStr = bufferedReader.readLine();
            tempUser = LoadLineParser.parseUser(lineStr);
            while ((lineStr = bufferedReader.readLine()) != null) {
                String[] splitWeightInfo = lineStr.split("\\|");
                tempUser.setWeight(Integer.parseInt(splitWeightInfo[1]), splitWeightInfo[0]);
            }
            bufferedReader.close();
            return tempUser;

        } catch (Exception e) {
            throw new DukeException(e.getMessage());
        }
    }

    public void loadAutoCorrect(Autocorrect autocorrect) throws DukeException {
        try {
            String autocorrectFileStr = filePaths.getFilePathStr(FilePathNames.FILE_PATH_AUTOCORRECT_FILE);
            bufferedReader = FileUtil.readFile(autocorrectFileStr, useResourceAsBackup);
            while ((lineStr = bufferedReader.readLine()) != null) {
                autocorrect.load(lineStr.trim());
            }
            bufferedReader.close();
        } catch (Exception e) {
            throw new DukeException(e.getMessage());
        }
    }

    public void loadHelp(ArrayList<String> lines, String specifiedHelp) throws DukeException {
        BufferedReader bufferedReader = LoadHelpUtil.load(specifiedHelp);
        try {
            while ((lineStr = bufferedReader.readLine()) != null) {
                lines.add(lineStr);
            }
            bufferedReader.close();
        } catch (IOException e) {
            throw new DukeException("Error reading help file");
        }
    }

}
