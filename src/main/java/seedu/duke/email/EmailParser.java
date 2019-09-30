package seedu.duke.email;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Locale;

public class EmailParser {
    protected static DateTimeFormatter foramt = DateTimeFormatter
                    .ofPattern("uuuu-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
                    .withResolverStyle(ResolverStyle.STRICT);;

    public static EmailList parseFetchResponse(String response) throws EmailParsingException {
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

    public static LocalDateTime parseEmailDateTime(String dateTimeString) {
        return LocalDateTime.parse(dateTimeString, foramt);
    }

    public static String formatEmailDateTime(LocalDateTime dateTime) {
        return dateTime.format(foramt);
    }

    public static class Sender {
        private String name;
        private String address;

        public Sender(JSONObject senderInfo) throws JSONException {
            this.name = senderInfo.getJSONObject("emailAddress").getString("name");
            this.address = senderInfo.getJSONObject("emailAddress").getString("address");
        }

        public String toString() {
            return name + " => " + address;
        }
    }

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
