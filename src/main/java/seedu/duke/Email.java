package seedu.duke;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Email {
    protected String filepath;
    protected String title;
    protected String sender;
    protected static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HHmm");

    public Email(String title) {
        this.title = title;
        this.filepath = getEmailFilePath();
    }

    public String getTitle() {
        return this.title;
    }

    public String getEmailFilePath () {
        return getFolderDir() + File.separator + this.title;
    }

    // To be replaced by JavaFx code for UI display.
    public void showEmail() throws IOException {
        File emailFile = new File(this.filepath);
        Desktop.getDesktop().browse(emailFile.toURI());
    }

    private static String getFolderDir() {
        String dir;
        String workingDir = System.getProperty("user.dir");
        if (workingDir.endsWith(File.separator + "text-ui-test")) {
            dir = ".." + File.separator + "data" + File.separator + "emails";
        } else if (workingDir.endsWith(File.separator + "main")) {
            dir = "data" + File.separator + "emails";
        } else {
            dir = "emails";
        }
        return dir;
    }

}
