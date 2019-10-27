package entertainment.pro.storage.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entertainment.pro.model.GenreId;
import entertainment.pro.model.UserProfile;
import entertainment.pro.storage.utils.EditProfileJson;
import entertainment.pro.ui.MovieHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.TreeMap;


/**
 * class that contains all methods that deal with Profile object
 */
public class ProfileCommands {
    private File genreList;
    private UserProfile userProfile;
    private EditProfileJson editProfileJson;
    private static String GET_NEW_GENRE_PREF = "-g";
    private static String GET_NEW_GENRE_RESTRICT = "-r";
    private static String GET_NEW_SORT = "-s";
    private static String GET_NEW_ADULT_RATING = "-a";


    public ProfileCommands(UserProfile userProfile) throws FileNotFoundException {
        genreList = new File("../../../../EPdata/genreIDlist.txt");
        this.userProfile = userProfile;
        this.editProfileJson = new EditProfileJson();
    }

    public void addPreference(TreeMap<String, ArrayList<String>> flagMap, String getInput) throws IOException {
        ArrayList<Integer> genrePreferences = new ArrayList<>(50);
        ArrayList<Integer> genreRestrict = new ArrayList<>(50);
        int sortOption;
        for (String log : flagMap.get(getInput)){
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


    public void clearGenrePreference() throws IOException {
        userProfile.removeGenreIdPreference(userProfile.getGenreIdPreference());
    }

    public void clearAdultPreference() throws IOException {
        userProfile.setAdult(false);
    }


    public static Integer findGenreID(String genreName) throws IOException {
        genreName = genreName.trim();
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = new FileInputStream("EPdata/GenreId.json");
        TypeReference<ArrayList<GenreId>> typeReference = new TypeReference<ArrayList<GenreId>>() {};
        ArrayList<GenreId> genreIds = mapper.readValue(inputStream, typeReference);
        for (GenreId log : genreIds){
            if (log.getGenre().equalsIgnoreCase(genreName)){
                inputStream.close();
                return log.getId();
            }
        }
        inputStream.close();
        return 0;
    }

    public String findGenreName(int ID) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = new FileInputStream("EPdata/GenreId.json");
        TypeReference<ArrayList<GenreId>> typeReference = new TypeReference<ArrayList<GenreId>>() {};
        ArrayList<GenreId> genreIds = mapper.readValue(inputStream, typeReference);
        for (GenreId log : genreIds){
            if (log.getId() == ID){
                inputStream.close();
                return log.getGenre();
            }
        }
        inputStream.close();
        return "0";
    }

    public String convertToLabel(ArrayList<Integer> userList) throws IOException {
        String labelText = "";
        for (Integer log : userList){
            labelText += findGenreName(log);
            labelText += "\n";
        }
        return labelText;
    }

    public String getAdultLabel() {
        if (userProfile.isAdult()) {
            return "allow";
        } else {
            return "restrict";
        }
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
     * @param isAlphaOrder    true when user have entered command to sort results in alphabetical order and otherwise false.
     * @param isLatDatesOrder true when user have entered command to sort results based on release dates and otherwise false.
     * @param isRatingsOrder  true when user have entered command to sort results based on ratings and otherwise false.
     */
    public void setSort(boolean isAlphaOrder, boolean isLatDatesOrder, boolean isRatingsOrder) {
        userProfile.setSortByAlphabetical(isAlphaOrder);
        userProfile.setSortByHighestRating(isRatingsOrder);
        userProfile.setSortByLatestRelease(isLatDatesOrder);
    }
}