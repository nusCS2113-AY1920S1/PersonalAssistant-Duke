package command;
import Dictionary.Word;
import command.*;
import exception.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Sets up API call and options to query a word from Oxford Dictionary API
 * Only works if internet connection is present.
 * @author Ng Jian Wei
 */
public class OxfordCall{

    public static String onlineSearch(String word) throws NoWordFoundException{
        String queryWord = word;
        String alpha = doInBackground(queryWord);
        String result = extractFirstDef(alpha);
        return result;
    }

    public static String dictionaryEntries(String lookUpWord) {
        final String language = "en-gb";
        final String word = lookUpWord; //query this word to oxford API
        final String fields = "definitions";
        final String strictMatch = "false";
        final String word_id = word.toLowerCase();
        return "https://od-api.oxforddictionaries.com/api/v2/entries/" + language + "/" + word_id + "?" + "fields=" + fields + "&strictMatch=" + strictMatch;
    }

    /**
     * Request for metaData
     * @author Ng Jian Wei
     */
    public static String doInBackground(String word) throws NoWordFoundException {
        final String app_id = "11f848bf"; //obtained from Oxford account
        final String app_key = "5be9615c9940859a6ce549f449cc670d"; //obtained from Oxford account
        try {
            URL url = new URL(dictionaryEntries(word));
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("app_id", app_id);
            urlConnection.setRequestProperty("app_key", app_key);

            // read the output from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            throw new NoWordFoundException(word);
        }
    }
    /**
     * Extract the first definition inside the metadata
     * @author Ng Jian Wei
     */
    public static String extractFirstDef(String metaData){
        String result ="";
        String[] temp = metaData.split("definitions", 2);
        String[] temp2 = temp[1].split("]", 2);
        temp2[0] = temp2[0].replaceAll(":","");
        temp2[0] = temp2[0].replaceAll("\\[","");
        temp2[0] = temp2[0].replaceAll("\"","");
        return temp2[0].trim();
    }
}
