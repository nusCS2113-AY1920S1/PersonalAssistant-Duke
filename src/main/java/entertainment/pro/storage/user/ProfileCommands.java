package entertainment.pro.storage.user;

import entertainment.pro.commons.exceptions.InvalidFormatCommandException;
import entertainment.pro.model.UserProfile;
import entertainment.pro.storage.utils.EditProfileJson;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.TreeMap;


/**
 * Class contains all methods that deal with Profile object.
 */
public class ProfileCommands {
    //    private File genreList;
    private UserProfile userProfile;
    private EditProfileJson editProfileJson;
    private static String GET_NEW_GENRE_PREF = "-g";
    private static String GET_NEW_GENRE_RESTRICT = "-r";
    private static String GET_NEW_SORT = "-s";
    private static String GET_NEW_ADULT_RATING = "-a";

    /**
     * Constructor for ProfileCommands.
     */
    public ProfileCommands(UserProfile userProfile) throws IOException {
        //genreList = new File("../../../../EPdata/genreIDlist.txt");
        this.userProfile = userProfile;
        this.editProfileJson = new EditProfileJson();
    }

    /**
     * change name in profile.
     */
    public void setName(String name) throws IOException {
        userProfile.setUserName(name);
        editProfileJson.updateProfile(userProfile);
    }

    /**
     * change age in profile.
     */
    public void setAge(String age) throws IOException {
        userProfile.setUserAge(Integer.parseInt(age));
        editProfileJson.updateProfile(userProfile);
    }

    /**
     * to add preferences to userprofile.
     */
    public void addPreference(TreeMap<String, ArrayList<String>> flagMap, String getInput) throws IOException, InvalidFormatCommandException {
        ArrayList<Integer> genrePreferences = new ArrayList<>(50);
        ArrayList<Integer> genreRestrict = new ArrayList<>(50);
        int sortOption = 0;
        if (getInput.equals(GET_NEW_SORT) || getInput.equals(GET_NEW_ADULT_RATING)) {
            if (flagMap.size() != 1) {
                throw new InvalidFormatCommandException();
            }
        }
        for (String log : flagMap.get(getInput)) {
            if (getInput.equals(GET_NEW_GENRE_PREF)) {
                System.out.println("ok so far0...");
                genrePreferences.add(findGenreID(log));
            }
            if (getInput.equals(GET_NEW_GENRE_RESTRICT)) {
                System.out.println("ok so far1...");
                genreRestrict.add(findGenreID(log));
            }
            if (getInput.equals(GET_NEW_ADULT_RATING)) {
                if (log.equals("true")) {
                    userProfile.setAdult(true);
                } else if (log.equals("false")){
                    userProfile.setAdult(false);
                } else {
                    throw new InvalidFormatCommandException();
                }
                System.out.println("ok so far2...");
            }
            if (getInput.equals(GET_NEW_SORT)) {
                System.out.println("ok so far..." + log);
                try {
                    sortOption = Integer.parseInt(log);
                    if (sortOption <= 0 || sortOption > 3) {
                        throw new InvalidFormatCommandException();
                    }
                } catch (NumberFormatException e) {
                    throw new InvalidFormatCommandException();
                }
                System.out.println("ok so far..." + sortOption);
                getSortFromUserInput(sortOption);
                System.out.println("ok so far3...");
            }
        }
        userProfile.addGenreIdPreference(genrePreferences);
        System.out.println("ok so far4...");
        userProfile.addGenreIdRestriction(genreRestrict);
        editProfileJson.updateProfile(userProfile);
        System.out.println("ok so far5...");
    }


    /**
     * to remove preferences from userprofile.
     */
    public void removePreference(TreeMap<String, ArrayList<String>> flagMap, String getInput) throws IOException, InvalidFormatCommandException {
        ArrayList<Integer> removeGenrePreferences = new ArrayList<>(50);
        ArrayList<Integer> removeGenreRestrict = new ArrayList<>(50);
        for (String log : flagMap.get(getInput)) {
            if (getInput.equals(GET_NEW_GENRE_PREF)) {
                removeGenrePreferences.add(findGenreID(log));
            }
            if (getInput.equals(GET_NEW_GENRE_RESTRICT)) {
                removeGenreRestrict.add(findGenreID(log));
            }
        }
        if (getInput.equals(GET_NEW_ADULT_RATING)) {
            ArrayList<String> getFlag = flagMap.get(GET_NEW_ADULT_RATING);
            if (getFlag.isEmpty()) {
                if (userProfile.isAdult()) {
                    userProfile.setAdult(false);
                    System.out.println("ok so far...0");
                } else {
                    userProfile.setAdult(true);
                    System.out.println("ok so far...1");
                }
            } else {
                throw new InvalidFormatCommandException();
            }
        }
        if (getInput.equals(GET_NEW_SORT)) {
            ArrayList<String> getFlag = flagMap.get(GET_NEW_SORT);
            if (getFlag.isEmpty()) {
                clearSortPreference();
                System.out.println("ok so far...3");
            } else {
                throw new InvalidFormatCommandException();
            }
        }

        userProfile.removeGenreIdPreference(removeGenrePreferences);
        userProfile.removeGenreIdRestriction(removeGenreRestrict);
        editProfileJson.updateProfile(userProfile);
        System.out.println("ok so far5...");

    }

