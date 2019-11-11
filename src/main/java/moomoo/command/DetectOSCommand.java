package moomoo.command;

public class DetectOSCommand {
    public static String osName;

    /**
     * Get operating system name the user is running on.
     */
    public DetectOSCommand() {
        this.osName = System.getProperty("os.name");
    }
}
