package seedu.duke.client;

import seedu.duke.Duke;

import java.awt.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class Http {

    //This function is adapted from code on https://www.baeldung.com/java-http-request
    public static String getRequest(String link, Map<String, String> params) {
        URL url = null;
        try {
            url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setConnectTimeout(5000);
            con.setInstanceFollowRedirects(false);

            int status = con.getResponseCode();
            Reader streamReader = null;
            if (status > 299) {
                streamReader = new InputStreamReader(con.getErrorStream());
            } else {
                streamReader = new InputStreamReader(con.getInputStream());
            }

            BufferedReader in = new BufferedReader(streamReader);
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            con.disconnect();
            Duke.getUI().showResponse(content.toString());
            return content.toString();
        } catch (MalformedURLException e) {
            Duke.getUI().showError("Wrong URL format...");
        } catch (ProtocolException e) {
            Duke.getUI().showError("Protocol exception encountered...");
        } catch (IOException e) {
            Duke.getUI().showError("HTTP connection failed...");
        }
        return "";
    }

    //This function is adapted from https://stackoverflow
    // .com/questions/10967451/open-a-link-in-browser-with-java-button
    public static boolean openBrowser(String link) {
        try {
            URI url = new URI(link);
            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(url);
                    return true;
                } catch (Exception e) {
                    Duke.getUI().showError(e.toString());
                }
            }
        } catch (URISyntaxException e) {
            Duke.getUI().showError("Wrong URI format...");
        }
        return false;
    }
}
