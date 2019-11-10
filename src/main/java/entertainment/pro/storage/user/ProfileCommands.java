package entertainment.pro.storage.user;

import entertainment.pro.commons.exceptions.DuplicateGenreException;
import entertainment.pro.commons.exceptions.GenreDoesNotExistException;
import entertainment.pro.commons.exceptions.InvalidFormatCommandException;
import entertainment.pro.commons.exceptions.InvalidGenreNameEnteredException;
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
    UserProfile userProfile;
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
     * Responsible for setting the name in the userProfile.
     * @param name The name to be set in the UserProfile.
     * @throws IOException
     */
    public void setName(String name) throws IOException {
        userProfile.setUserName(name);
        editProfileJson.updateProfile(userProfile);
    }

    /**
     * Responsible for setting the age in the userProfile.
     * @param age The age to be set in the userProfile.
     * @throws IOException
     */
    public void setAge(String age) throws IOException {
        userProfile.setUserAge(Integer.parseInt(age));
        editProfileJson.updateProfile(userProfile);
    }

    /**
     * Responsible for adding new preference and sort options in the userProfile.
     * @param flagMap The elements to be added under a particular category in the userProfile.
     * @param getInput String that signifies a particular category in the userProfile.
     * @throws IOException
     * @throws InvalidFormatCommandException
     * @throws InvalidGenreNameEnteredException
     * @throws DuplicateGenreException
     */
    public void addPreference(TreeMap<String, ArrayList<String>> flagMap, String getInput) throws IOException, InvalidFormatCommandException, InvalidGenreNameEnteredException, DuplicateGenreException {
        ArrayList<Integer> genrePreferences = new ArrayList<>(50);
        ArrayList<Integer> genreRestrict = new ArrayList<>(50);
        int sortOption = 0;
        ArrayList<String> listForFlag = flagMap.get(getInput);
            if (listForFlag.size() == 0) {
                throw new InvalidFormatCommandException();
            }

        for (String log : flagMap.get(getInput)) {
            if (log.isEmpty() || log.isBlank()) {
                throw new InvalidFormatCommandException();
            }
            if (getInput.equals(GET_NEW_GENRE_PREF)) {
                System.out.println("ok so far0...");
                int genre = findGenreID(log);
                if (genre == 0) {
                    throw new InvalidGenreNameEnteredException();
                }
                if (isRepetitiveGenrePreference(genre) || isRepetitiveGenreRestrict(genre)) {
                    throw new DuplicateGenreException();
                }
                genrePreferences.add(genre);
            }
            if (getInput.equals(GET_NEW_GENRE_RESTRICT)) {
                System.out.println("ok so far1...");
                int genre = findGenreID(log);
                if (genre == 0) {
                    throw new InvalidGenreNameEnteredException();
                }
                if (isRepetitiveGenrePreference(genre) || isRepetitiveGenreRestrict(genre)) {
                    throw new DuplicateGenreException();
                }
                genreRestrict.add(genre);
            }
            if (getInput.equals(GET_NEW_ADULT_RATING)) {
                if (listForFlag.size() != 1) {
                    throw new InvalidFormatCommandException();
                }
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
                if (listForFlag.size() != 1) {
                    throw new InvalidFormatCommandException();
                }
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
     * Responsible for checking whether a genre exist under genre preference in the database.
     * @param genreNumber The ID of the genre that needs to be checked.
     * @return true if genre exist in the database and false otherwise.
     */
    private boolean isRepetitiveGenrePreference(int genreNumber) {
        ArrayList<Integer>genrePref = userProfile.getGenreIdPreference();
        for (int i = 0; i < genrePref.size(); i += 1) {
            if (genreNumber == genrePref.get(i)){
                return true;
            }
        }
        return false;
    }

    /**
     * Responsible for checking whether a genre exist under genre restrictions in the database.
     * @param genreNumber The ID of the genre that needs to be checked.
     * @return true if genre exist in the database and false otherwise.
     */
    private boolean isRepetitiveGenreRestrict(int genreNumber) {
        ArrayList<Integer>genreRestrict = userProfile.getGenreIdRestriction();
        for (int i = 0; i < genreRestrict.size(); i += 1) {
            if (genreNumber == genreRestrict.get(i)){
                return true;
            }
        }
        return false;
    }


    /**
     * Responsible for removing preference and sort options in the userProfile.
     * @param flagMap The elements to be added under a particular category in the userProfile.
     * @param getInput String that signifies a particular category in the userProfile
     * @throws IOException
     * @throws InvalidFormatCommandException
     */
    public void removePreference(TreeMap<String, ArrayList<String>> flagMap, String getInput) throws IOException,
            InvalidFormatCommandException, InvalidGenreNameEnteredException, GenreDoesNotExistException {
        ArrayList<Integer> removeGenrePreferences = new ArrayList<>(50);
        ArrayList<Integer> removeGenreRestrict = new ArrayList<>(50);
        for (String log : flagMap.get(getInput)) {
            ArrayList<String>listForFlag = flagMap.get(getInput);
            if (getInput.equals(GET_NEW_GENRE_PREF)) {
                if (listForFlag.size() == 0) {
                    throw new InvalidFormatCommandException();
                }
                int genre = findGenreID(log);
                if (genre == 0) {
                    throw new InvalidGenreNameEnteredException();
                }
                if (!isRepetitiveGenrePreference(genre)) {
                    throw new GenreDoesNotExistException();
                }
                removeGenrePreferences.add(genre);
            }
            if (getInput.equals(GET_NEW_GENRE_RESTRICT)) {
                if (listForFlag.size() == 0) {
                    throw new InvalidFormatCommandException();
                }
                int genre = findGenreID(log);
                if (genre == 0) {
                    throw new InvalidGenreNameEnteredException();
                }
                if (!isRepetitiveGenreRestrict(genre)) {
                    throw new GenreDoesNotExistException();
                }
                removeGenreRestrict.add(genre);
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
     * Responsible for clearing preference and sort options for a particular category in the userProfile.
     * @param flagMap The elements to be added under a particular category in the userProfile.
     * @param getInput String that signifies a particular category in the userProfile.
     * @throws IOException
     * @throws InvalidFormatCommandException
     */
    public void clearPreference(TreeMap<String, ArrayList<String>> flagMap, String getInput) throws IOException,
            InvalidFormatCommandException {
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

    /**
     * Responsible for clearing the sort options in the userProfile.
     * @throws IOException
     */
    public void clearSortPreference() throws IOException {
        setSort(false, false, false);
    }

    /**
     * Responsible for clearing all the genres set under gene restrictions in the userProfile.
     */
    public void clearGenreRestrict() {
        userProfile.removeGenreIdRestriction(userProfile.getGenreIdRestriction());
    }


    /**
     * Responsible for clearing all the genres set under gene preferences in the userProfile.
     */
    public void clearGenrePreference() {
        userProfile.removeGenreIdPreference(userProfile.getGenreIdPreference());
    }


    /**
     * Responsible for clearing whether to have search results that conatin adult content in the userProfile.
     */
    public void clearAdultPreference() {
        userProfile.setAdult(false);
    }


    /**
     * Responsible for setting sort preferences based on user's input.
     * @param sortOption A integer that corresponds to the user's input.
     * @throws IOException
     */
    private void getSortFromUserInput(int sortOption) throws IOException {
        if (sortOption == 1) {
            setSort(true, false, false);
        } else if (sortOption == 2) {
            setSort(false, true, false);
        } else if (sortOption == 3) {
            setSort(false, false, true);
        }
    }

    /**
     * Updates the components in the SortProfile accordingly.
     *
     * @param isAlphaOrder true when user have entered command to sort results in alphabetical order and otherwise false.
     * @param isLatDatesOrder true when user have entered command to sort results based on release dates and otherwise false.
     * @param isRatingsOrder  true when user have entered command to sort results based on ratings and otherwise false.
     */
    public void setSort(boolean isAlphaOrder, boolean isLatDatesOrder, boolean isRatingsOrder) throws IOException {
        userProfile.setSortByAlphabetical(isAlphaOrder);
        userProfile.setSortByHighestRating(isRatingsOrder);
        userProfile.setSortByLatestRelease(isLatDatesOrder);
        editProfileJson.updateProfile(userProfile);
    }


    /**
     * Responsible for setting user preferences.
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

    /**
     * Responsible for returning the genreId for corresponding genre name.
     * @param genreName A string that correspondes to the genre name.
     * @return GenreId of the genre name.
     * @throws IOException
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
        int genreId = Integer.parseInt(parseToId(genreListString, genreName.trim()));
        return genreId;
        }


    /**
     * Responsible for returning the genre name for corresponding genreId.
     * @param id Integer that corresponds to a genreID.
     * @return Genre name for corresponding genreId.
     * @throws IOException
     */
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

    /**
     * Responsible for parsing and extracting genre name.
     * @param genreListString String from which genre name needs to be extracted.
     * @param id GenreId of the genre name.
     * @return Genre name.
     */
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
