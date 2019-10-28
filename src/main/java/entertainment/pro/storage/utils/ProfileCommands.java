package entertainment.pro.storage.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entertainment.pro.model.GenreId;
import entertainment.pro.model.UserProfile;

import java.io.*;
import java.util.ArrayList;
import java.util.TreeMap;


/**
 * class that contains all methods that deal with Profile object.
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
     * constructor for ProfileCommands.
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
    public void addPreference(TreeMap<String, ArrayList<String>> flagMap, String getInput) throws IOException {
        ArrayList<Integer> genrePreferences = new ArrayList<>(50);
        ArrayList<Integer> genreRestrict = new ArrayList<>(50);
        int sortOption;
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
                } else {
                    userProfile.setAdult(false);
                }
                System.out.println("ok so far2...");
            }
            if (getInput.equals(GET_NEW_SORT)) {
                System.out.println("ok so far..." + log);
                sortOption = Integer.parseInt(log);
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
    public void removePreference(TreeMap<String, ArrayList<String>> flagMap, String getInput) throws IOException {
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
            if (userProfile.isAdult()) {
                userProfile.setAdult(false);
                System.out.println("ok so far...0");
            } else {
                userProfile.setAdult(true);
                System.out.println("ok so far...1");
            }
        }
        if (getInput.equals(GET_NEW_SORT)) {
            clearSortPreference();
            System.out.println("ok so far...3");
        }

        userProfile.removeGenreIdPreference(removeGenrePreferences);
        userProfile.removeGenreIdRestriction(removeGenreRestrict);
        editProfileJson.updateProfile(userProfile);
        System.out.println("ok so far5...");

    }

    /**
     * to clear userprofile preferences.
     */
    public void clearPreference(TreeMap<String, ArrayList<String>> flagMap, String getInput) throws IOException {
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
    }

    private void clearSortPreference() {
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


    private void getSortFromUserInput(int sortOption) {
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
    public void setSort(boolean isAlphaOrder, boolean isLatDatesOrder, boolean isRatingsOrder) {
        userProfile.setSortByAlphabetical(isAlphaOrder);
        userProfile.setSortByHighestRating(isRatingsOrder);
        userProfile.setSortByLatestRelease(isLatDatesOrder);
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
            genrePreferences.add(findGenreID(log));
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
            genrePreferences.add(findGenreID(log));
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
            genreRestrictions.add(findGenreID(log));
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
        genreName = genreName.trim();
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = new FileInputStream("EPdata/GenreId.json");
        TypeReference<ArrayList<GenreId>> typeReference = new TypeReference<ArrayList<GenreId>>() {
        };
        ArrayList<GenreId> genreIds = mapper.readValue(inputStream, typeReference);
        for (GenreId log : genreIds) {
            if (log.getGenre().equalsIgnoreCase(genreName)) {
                inputStream.close();
                return log.getId();
            }
        }
        inputStream.close();
        return 0;
    }

    private String findGenreName(int id) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = new FileInputStream("EPdata/GenreId.json");
        TypeReference<ArrayList<GenreId>> typeReference = new TypeReference<ArrayList<GenreId>>() {
        };
        ArrayList<GenreId> genreIds = mapper.readValue(inputStream, typeReference);
        for (GenreId log : genreIds) {
            if (log.getId() == id) {
                inputStream.close();
                return log.getGenre();
            }
        }
        inputStream.close();
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

    /**
     * to rename playlist in userprofile.
     */
    public void renamePlaylist(String oldName, String newName) throws IOException {
        ArrayList<String> playlistNames = userProfile.getPlaylistNames();
        ArrayList<String> newPlaylistNames = new ArrayList<>();
        for (String log : playlistNames) {
            if (!log.equals(oldName)) {
                newPlaylistNames.add(log);
            } else {
                newPlaylistNames.add(newName);
            }
        }
        userProfile.setPlaylistNames(newPlaylistNames);
        editProfileJson.updateProfile(userProfile);
    }
}
