package seedu.duke.email;

import org.json.JSONException;
import org.json.JSONObject;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class Email {
    protected String filepath;
    protected String subject;
    protected EmailParser.Sender from;
    protected LocalDateTime receivedDateTime;
    protected String body;
    protected Boolean hasHtml;
    protected String tag;

    public Email(String subject) {
        this.subject = subject;
        this.filepath = getEmailFilePath();
    }

    public Email(String subject, EmailParser.Sender from, LocalDateTime receivedDateTime, String body) {
        this.subject = subject;
        this.from = from;
        this.receivedDateTime = receivedDateTime;
        this.body = body;
    }

    /**
     * Get title of this email.
     *
     * @return title of this email.
     */
    public String getSubject() {
        return this.subject;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * Get the pathname for this email.
     *
     * @return pathname for this email.
     */
    public String getEmailFilePath() {
        return getFolderDir() + File.separator + this.subject;
    }

    /**
     * Show this email on browser.
     * To be replaced by JavaFx code for UI display.
     *
     * @throws IOException if fails to load the filepath or open the browser.
     */
    public void showEmail() throws IOException {
        File emailFile = new File(this.filepath);
        Desktop.getDesktop().browse(emailFile.toURI());
    }

    /**
     * Get the pathname of the data/emails folder.
     *
     * @return the pathname of the data/emails folder.
     */
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

    /**
     * Outputs a string with all the information of this email to be stored in a file for future usage.
     *
     * @return a string with all the information of this email.
     */
    public String toFileString() {
        String filestring = this.subject + " | ";  // to add on info such as tags.
        return filestring;
    }

    private String getDateTimeString() {
        return EmailParser.formatEmailDateTime(receivedDateTime);
    }

    public String toCliString() {
        String output = this.subject + "\n\t" + "From: " + this.from.toString() + "\n\t" +
                "ReceivedDateTime: " + getDateTimeString() + "\n\t" + "Body: " + body.substring(0, 30)
                + "...\n";
        return output;
    }

    public String getBody() {
        return body;
    }
}
