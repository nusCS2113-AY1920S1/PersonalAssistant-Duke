package javacake.storage;

import javacake.exceptions.CakeException;
import javacake.ui.MainWindow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Profile {
    private static final Logger LOGGER = Logger.getLogger(Profile.class.getPackageName());
    private String filepath = "data";
    private String username;
    private ArrayList<Integer> overalltopicsDone = new ArrayList<>();
    private ArrayList<Integer> individualTopicsDone = new ArrayList<>();
    private int totalNumOfMainTopics = 4;
    private int levelsOfDifficulty = 3;
    private static boolean isResetFresh = false;
    public boolean isCli = true;

    public Profile() throws CakeException {
        this("data");
    }

    /**
     * Constructor for profile.
     * @param filename String of filepath
     * @throws CakeException when unable to create profile
     */
    public Profile(String filename) throws CakeException {
        LOGGER.setUseParentHandlers(true);
        LOGGER.setLevel(Level.INFO);
        LOGGER.entering(getClass().getName(), "Profile");
        filepath = filename;
        filepath += "/save/savefile.txt";
        File file = new File(filepath);
        LOGGER.info("Filepath[p]: " + filepath);
        try {
            try {
                initialiseUser(file, filename);

            } catch (IOException e) {
                System.out.println("before reader");
                throw new CakeException("Failed to create new file");
            }
            readColorConfig();

            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String line;
            int count = -1;
            while ((line = reader.readLine()) != null) {
                if (count == -1) {
                    username = line;
                } else if (count < 4) {
                    int tempOverallGrade;
                    try {
                        tempOverallGrade = Integer.parseInt(line);
                    } catch (NumberFormatException e) {
                        throw new CakeException("NOT A NUMBER: " + line);
                    }
                    checksumOverallGrade(tempOverallGrade);
                    overalltopicsDone.add(tempOverallGrade);
                } else {
                    int tempIndieGrade;
                    try {
                        tempIndieGrade = Integer.parseInt(line);
                    } catch (NumberFormatException e) {
                        throw new CakeException("NOT A NUMBER: " + line);
                    }
                    checksumIndiGrade(tempIndieGrade);
                    individualTopicsDone.add(Integer.parseInt(line));
                }
                ++count;
            }
            reader.close();

            LOGGER.info("Checksum Line Count: " + count);
            overallChecksum(count);

        } catch (IOException e) {
            //System.out.println("after reader");
            LOGGER.severe("Reader failed[p]");
            throw new CakeException("Failed to close reader");
        }
        LOGGER.exiting(getClass().getName(), "Profile");
    }

    private void overallChecksum(int count) throws CakeException {
        //checks total number of lines, must be == 16
        if (count != 16) {
            System.out.println("fk");
            throw new CakeException("PROFILE HAS BEEN MODIFIED");
        }
        //Check summation sum
        checkSumTotalSum(0, 0, 3);
        checkSumTotalSum(1, 3, 6);
        checkSumTotalSum(2, 6, 9);
        checkSumTotalSum(3, 9, 12);
    }

    private void checkSumTotalSum(int expectedIdx, int lowB, int upB) throws CakeException {
        int totalMarks = 0;
        for (int i = lowB; i < upB; ++i) {
            totalMarks += individualTopicsDone.get(i);
        }
        if (totalMarks != overalltopicsDone.get(expectedIdx)) {
            throw new CakeException("CHECKSUM " + expectedIdx + " FAILED!");
        }
    }

    private void checksumOverallGrade(int grade) throws CakeException {
        if (grade < 0 || grade > 15) {
            throw new CakeException("Error in checksum: OVERALL");
        }
    }

    private void checksumIndiGrade(int grade) throws CakeException {
        if (grade < 0 || grade > 5) {
            throw new CakeException("Error in checksum: INDI");
        }
    }

    /**
     * Method to hard reset profile.
     */
    public void resetProfile() {
        isResetFresh = true;
        File file = new File(filepath);
        if (file.exists()) {
            file.delete();
            System.out.println("deleting: " + file.getPath());
        }
    }

    public String getUsername() {
        return username;
    }

    /**
     * Method to overwrite Username.
     * @param oldname old username
     * @throws CakeException when unable to write progress
     */
    public void overwriteName(String oldname) throws CakeException {
        username = oldname;
        writeProgress();
    }

    /**
     * Method to set topic score.
     * @param contentIdx idx of content
     * @throws CakeException when unable to write progress
     */
    public void setOverallMarks(int contentIdx, int marks) throws CakeException {
        overalltopicsDone.set(contentIdx, marks);
        writeProgress();
    }

    public void setIndividualMarks(int contentIdx, int marks) throws CakeException {
        individualTopicsDone.set(contentIdx, marks);
        writeProgress();
    }


    /**
     * Method to get topic score.
     * @param contentIdx idx of content
     * @return score of the specified topic
     */
    public int getOverallContentMarks(int contentIdx) {
        return overalltopicsDone.get(contentIdx);
    }

    public int getIndividualContentMarks(int contentIdx) {
        return individualTopicsDone.get(contentIdx);
    }

    /**
     * Method to get total progress.
     * @return total progress status
     */
    public int getTotalProgress() {
        int count = 0;
        for (int i : overalltopicsDone) {
            count += i;
        }
        return count;
    }

    /**
     * Method to write config file.
     * @param isLight whether isLight mode is on
     * @throws CakeException when unable to create file
     */
    public void writeColorConfig(boolean isLight) throws CakeException {
        File configFile = new File("data/colorconfig/color.txt");
        try {
            if (!configFile.getParentFile().getParentFile().exists()) {
                configFile.getParentFile().getParentFile().mkdirs();
                configFile.getParentFile().mkdirs();
                configFile.createNewFile();
            } else if (!configFile.getParentFile().exists()) {
                configFile.getParentFile().mkdirs();
                configFile.createNewFile();
            } else {
                configFile.createNewFile();
            }

            PrintWriter out = new PrintWriter(configFile.getPath());
            System.out.println(configFile.getPath());
            if (isLight) {
                out.println("1");
            } else {
                out.println("0");
            }
            out.close();
        } catch (IOException e) {
            throw new CakeException("Failed to change color!");
        }
    }

    private void readColorConfig() throws CakeException {
        File configFile = new File("data/colorconfig/color.txt");
        if (configFile.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(configFile.getPath()));
                String line;
                String output = "";
                int count = -1;
                while ((line = reader.readLine()) != null) {
                    output += line;
                }
                if (output.equals("0")) {
                    MainWindow.isChanged = true;
                }
                reader.close();
            } catch (IOException e) {
                throw new CakeException("Config failed");
            }
        }
    }

    /**
     * Method that creates data to be written into savefile.txt.
     */
    private void initialiseUser(File file, String filename) throws IOException {
        LOGGER.entering(getClass().getName(), "initialiseUser");
        boolean isCleanSlate = true;
        if (!file.getParentFile().getParentFile().exists()) {
            file.getParentFile().getParentFile().mkdir();
            LOGGER.info("Creating grandpa file[p]");
        }
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdir();
            LOGGER.info("Creating papa file[p]");
        }
        if (!file.exists()) {
            file.createNewFile();
            LOGGER.info("Creating file[p]");
        } else {
            isCleanSlate = false;
            LOGGER.info(filepath + " is found![p]");
        }

        if (!isResetFresh && isCleanSlate && filename.equals("data")) {
            LOGGER.info("Testing Profile initiated");
            username = "BakaTester";
            PrintWriter out = new PrintWriter(filepath);
            out.println(username);

            //for stupid fking testers
            for (int i = 0; i < 3; ++i) {
                out.println("3");
            }
            out.println("0");
            for (int i = 0; i < 9; ++i) {
                out.println("1");
            }
            for (int i = 0; i < 3; ++i) {
                out.println("0");
            }
            out.close();
        } else if (isCleanSlate) {
            LOGGER.info("Resetted Profile initiated");
            username = "NEW_USER_!@#";
            PrintWriter out = new PrintWriter(filepath);
            out.println(username);
            for (int i = 0; i < totalNumOfMainTopics * (levelsOfDifficulty + 1); ++i) {
                out.println("0");
            }
            out.close();
        }
        LOGGER.exiting(getClass().getName(), "initialiseUser");
    }

    private void writeProgress() throws CakeException {
        try {
            PrintWriter out = new PrintWriter(filepath);
            out.println(username);
            for (int i : overalltopicsDone) {
                out.println("" + i);
            }
            for (int i: individualTopicsDone) {
                out.println("" + i);
            }
            out.close();
        } catch (FileNotFoundException e) {
            LOGGER.severe("Cannot write[p]");
            throw new CakeException("Cannot initialise file");
        }
    }
}
