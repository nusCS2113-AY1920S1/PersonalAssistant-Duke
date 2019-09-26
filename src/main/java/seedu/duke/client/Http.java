package seedu.duke.client;

import org.json.JSONException;
import org.json.JSONObject;
import seedu.duke.Duke;

import java.awt.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Http {
    private static String authCode = null;
    private static String accessToken = null;
    private static String clientId = "feacc09e-5364-4386-92e5-78ee25d2188d";
    private static String clientSecret = "8dhu0-v80Ic-ZrQpACgWLEPg:??1MGkc";
    private static String redirect = "http://localhost:3000";

    //This function is adapted from code on https://www.baeldung.com/java-http-request
    public static String sendGetRequest(String link) {
        URL url = null;
        try {
            url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(5000);
            conn.setInstanceFollowRedirects(false);

            int status = conn.getResponseCode();
            Reader streamReader = null;
            if (status > 299) {
                streamReader = new InputStreamReader(conn.getErrorStream());
            } else {
                streamReader = new InputStreamReader(conn.getInputStream());
            }

            BufferedReader in = new BufferedReader(streamReader);
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            conn.disconnect();
            //Duke.getUI().showResponse(content.toString());
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

    public static void getAuth() {
        openBrowser("https://login.microsoftonline.com/common/oauth2/v2.0/authorize?client_id="
                + clientId + "&response_type=code"
                + "&redirect_uri=" + redirect + "&scope=openid+Mail.Read");
    }

    public static void setAuthCode(String code) {
        Duke.getUI().showDebug("Auth Code Set: " + code);
        authCode = code;
        getAccess();
    }

    //function adapted from https://stackoverflow
    // .com/questions/40574892/how-to-send-post-request-with-x-www-form-urlencoded-body
    public static void getAccess() {
        String params  = "client_id=" + clientId + "&client_secret=" + clientSecret + "&code=" + authCode
                + "&redirect_uri=" + redirect + "&grant_type=authorization_code";
        try{
            byte[] postData = params.getBytes( StandardCharsets.UTF_8 );
            int postDataLength = postData.length;
            String request = "https://login.microsoftonline.com/common/oauth2/v2.0/token";
            URL url = new URL( request );
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength ));
            conn.setUseCaches(false);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.write( postData );

            int status = conn.getResponseCode();
            Reader streamReader = null;
            if (status > 299) {
                streamReader = new InputStreamReader(conn.getErrorStream());
            } else {
                streamReader = new InputStreamReader(conn.getInputStream());
            }

            BufferedReader in = new BufferedReader(streamReader);
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            conn.disconnect();
            Duke.getUI().showDebug(content.toString());
            JSONObject json = new JSONObject(content.toString());
            setAccessToken(json.getString("access_token"));
        } catch (MalformedURLException e) {
            Duke.getUI().showError("Access Code url in wrong format...");
        } catch (IOException e) {
            Duke.getUI().showError("Access Code url failed to open...");
        } catch (JSONException e) {
            Duke.getUI().showError("Access code response in wrong format...");
        }
    }

    public static void setAccessToken(String token)
    {
        Duke.getUI().showDebug("Access Token Set: " + token);
        accessToken = token;
        fetchEmailList();
    }

    public static void fetchEmailList() {
        String url = "https://graph.microsoft.com/v1.0/me/mailfolders/inbox/messages?"
                + "$select=subject,from,receivedDateTime"
                + "&$top=25"
                + "&$orderby=receivedDateTime%20DESC";
        String response = sendGetRequest(url);
        Duke.getUI().showDebug(response);
    }
}