    /**
     * to clear userprofile preferences.
     */
    public void clearPreference(TreeMap<String, ArrayList<String>> flagMap, String getInput) throws IOException, InvalidFormatCommandException {

        ArrayList<String>getFlag = flagMap.get(getInput);
        if (getFlag.isEmpty()) {

            if (getInput.equals(GET_NEW_GENRE_PREF)) {
                clearGenrePreference();
            }
            if (getInput.equals(GET_NEW_GENRE_RESTRICT)) {
                clearGenreRestrict();
            }
            if (getInput.equals(GET_NEW_ADULT_RATING)) {
                clearAdultPreference();
            }
            if (getInput.equals(GET_NEW_SORT)) {
                clearSortPreference();
            }
            editProfileJson.updateProfile(userProfile);
        } else {
            throw new InvalidFormatCommandException();
        }
    }

    private void clearSortPreference() throws IOException {
        setSort(false, false, false);
    }

    private void clearGenreRestrict() {
        userProfile.removeGenreIdRestriction(userProfile.getGenreIdRestriction());
    }


    private void clearGenrePreference() throws IOException {
        userProfile.removeGenreIdPreference(userProfile.getGenreIdPreference());
    }


    public void clearAdultPreference() throws IOException {
        userProfile.setAdult(false);
    }


    private void getSortFromUserInput(int sortOption) throws IOException {
        if (sortOption == 1) {
            setSort(true, false, false);
        } else if (sortOption == 2) {
            setSort(false, true, false);
        } else if (sortOption == 3) {
            setSort(false, false, true);
        } else {
            // throw exception
            //throw new exceptions
        }
    }

    /**
     * Updates the components in the SortProfile accordingly.
     *
     * @param isAlphaOrder    true when user have entered command to
     *                        sort results in alphabetical order and otherwise false.
     * @param isLatDatesOrder true when user have entered command to
     *                        sort results based on release dates and otherwise false.
     * @param isRatingsOrder  true when user have entered command to
     *                        sort results based on ratings and otherwise false.
     */
    public void setSort(boolean isAlphaOrder, boolean isLatDatesOrder, boolean isRatingsOrder) throws IOException {
        userProfile.setSortByAlphabetical(isAlphaOrder);
        userProfile.setSortByHighestRating(isRatingsOrder);
        userProfile.setSortByLatestRelease(isLatDatesOrder);
        editProfileJson.updateProfile(userProfile);
    }


    /**
     * set user preferences.
     */
    public void setPreference(TreeMap<String, ArrayList<String>> flagMap) throws IOException {
        if (flagMap.containsKey("-g")) {
            setGenrePreference(flagMap);
        }
        if (flagMap.containsKey("-a")) {
            setAdultPreference(flagMap.get("-a").get(0));
        }
        if (flagMap.containsKey("-g") && flagMap.containsKey("-a")) {
            setAll(flagMap, flagMap.get("-a").get(0));
        }
    }

    /**
     * set user preferences -- for genre.
     */
    private void setGenrePreference(TreeMap<String, ArrayList<String>> flagMap) throws IOException {
        ArrayList<Integer> genrePreferences = new ArrayList<>(10);
        for (String log : flagMap.get("-g")) {
            int id = findGenreID(log);
            genrePreferences.add(id);
        }
        userProfile.setGenreIdPreference(genrePreferences);
        editProfileJson.updateProfile(userProfile);
    }

    /**
     * set user preferences -- for adult content restriction.
     * yes = allow adult content
     * no = restrict adult content
     */
    private void setAdultPreference(String value) throws IOException {
        if (value.equals("yes")) {
            userProfile.setAdult(true);
        } else if (value.equals("no")) {
            userProfile.setAdult(false);
        }
        editProfileJson.updateProfile(userProfile);
    }

    /**
     * to allow setting of both genre and adult content restriction preferences at the same time.
     */
    private void setAll(TreeMap<String, ArrayList<String>> flagMap, String value) throws IOException {
        ArrayList<Integer> genrePreferences = new ArrayList<>(10);
        for (String log : flagMap.get("-g")) {
            int id = findGenreID(log);
            genrePreferences.add(id);
        }
        userProfile.setGenreIdPreference(genrePreferences);
        if (value.equals("yes")) {
            userProfile.setAdult(true);
        } else if (value.equals("no")) {
            userProfile.setAdult(false);
        }
        editProfileJson.updateProfile(userProfile);
    }

