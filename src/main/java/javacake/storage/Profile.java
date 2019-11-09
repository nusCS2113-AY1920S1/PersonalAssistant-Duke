package javacake.storage;

import javacake.JavaCake;
import javacake.exceptions.CakeException;
import javacake.ui.MainWindow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

public class Profile {
    private static String filepath = "data";
    private String username;
    private ArrayList<Integer> overalltopicsDone = new ArrayList<>();
    private ArrayList<Integer> individualTopicsDone = new ArrayList<>();
    int totalNumOfMainTopics = 4;
    int levelsOfDifficulty = 3;
    private static boolean isResetFresh = false;


    public Profile() throws CakeException {
        this("data");
    }

    /**
     * Constructor for profile.
     * @param filename String of filepath
     * @throws CakeException when unable to create profile
     */
    public Profile(String filename) throws CakeException {
        filepath = filename;
        filepath += "/save/savefile.txt";
        File file = new File(filepath);
        JavaCake.logger.log(Level.INFO,"Filepath: " + filepath);
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
                    overalltopicsDone.add(Integer.parseInt(line));
                } else {
                    individualTopicsDone.add(Integer.parseInt(line));
                }
                ++count;
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("after reader");
            throw new CakeException("Failed to close reader");
        }
    }

    /**
     * Method to hard reset profile.
     */
    public static void resetProfile() {
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
    public static void writeColorConfig(boolean isLight) throws CakeException {
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
        boolean isCleanSlate = true;
        if (!file.getParentFile().getParentFile().exists()) {
            file.getParentFile().getParentFile().mkdir();
            JavaCake.logger.log(Level.INFO, "ProfileGrandpa");
        }
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdir();
            JavaCake.logger.log(Level.INFO, "ProfilePapa");
        }
        if (!file.exists()) {
            file.createNewFile();
            JavaCake.logger.log(Level.INFO, "ProfileP");
        } else {
            isCleanSlate = false;
            JavaCake.logger.log(Level.INFO, filepath + " is found!");
        }

        if (!isResetFresh && isCleanSlate && filename.equals("data")) {
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
            username = "NEW_USER_!@#";
            PrintWriter out = new PrintWriter(filepath);
            out.println(username);
            for (int i = 0; i < totalNumOfMainTopics * (levelsOfDifficulty + 1); ++i) {
                out.println("0");
            }
            out.close();
        }

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
            throw new CakeException("Cannot initialise file");
        }
    }
}
