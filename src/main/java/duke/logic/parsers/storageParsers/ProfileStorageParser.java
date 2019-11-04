package duke.logic.parsers.storageParsers;

import duke.commons.Messages;
import duke.commons.exceptions.NoSuchCategoryException;
import duke.commons.exceptions.ParseException;
import duke.logic.parsers.ParserTimeUtil;
import duke.model.profile.ProfileCard;

/**
 * Storage parser for Profile.
 */
public class ProfileStorageParser {
    /**
     * Updates the profile with data from the storage.
     *
     * @param profileCard The profileCard to be updated.
     * @param line The String description of a profile.
     * @throws ParseException If the data is corrupted.
     */
    public static void createProfileFromStorage(ProfileCard profileCard, String line)
            throws ParseException {
        try {
            String[] token = line.split("\\|");
            switch (token[0].strip()) {
            case "person":
                profileCard.setPerson(token[1].strip(), ParserTimeUtil.parseStringToDate(token[2].strip()));
                break;
            case "preference":
                String[] category = {"", "sports", "entertainment", "arts", "lifestyle"};
                for (int i = 1; i < token.length; i++) {
                    try {
                        profileCard.setPreference(category[i], token[i].strip().equals("true"));
                    } catch (NoSuchCategoryException e) {
                        throw new ParseException(Messages.ERROR_DATA_CORRUPTED);
                    }
                }
                break;
            default:
                break;
            }
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException(Messages.ERROR_DATA_CORRUPTED);
        }
    }

    /**
     * Parses a ProfileCard to String format.
     * @param profileCard The profileCard.
     * @return profileString The corresponding String format of the profileCard object.
     */
    public static String toProfileStorageString(ProfileCard profileCard) {
        String profileString = "";
        profileString += "person | " + profileCard.getPersonName() + " | " + profileCard.getPersonBirthday() + "\n";

        profileString += "preference";
        for (Boolean i : profileCard.getPreference()) {
            profileString += " | " + i;
        }
        profileString += "\n";
        return profileString;
    }
}
