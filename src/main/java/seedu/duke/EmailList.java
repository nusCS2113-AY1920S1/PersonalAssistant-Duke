package seedu.duke;

import java.io.File;
import java.util.ArrayList;

public class EmailList extends ArrayList<Email> {

    @Override
    public String toString() {
        File emailFolder = null;
        try {
            emailFolder = new File(getFolderDir());
        } catch (Exception e) {
            Duke.getUI().showError(e.toString());
        }
        return listFilesInFolder(emailFolder);
    }

    public String listFilesInFolder(final File folder) {
        String listOfEmails = "This is your list of emails:";
        int i = 0;
        for (File fileEntry : folder.listFiles()) {
            if(fileEntry.isFile()) {
                listOfEmails += "\n" + ++i + ". " + fileEntry.getName();
            }
        }
        return listOfEmails;
    }

    private static String getFolderDir() {
        String dir = "";
        String workingDir = System.getProperty("user.dir");
        if (workingDir.endsWith(File.separator + "text-ui-test")) {
            dir = ".." + File.separator + "data" + File.separator + "emails";
        } else if (workingDir.endsWith(File.separator + "main")) {
            dir = "." + File.separator + "data" + File.separator + "emails";
        } else {
            dir = "." + File.separator + "emails";
        }
        return dir;
    }

}
