package sgtravel.logic.commands;

import sgtravel.commons.Messages;
import sgtravel.commons.exceptions.NoSuchCategoryException;
import sgtravel.commons.exceptions.FileNotSavedException;
import sgtravel.commons.exceptions.ParseException;
import sgtravel.logic.commands.results.CommandResult;
import sgtravel.logic.commands.results.CommandResultText;
import sgtravel.model.Model;

/**
 * Sets the preference base on user input.
 */
public class ProfileSetPreferenceCommand extends Command {
    private String category;
    private Boolean setting;

    /**
     * Constructs ProfileSetPreferenceCommand object.
     * @param category Category of preference to set.
     * @param setting Setting which user wish to set preference to.
     * @throws ParseException If the user try to change settings to contain garbage.
     */
    public ProfileSetPreferenceCommand(String category, String setting) throws ParseException {
        this.category = category.toLowerCase();
        if (setting.equalsIgnoreCase("true")) {
            this.setting = true;
        } else if (setting.equalsIgnoreCase("false")) {
            this.setting = false;
        } else {
            throw new ParseException(Messages.ERROR_INPUT_INVALID_FORMAT);
        }
        if (!this.category.equals("sports") && !this.category.equals("entertainment")
                && !this.category.equals("arts") && !this.category.equals("lifestyle")) {
            throw new ParseException(Messages.ERROR_CATEGORY_NOT_FOUND);
        }
    }

    /**
     * Executes this command and returns a text result.
     * @param model The model containing the profile.
     * @throws NoSuchCategoryException If there is no such category.
     * @throws FileNotSavedException If the data cannot be saved.
     */
    @Override
    public CommandResult execute(Model model) throws NoSuchCategoryException, FileNotSavedException {
        model.getProfileCard().setPreference(category, setting);
        model.save();
        return new CommandResultText("Your preference for " + category + " is set to " + setting);
    }
}
