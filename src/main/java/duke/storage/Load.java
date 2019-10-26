package duke.storage;

import duke.commons.fileIO.FilePaths;
import duke.commons.fileIO.FileUtil;
import duke.logic.autocorrect.Autocorrect;
import duke.commons.exceptions.DukeException;
import duke.model.MealList;
import duke.model.user.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static duke.commons.fileIO.FilePaths.FILE_PATH_NAMES;

/**
 * This object is in charge of all reading from save operations.
 */
public class Load {
    private BufferedReader bufferedReader = null;
    private BufferedWriter bufferedWriter = null;
    private String line;
    private static FilePaths filePaths;
    private static final boolean useResourceAsBackup = false;

    public Load() {
        filePaths = new FilePaths();
    }

    /**
     * The function will act to load txt file specified by the filepath, parse it and store it in a new task ArrayList
     * to be added in that MealList.
     * @throws DukeException if either the object is unable to open file or it is unable to read the file
     */
    public void loadFile(MealList meals, String fileStr) throws DukeException {
        bufferedReader = FileUtil.readResourceFile(fileStr);
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

    public User loadUser() throws DukeException {
        String userFileStr = filePaths.getFilePathStr(FILE_PATH_NAMES.FILE_PATH_USER_FILE);
        User tempUser;
        bufferedReader = FileUtil.readResourceFile(userFileStr);
        try {
            line = bufferedReader.readLine();
            tempUser = LoadLineParser.parseUser(line);
            while ((line = bufferedReader.readLine()) != null) {
                String[] splitWeightInfo = line.split("\\|");
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
            String autocorrectFileStr = filePaths.getFilePathStr(FILE_PATH_NAMES.FILE_PATH_AUTOCORRECT_FILE);
            bufferedReader = FileUtil.readResourceFile(autocorrectFileStr);
            while ((line = bufferedReader.readLine()) != null) {
                autocorrect.load(line.trim());
            }
            bufferedReader.close();
        } catch (Exception e) {
            throw new DukeException(e.getMessage());
        }
    }

    public void loadHelp(ArrayList<String> lines, String specifiedHelp) throws DukeException {
        File helpFile = LoadHelpUtil.load(specifiedHelp);
        try {
            bufferedReader = new BufferedReader(new FileReader(helpFile));
        } catch (Exception e) {
            throw new DukeException("Unable to access help file");
        }
        try {
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            throw new DukeException("Error reading help file");
        }
    }

}
