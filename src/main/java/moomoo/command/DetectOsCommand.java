package moomoo.command;

public class DetectOsCommand {
    public static String osName;

    /**
     * Get operating system name the user is running on.
     */
    public DetectOsCommand() {
        this.osName = System.getProperty("os.name");
    }
}
