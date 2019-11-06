package frontend;

import farmio.Farmer;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


/**
 * Creates a virtual game console on the terminal.
 */
public class GameConsole {
    static final int FULL_CONSOLE_HEIGHT = 22;
    static final int FULL_CONSOLE_WIDTH = 103;
    public static final int FRAME_SECTION_HEIGHT = 18;
    static final int FRAME_SECTION_WIDTH = 55;
    static final int USER_CODE_SECTION_WIDTH = 31;

    private static final int LEVEL_SECTION_WIDTH = 10;
    private static final int DAY_SECTION_WIDTH = 8;
    private static final int LOCATION_SECTION_WIDTH = 20;
    private static final int ASSET_SECTION_Y_POSITION_WRT_FRAME = 7;
    private static final int LEFT_COLUMN_SECTION_WIDTH = 15;

    private static final String TOP_BORDER = "."
            + "_".repeat(FULL_CONSOLE_WIDTH)
            + ".\n";
    private static final String GOAL_TITLE = "|"
            + "----" + AsciiColours.RED
            + "<GOALS>"
            + AsciiColours.SANE
            + "----"
            + "|"
            + AsciiColours.HIGH_INTENSITY
            + AsciiColours.BLACK;
    private static final String CODE_TITLE = AsciiColours.SANE
            + "|"
            + "-".repeat(12)
            + AsciiColours.CYAN
            + "<CODE>"
            + AsciiColours.SANE
            + "-".repeat(13)
            + "|\n";
    private static final String BOX_BOTTOM_BORDER = "|"
            + "_".repeat(LEFT_COLUMN_SECTION_WIDTH)
            + "|"
            + "_".repeat(FRAME_SECTION_WIDTH)
            + "|"
            + "_".repeat(9)
            + "|"
            + "_".repeat(21)
            + "|\n";
    private static final String ASSETS_TITLE = "---"
            + AsciiColours.YELLOW
            + "<ASSETS>"
            + AsciiColours.SANE
            + "----";
    private static final String MENU_SEPARATOR = "|"
            + "_".repeat(LEFT_COLUMN_SECTION_WIDTH)
            + "|"
            + "_".repeat(FRAME_SECTION_WIDTH)
            + "|"
            + "_".repeat(USER_CODE_SECTION_WIDTH)
            + "|\n";
    private static final String MENU_TITLE = "|"
            + AsciiColours.BACKGROUND_BLUE
            + AsciiColours.HIGH_INTENSITY
            + AsciiColours.UNDERLINE
            + " [exit] to exit"
            + " ".repeat(6)
            + "[menu]"
            + " for full instruction list or settings"
            + " ".repeat(10)
            + "[hint] for hint on <CODE>"
            + " ".repeat(3)
            + AsciiColours.SANE
            + "|\n";
    private static final String TOO_LONG = "<";
    private static Map<String,Integer> previousAssetSet;

    /**
     * Pads a String with spaces to ensure it takes up exaclty it's allocated space.
     * @param content to be padded with spacing.
     * @param totalSpace that the horizontalPanel should take.
     */
    private static String horizontalPanel(String content, int totalSpace) {
        int numerOfSpaces = totalSpace - content.length();
        if (numerOfSpaces >= 0) {
            return content + " ".repeat(numerOfSpaces);
        } else {
            return content.substring(0, totalSpace - 1) + AsciiColours.YELLOW + TOO_LONG + AsciiColours.SANE;
        }
    }

    private static String horizontalCentralisedPanel(String content, int totalSpace) {
        if (totalSpace > content.length()) {
            content = " ".repeat((totalSpace - content.length()) / 2) + content;
        }
        return horizontalPanel(content, totalSpace);
    }

