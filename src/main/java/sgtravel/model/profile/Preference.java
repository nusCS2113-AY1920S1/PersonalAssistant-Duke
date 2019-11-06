package sgtravel.model.profile;

import sgtravel.commons.exceptions.NoSuchCategoryException;

import java.util.ArrayList;

public class Preference {
    private Boolean sports;
    private Boolean entertainment;
    private Boolean arts;
    private Boolean lifestyle;

    /**
     * Constructs Preference object. Set all preference to false.
     */
    public Preference() {
        sports = false;
        entertainment = false;
        arts = false;
        lifestyle = false;
    }

    /**
     * Sets the preference of user.
     *
     * @param category category of preference to set
     * @param setting setting which user wish to set the preference to
     */
    public void setPreference(String category, Boolean setting) throws NoSuchCategoryException {
        switch (category) {
        case "sports":
            sports = setting;
            break;
        case "entertainment":
            entertainment = setting;
            break;
        case "arts":
            arts = setting;
            break;
        case "lifestyle":
            lifestyle = setting;
            break;
        default:
            throw new NoSuchCategoryException();
        }
    }

    /**
     * Returns all the preference of the user.
     *
     * @return Arraylist of user preference
     */
    public ArrayList<Boolean> getAllPreference() {
        ArrayList<Boolean> all = new ArrayList<>();
        all.add(sports);
        all.add(entertainment);
        all.add(arts);
        all.add(lifestyle);
        return all;
    }
}
