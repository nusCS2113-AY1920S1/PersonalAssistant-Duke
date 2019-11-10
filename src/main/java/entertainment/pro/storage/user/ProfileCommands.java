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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.TreeMap;


/**
 * Class contains all methods that deal with Profile object.
 */
public class ProfileCommands {
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
        this.userProfile = userProfile;
        this.editProfileJson = new EditProfileJson();
    }

    /**
     * Responsible for setting the name in the userProfile.
     * @param name The name to be set in the UserProfile.
     * @throws IOException
     */
    public UserProfile setName(String name) throws IOException {
        return userProfile.setUserName(name);
    }

    /**
     * Responsible for setting the age in the userProfile.
     * @param age The age to be set in the userProfile.
     * @throws IOException
     */
    public UserProfile setAge(String age) throws IOException {
        return userProfile.setUserAge(Integer.parseInt(age));
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
    public UserProfile addPreference(TreeMap<String, ArrayList<String>> flagMap, String getInput) throws IOException, InvalidFormatCommandException, InvalidGenreNameEnteredException, DuplicateGenreException {
        ArrayList<Integer> genrePreferences = new ArrayList<>();
        ArrayList<Integer> genreRestrict = new ArrayList<>();
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
                    userProfile = userProfile.setAdult(true);
                } else if (log.equals("false")) {
                    userProfile = userProfile.setAdult(false);
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
                userProfile = getSortFromUserInput(sortOption);
                System.out.println("ok so far3...");
            }
        }
        userProfile = userProfile.addGenreIdPreference(genrePreferences);
        System.out.println("ok so far4...");
        userProfile = userProfile.addGenreIdRestriction(genreRestrict);
        System.out.println("ok so far5...");
        return userProfile;
    }

    /**
     * Responsible for checking whether a genre exist under genre preference in the database.
     * @param genreNumber The ID of the genre that needs to be checked.
     * @return true if genre exist in the database and false otherwise.
     */
    private boolean isRepetitiveGenrePreference(int genreNumber) {
        ArrayList<Integer> genrePref = userProfile.getGenreIdPreference();
        for (int i = 0; i < genrePref.size(); i += 1) {
            if (genreNumber == genrePref.get(i)) {
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
        ArrayList<Integer> genreRestrict = userProfile.getGenreIdRestriction();
        for (int i = 0; i < genreRestrict.size(); i += 1) {
            if (genreNumber == genreRestrict.get(i)) {
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
    public UserProfile removePreference(TreeMap<String, ArrayList<String>> flagMap, String getInput) throws IOException,
            InvalidFormatCommandException, InvalidGenreNameEnteredException, GenreDoesNotExistException {
        ArrayList<Integer> removeGenrePreferences = new ArrayList<>();
        ArrayList<Integer> removeGenreRestrict = new ArrayList<>();
        for (String log : flagMap.get(getInput)) {
            ArrayList<String> listForFlag = flagMap.get(getInput);
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
                    userProfile = userProfile.setAdult(false);
                    System.out.println("ok so far...0");
                } else {
                    userProfile = userProfile.setAdult(true);
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
        userProfile = userProfile.removeGenreIdPreference(removeGenrePreferences);
        userProfile =  userProfile.removeGenreIdRestriction(removeGenreRestrict);
        System.out.println("ok so far5...");
        return userProfile;
    }

    /**
     * Responsible for clearing preference and sort options for a particular category in the userProfile.
     * @param flagMap The elements to be added under a particular category in the userProfile.
     * @param getInput String that signifies a particular category in the userProfile.
     * @throws IOException
     * @throws InvalidFormatCommandException
     */
    public UserProfile clearPreference(TreeMap<String, ArrayList<String>> flagMap, String getInput) throws IOException,
            InvalidFormatCommandException {
        ArrayList<String> getFlag = flagMap.get(getInput);
        if (getFlag.isEmpty()) {
            if (getInput.equals(GET_NEW_GENRE_PREF)) {
                return clearGenrePreference();
            }
            if (getInput.equals(GET_NEW_GENRE_RESTRICT)) {
                return clearGenreRestrict();
            }
            if (getInput.equals(GET_NEW_ADULT_RATING)) {
                return clearAdultPreference();
            }
            if (getInput.equals(GET_NEW_SORT)) {
                return clearSortPreference();
            }
        } else {
            throw new InvalidFormatCommandException();
        }
        return null;
    }

    /**
     * Responsible for clearing the sort options in the userProfile.
     * @throws IOException
     */
    public UserProfile clearSortPreference() throws IOException {
        return setSort(false, false, false);
    }

    /**
     * Responsible for clearing all the genres set under gene restrictions in the userProfile.
     */
    public UserProfile clearGenreRestrict() {
        return userProfile.removeGenreIdRestriction(userProfile.getGenreIdRestriction());
    }


    /**
     * Responsible for clearing all the genres set under gene preferences in the userProfile.
     */
    public UserProfile clearGenrePreference() {
        return userProfile.removeGenreIdPreference(userProfile.getGenreIdPreference());
    }


    /**
     * Responsible for clearing whether to have search results that conatin adult content in the userProfile.
     */
    public UserProfile clearAdultPreference() {
        return userProfile.setAdult(false);
    }


    /**
     * Responsible for setting sort preferences based on user's input.
     * @param sortOption A integer that corresponds to the user's input.
     * @throws IOException
     */
    private UserProfile getSortFromUserInput(int sortOption) throws IOException {
        if (sortOption == 1) {
            return setSort(true, false, false);
        } else if (sortOption == 2) {
            return setSort(false, true, false);
        } else {
            return setSort(false, false, true);
        }
    }

    /**
     * Updates the components in the SortProfile accordingly.
     *
     * @param isAlphaOrder true when user have entered command to sort results in alphabetical order and otherwise false.
     * @param isLatDatesOrder true when user have entered command to sort results based on release dates and otherwise false.
     * @param isRatingsOrder  true when user have entered command to sort results based on ratings and otherwise false.
     */
    public UserProfile setSort(boolean isAlphaOrder, boolean isLatDatesOrder, boolean isRatingsOrder) throws IOException {
        userProfile = userProfile.setSortByAlphabetical(isAlphaOrder);
        userProfile = userProfile.setSortByHighestRating(isRatingsOrder);
        userProfile = userProfile.setSortByLatestRelease(isLatDatesOrder);
        return userProfile;
    }


    /**
     * Responsible for setting user preferences.
     */
    public UserProfile setPreference(TreeMap<String, ArrayList<String>> flagMap) throws IOException {
        if (flagMap.containsKey("-g") && !flagMap.containsKey("-a")) {
            return setGenrePreference(flagMap);
        } else if (flagMap.containsKey("-a") && !flagMap.containsKey("-g")) {
            return setAdultPreference(flagMap.get("-a").get(0));
        } else {
            return setAll(flagMap, flagMap.get("-a").get(0));
        }
    }

    /**
     * set user preferences -- for genre.
     */
    private UserProfile setGenrePreference(TreeMap<String, ArrayList<String>> flagMap) throws IOException {
        ArrayList<Integer> genrePreferences = new ArrayList<>();
        for (String log : flagMap.get("-g")) {
            int id = findGenreID(log);
            genrePreferences.add(id);
        }
        return userProfile.setGenreIdPreference(genrePreferences);
    }

    /**
     * set user preferences -- for adult content restriction.
     * yes = allow adult content
     * no = restrict adult content
     */
    private UserProfile setAdultPreference(String value) throws IOException {
        if (value.equals("yes")) {
            return userProfile.setAdult(true);
        } else {
            return userProfile.setAdult(false);
        }
    }

    /**
     * to allow setting of both genre and adult content restriction preferences at the same time.
     */
    private UserProfile setAll(TreeMap<String, ArrayList<String>> flagMap, String value) throws IOException {
        ArrayList<Integer> genrePreferences = new ArrayList<>();
        for (String log : flagMap.get("-g")) {
            int id = findGenreID(log);
            genrePreferences.add(id);
        }
        userProfile = userProfile.setGenreIdPreference(genrePreferences);
        if (value.equals("yes")) {
            userProfile = userProfile.setAdult(true);
        } else if (value.equals("no")) {
            userProfile = userProfile.setAdult(false);
        }
        return userProfile;
    }

    /**
     * set user restrictions.
     */
    public UserProfile setRestriction(TreeMap<String, ArrayList<String>> flagMap) throws IOException {
        ArrayList<Integer> genreRestrictions = new ArrayList<>(10);
        for (String log : flagMap.get("-g")) {
            int id = findGenreID(log);
            genreRestrictions.add(id);
        }
        return userProfile.setGenreIdRestriction(genreRestrictions);
    }

    /**
     * add restriction to userprofile.
     */
    public UserProfile addRestriction(TreeMap<String, ArrayList<String>> flagMap) throws IOException {
        ArrayList<Integer> genreRestrictions = new ArrayList<>(50);
        for (String log : flagMap.get("-g")) {
            genreRestrictions.add(findGenreID(log));
        }
        return userProfile.addGenreIdRestriction(genreRestrictions);
    }

    /**
     * remove restriction from userprofile.
     */
    public UserProfile removeRestriction(TreeMap<String, ArrayList<String>> flagMap) throws IOException {
        ArrayList<Integer> genreRestrictions = new ArrayList<>(50);
        for (String log : flagMap.get("-g")) {
            genreRestrictions.add(findGenreID(log));
        }
        return userProfile.removeGenreIdRestriction(genreRestrictions);
    }

    public UserProfile clearRestriction(TreeMap<String, ArrayList<String>> flagMap) throws IOException {
        return userProfile.removeGenreIdRestriction(userProfile.getGenreIdRestriction());
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

    public UserProfile addPlaylist(String listName) throws IOException {
        return userProfile.addPlaylist(listName);
    }

    public UserProfile deletePlaylist(String listName) throws IOException {
        return userProfile.deletePlaylist(listName);
    }
}