    /**
     * Creates the virtual Game console with all game variable displayed.
     * @param frame the ascii art picture in the center of the console.
     * @param farmer The farmer who's statistics will be displayed on the console.
     * @param goalMap The list of Goals the farmer has to accomplish.
     * @param objective The mission of the level.
     * @return the virtual Game console as a String.
     */
    static String fullconsole(ArrayList<String> frame, Farmer farmer, Map<String, Integer> goalMap, String objective) {
        StringBuilder output = new StringBuilder();
        String location = farmer.getLocation();
        double level = farmer.getLevel();
        ArrayList<String> userCode = farmer.getTasks().toStringArray();
        ArrayList<String> assets = formatAssets(farmer.getAssets(), goalMap);
        ArrayList<String> goals;
        goals = (level == 1.1) ? tutorial1_1Goals(location) : formatGoals(goalMap, farmer.getAssets());
        userCode = formatAndHighlightCode(userCode, farmer.getCurrentTask(), farmer.isHasfailedCurrentTask());
        output.append(AsciiColours.SANE).append(TOP_BORDER);
        output.append("|   " + AsciiColours.BLUE).append(horizontalPanel("Level: "
                        + level, LEVEL_SECTION_WIDTH)).append(AsciiColours.SANE).append("  |");
        output.append(AsciiColours.RED).append("Objective: ").append(AsciiColours.SANE)
                .append(AsciiColours.HIGH_INTENSITY).append(objective).append(AsciiColours.SANE)
                .append(" ".repeat(FRAME_SECTION_WIDTH - objective.length() - 11));
        output.append("|").append(AsciiColours.MAGENTA).append(horizontalPanel("Day: "
                        + farmer.getDay(), DAY_SECTION_WIDTH)).append(AsciiColours.SANE).append(" ");
        output.append("| ").append(AsciiColours.GREEN).append(horizontalPanel("Location: "
                        + location, LOCATION_SECTION_WIDTH)).append(AsciiColours.SANE);
        output.append("|\n");
        output.append(BOX_BOTTOM_BORDER);
        output.append(GOAL_TITLE).append(horizontalCentralisedPanel("Farmer " + farmer.getName()
                        + "'s Adventure", FRAME_SECTION_WIDTH)).append(CODE_TITLE);
        for (int i = 0; i < FRAME_SECTION_HEIGHT; i++) {
            if (i < ASSET_SECTION_Y_POSITION_WRT_FRAME) {
                output.append("|").append(goals.get(i)).append(frame.get(i)).append(userCode.get(i)).append("\n");
            } else if (i == ASSET_SECTION_Y_POSITION_WRT_FRAME) {
                output.append("|").append(ASSETS_TITLE).append(frame.get(i)).append(userCode.get(i)).append("\n");
            } else {
                output.append("|").append(assets.get(i - ASSET_SECTION_Y_POSITION_WRT_FRAME - 1)).append(frame.get(i))
                        .append(userCode.get(i)).append("\n");
            }
        }
        output.append(MENU_SEPARATOR);
        output.append(MENU_TITLE);
        return output.toString() + AsciiColours.SANE;
    }

    /**
     * Formats and highlight current code in execution.
     * @param userCode The user's code.
     * @param currentTask The current task being executed.
     * @param hasFailedCurrentTask if the current task has failed execution.
     * @return a list of formatted code with relevant highlights.
     */
    private static ArrayList<String> formatAndHighlightCode(ArrayList<String> userCode, int currentTask,
                                                            boolean hasFailedCurrentTask) {
        ArrayList<String> userCodeOutput = new ArrayList<>();
        while (userCode.size() < FRAME_SECTION_HEIGHT) {
            userCode.add("");
        }
        int i = 0;
        for (String s: userCode) {
            if (i == currentTask && !hasFailedCurrentTask) {
                userCodeOutput.add(AsciiColours.HIGHLIGHT + horizontalPanel(s, USER_CODE_SECTION_WIDTH)
                        + AsciiColours.SANE + "|");
            } else if (i == currentTask) {
                userCodeOutput.add(AsciiColours.ERROR + horizontalPanel(s, USER_CODE_SECTION_WIDTH)
                        + AsciiColours.SANE + "|");
            } else {
                userCodeOutput.add(horizontalPanel(s, USER_CODE_SECTION_WIDTH) + "|");
            }
            i++;
        }
        return userCodeOutput;
    }

    /**
     * Creates a blank console where the frame occupies the whole console.
     * @param frame the ascii image.
     * @return The blank console.
     */
    static String blankConsole(ArrayList<String> frame) {
        StringBuilder output = new StringBuilder();
        output.append(AsciiColours.SANE).append(TOP_BORDER);
        for (int i = 0; i < FULL_CONSOLE_HEIGHT; i++) {
            output.append(frame.get(i)).append("\n");
        }
        return output.toString();
    }


