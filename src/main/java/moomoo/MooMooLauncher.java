package moomoo;

import moomoo.task.MooMooException;

import java.io.IOException;

/**
 * Launches the application.
 */
public class MooMooLauncher {

    /**
     * Runs the program in CLI mode.
     * @param args If appropriate argument is given, GUI will be launched.
     */
    public static void main(String[] args) throws MooMooException {
        String operatingSystem = System.getProperty("os.name").toLowerCase();
        if (operatingSystem.contains("win")) {
            try {
                ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c",
                        "chcp", "65001", ">", "nul", "2>&1").inheritIO();
                pb.start();
            } catch (IOException e) {
                throw new MooMooException("An error has occurred. Some output may be corrupted");
            }
        }
        MooMoo.main(args);
    }
}


