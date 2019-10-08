package seedu.duke.email;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import seedu.duke.Duke;
import seedu.duke.email.entity.Email;
import seedu.duke.email.entity.EmailList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Locale;

public class EmailParser {
    protected static DateTimeFormatter format = DateTimeFormatter
            .ofPattern("uuuu-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
            .withResolverStyle(ResolverStyle.STRICT);
    //plain format is for filename without special characters
    protected static DateTimeFormatter formatPlain = DateTimeFormatter
            .ofPattern("uuuu-MM-dd-HHmmss", Locale.ENGLISH)
            .withResolverStyle(ResolverStyle.STRICT);

    /**
     * Parses the response of email fetching from Outlook server.
     *
     * @param response response from Outlook server
     * @return a list of emails containing all the parsed email from the response
     * @throws EmailParsingException the exception of the failure of the response parsing
     */
    public static EmailList parseFetchResponse(String response) throws EmailParsingException {
        Duke.getUI().showDebug(response);
        EmailList emailList = new EmailList();
        try {
            JSONObject responseJson = new JSONObject(response);
            JSONArray emailJsonArray = responseJson.getJSONArray("value");
            for (int i = 0; i < emailJsonArray.length(); i++) {
                JSONObject emailJson = emailJsonArray.getJSONObject(i);
                String subject = emailJson.getString("subject");
                Sender from = new Sender(emailJson.getJSONObject("from"));
                LocalDateTime dateTime = parseEmailDateTime(emailJson.getString("receivedDateTime"));
                String body = emailJson.getString("body");
                emailList.add(new Email(subject, from, dateTime, body));
            }
        } catch (JSONException e) {
            throw new EmailParsingException("Email fetch response failed to parse");
        }

        return emailList;
    }

    /**
     * Parses the email date time string to a LocalDateTime.
     *
     * @param dateTimeString string of the date time
     * @return LocalDateTime object to be stored
     */
    public static LocalDateTime parseEmailDateTime(String dateTimeString) {
        return LocalDateTime.parse(dateTimeString, format);
    }

    /**
     * Formats the email date time to a string.
     *
     * @param dateTime LocalDateTime object stored in the Email object
     * @return String of the formatted date time.
     */
    public static String formatEmailDateTime(LocalDateTime dateTime) {
        return dateTime.format(format);
    }

    /**
     * Formats the email date time to a plain string without any special character for filename.
     *
     * @param dateTime LocalDateTiem object stored in the Email object
     * @return String of plain formatted date time.
     */
    public static String formatEmailDateTimePlain(LocalDateTime dateTime) {
        return dateTime.format(formatPlain);
    }

    /**
     * Class of the email sender containing the name and address.
     */
    public static class Sender {
        private String name;
        private String address;

        /**
         * Constructor of the sender class with the json object containing the information.
         *
         * @param senderInfo json object containing the information
         * @throws JSONException exception of failure of parsing
         */
        public Sender(JSONObject senderInfo) throws JSONException {
            this.name = senderInfo.getJSONObject("emailAddress").getString("name");
            this.address = senderInfo.getJSONObject("emailAddress").getString("address");
        }

        public String toString() {
            return name + " => " + address;
        }
    }

    /**
     * Exception dedicated for failed email parsing.
     */
    public static class EmailParsingException extends Exception {
        private String msg;

        public EmailParsingException(String msg) {
            this.msg = msg;
        }

        @Override
        public String toString() {
            return this.msg;
        }
    }
}