    /**
     * Specific goal creaetion for level 1.1 since the goal is a change in location.
     * @param location the current location of the user.
     * @return The formatted goals.
     */
    private static ArrayList<String> tutorial1_1Goals(String location) {
        ArrayList<String> goals = new ArrayList<>();
        if (location.equals("Market")) {
            goals.add(AsciiColours.DONE + "Location:Market" + AsciiColours.SANE);
        } else {
            goals.add(AsciiColours.NOT_DONE + "Location:Market" + AsciiColours.SANE);
        }
        while (goals.size() < ASSET_SECTION_Y_POSITION_WRT_FRAME) {
            goals.add(" ".repeat(LEFT_COLUMN_SECTION_WIDTH));
        }
        return goals;
    }

    /**
     * Formats Goals and changes goal font when they are reached.
     * @param goals the goals of the current level.
     * @param assets the current assets of the farmer.
     * @return The formatted goals.
     */
    private static ArrayList<String> formatGoals(Map<String, Integer> goals, Map<String, Integer> assets) {
        ArrayList<String> formattedGoals = new ArrayList<>();
        Set<Map.Entry<String,Integer>> goalSet = goals.entrySet();
        for (Map.Entry<String,Integer> goal:goalSet) {
            String s = goal.getKey() + ": " +  goal.getValue();
            if (assets.containsKey(goal.getKey()) && goal.getValue() <= assets.get(goal.getKey())
                    && goal.getValue() > 0) {
                formattedGoals.add(0, AsciiColours.DONE + horizontalPanel(s, LEFT_COLUMN_SECTION_WIDTH)
                        + AsciiColours.SANE);
            } else if ((assets.containsKey(goal.getKey()) && goal.getValue() > 0)) {
                formattedGoals.add(AsciiColours.NOT_DONE + horizontalPanel(s, LEFT_COLUMN_SECTION_WIDTH)
                        + AsciiColours.SANE);
            }
        }
        while (formattedGoals.size() < ASSET_SECTION_Y_POSITION_WRT_FRAME) {
            formattedGoals.add(" ".repeat(LEFT_COLUMN_SECTION_WIDTH));
        }
        return formattedGoals;
    }

    /**
     * Formats the Assets of the user and highlights when there is a change.
     * @param assets the current assets of the user.
     * @param goals the goals of the current level.
     * @return the formatted Assets.
     */
    private static ArrayList<String> formatAssets(Map<String, Integer> assets, Map<String, Integer> goals) {
        ArrayList<String> formattedAssets = new ArrayList<>();
        Set<Map.Entry<String,Integer>> assetSet = assets.entrySet();
        for (Map.Entry<String,Integer> asset:assetSet) {
            String s = asset.getKey() + ": " +  asset.getValue();
            String toAdd = horizontalPanel(s, LEFT_COLUMN_SECTION_WIDTH);
            if (previousAssetSet != null && previousAssetSet.containsKey(asset.getKey())
                    && !previousAssetSet.get(asset.getKey()).equals(asset.getValue())) {
                s = asset.getKey() + ": " +  previousAssetSet.get(asset.getKey()) + "->" + asset.getValue();
                toAdd = horizontalPanel(s, LEFT_COLUMN_SECTION_WIDTH);
                toAdd = AsciiColours.YELLOW + AsciiColours.BACKGROUND_MAGENTA + toAdd + AsciiColours.SANE;
            }
            if ((!goals.containsKey(asset.getKey()) || goals.get(asset.getKey()) <= 0)) {
                formattedAssets.add(toAdd);
            } else {
                formattedAssets.add(0,  toAdd);
            }
        }
        while (formattedAssets.size() < FRAME_SECTION_HEIGHT - ASSET_SECTION_Y_POSITION_WRT_FRAME) {
            formattedAssets.add(" ".repeat(LEFT_COLUMN_SECTION_WIDTH));
        }
        previousAssetSet = new HashMap<>();
        previousAssetSet.putAll(assets);
        return formattedAssets;
    }
}