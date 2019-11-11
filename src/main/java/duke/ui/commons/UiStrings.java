package duke.ui.commons;

import java.util.Map;

import static java.util.Map.entry;

public class UiStrings {
    public static final String MESSAGE_WELCOME_GREET = "Hello! I'm Dr. Duke.";
    public static final String MESSAGE_WELCOME_QUESTION = "What can I do for you today?";
    public static String MESSAGE_GOODBYE = "Thank you for using Dr. Duke. Hope to see you again soon!";
    public static String MESSAGE_ERROR_LAUNCH = "Fatal error encountered on application launch";
    public static String MESSAGE_ERROR_OPEN_CONTEXT = "Unable to open context window.";
    public static String MESSAGE_ERROR_WRITE_COMMAND_HISTORY = "Unable to write command history! "
            + "Some data may have been lost,";
    public static String MESSAGE_ERROR_UNINITIALISED_PATIENT_LIST = "Patient list has not been initialised.";
    public static String MESSAGE_ERROR_UNINITIALISED_PATIENT = "Patient has not been initialised.";
    public static String MESSAGE_ERROR_UNINITIALISED_IMPRESSION = "Impression has not been initialised.";
    public static String MESSAGE_ERROR_UNINITIALISED_DUKEDATA = "Data has not been initialised.";

    public static String LOG_INFO_LAUNCH_UI = "Starting UI...";
    public static String LOG_ERROR_LAUNCH_UI = "Fatal error encountered on application launch.";
    public static String LOG_INFO_LAUNCH_HELP = "Initialising Help window.";
    public static String LOG_INFO_LAUNCH_HOME = "Initialising Home window.";
    public static String LOG_INFO_LAUNCH_COMMAND = "Initialising Command window.";
    public static String LOG_INFO_SWITCH_CONTEXT = "Switching UI context...";

    public static String DISPLAY_ALLERGIES_NONE = "No allergies";
    public static String DISPLAY_HISTORY_NOT_SET = "No medical history";
    public static String DISPLAY_AGE_NOT_SET = "No age set";
    public static String DISPLAY_HEIGHT_NOT_SET = "No height set";
    public static String DISPLAY_WEIGHT_NOT_SET = "No weight set";
    public static String DISPLAY_NUMBER_NOT_SET = "No number set";
    public static String DISPLAY_ADDRESS_NOT_SET = "No address set";

    public static final Map<String, String> SEARCH_HELPER = Map.ofEntries(
            entry("Patient", "/images/patient.png"),
            entry("Impression", "/images/impression_name.png"),
            entry("Observation", "/images/observation.png"),
            entry("Result", "/images/result.png"),
            entry("Investigation", "/images/investigation.png"),
            entry("Medicine", "/images/treatment.png"),
            entry("Plan", "/images/plan.png")
    );
}