    /**
     * set user restrictions.
     */
    public void setRestriction(TreeMap<String, ArrayList<String>> flagMap) throws IOException {
        ArrayList<Integer> genreRestrictions = new ArrayList<>(10);
        for (String log : flagMap.get("-g")) {
            int id = findGenreID(log);
            genreRestrictions.add(id);
        }
        userProfile.setGenreIdRestriction(genreRestrictions);
        editProfileJson.updateProfile(userProfile);
    }

    /**
     * add restriction to userprofile.
     */
    public void addRestriction(TreeMap<String, ArrayList<String>> flagMap) throws IOException {
        ArrayList<Integer> genreRestrictions = new ArrayList<>(50);
        for (String log : flagMap.get("-g")) {
            genreRestrictions.add(findGenreID(log));
        }
        userProfile.addGenreIdRestriction(genreRestrictions);
        editProfileJson.updateProfile(userProfile);
    }

    /**
     * remove restriction from userprofile.
     */
    public void removeRestriction(TreeMap<String, ArrayList<String>> flagMap) throws IOException {
        ArrayList<Integer> genreRestrictions = new ArrayList<>(50);
        for (String log : flagMap.get("-g")) {
            genreRestrictions.add(findGenreID(log));
        }
        userProfile.removeGenreIdRestriction(genreRestrictions);
        editProfileJson.updateProfile(userProfile);
    }

    public void clearRestriction(TreeMap<String, ArrayList<String>> flagMap) throws IOException {
        userProfile.removeGenreIdRestriction(userProfile.getGenreIdRestriction());
        editProfileJson.updateProfile(userProfile);
    }

//    public void addPreference(TreeMap<String, ArrayList<String>> flagMap) throws IOException {
//        ArrayList<Integer> genrePreferences = new ArrayList<>(50);
//        for (String log : flagMap.get("-g")) {
//            genrePreferences.add(findGenreID(log));
//        }
//        userProfile.addGenreIdPreference(genrePreferences);
//        editProfileJson.updateProfile(userProfile);
//    }
//
//    public void removePreference(TreeMap<String, ArrayList<String>> flagMap) throws IOException {
//        ArrayList<Integer> genrePreferences = new ArrayList<>(50);
//        for (String log : flagMap.get("-g")) {
//            genrePreferences.add(findGenreID(log));
//        }
//        userProfile.removeGenreIdPreference(genrePreferences);
//        editProfileJson.updateProfile(userProfile);
//    }
//
//    public void clearPreference(TreeMap<String, ArrayList<String>> flagMap) throws IOException {
//        if (flagMap.containsKey("-g")) {
//            clearGenrePreference();
//        }
//        if (flagMap.containsKey("-a")) {
//            clearAdultPreference();
//        }
//        if (flagMap.containsKey("-g") && flagMap.containsKey("-a")) {
//            clearAll();
//        }
//    }
//
//
//    private void clearAll() throws IOException {
//        userProfile.removeGenreIdPreference(userProfile.getGenreIdPreference());
//        userProfile.setAdult(true);
//        editProfileJson.updateProfile(userProfile);
//    }

    /**
     * to find genreId for corresponding genre name.
     */
    public static Integer findGenreID(String genreName) throws IOException {
        InputStream inputStream = ProfileCommands.class.getResourceAsStream("/data/GenreId.json");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String genreListString = "";
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            genreListString += line;
        }
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
        return Integer.parseInt(parseToId(genreListString, genreName.trim()));
    }

    public static String findGenreName(int id) throws IOException {
        InputStream inputStream = ProfileCommands.class.getResourceAsStream("/data/GenreId.json");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String genreListString = "";
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            genreListString += line;
        }
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
        return parseToName(genreListString, Integer.toString(id));
    }

    private static String parseToName(String genreListString, String id) {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = (JSONArray) parser.parse(genreListString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            String genreId = (String) jsonObject.get("id");
            if (genreId.equals(id)) {
                return (String) jsonObject.get("genre");
            }
        }
        return "0";
    }

    private static String parseToId(String genreListString, String name) {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = (JSONArray) parser.parse(genreListString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            String genreName = (String) jsonObject.get("genre");
            if (genreName.equalsIgnoreCase(name)) {
                return (String) jsonObject.get("id");
            }
        }
        return "0";
    }



    /**
     * to get Label text for genres.
     */
    public String convertToLabel(ArrayList<Integer> userList) throws IOException {
        String labelText = "";
        for (Integer log : userList) {
            labelText += findGenreName(log);
            labelText += "\n";
        }
        return labelText;
    }

    /**
     * to get Label text for adult preferences.
     */
    public String getAdultLabel() {
        if (userProfile.isAdult()) {
            return "allow";
        } else {
            return "restrict";
        }
    }

    public void addPlaylist(String listName) throws IOException {
        userProfile.addPlaylist(listName);
        editProfileJson.updateProfile(userProfile);
    }

    public void deletePlaylist(String listName) throws IOException {
        userProfile.deletePlaylist(listName);
        editProfileJson.updateProfile(userProfile);
    }
}
