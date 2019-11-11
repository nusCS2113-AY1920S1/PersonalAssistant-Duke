package dolla;

import dolla.ui.UiStrings;

public interface UiTestExpected extends UiStrings {
    String LINE_SEPARATOR = "line.separator";

    String EXPECTED_WELCOME_MSG = welcomeMsg + System.getProperty(LINE_SEPARATOR)
            + line + System.getProperty(LINE_SEPARATOR)
            + dollaModeLogo;

    String EXPECTED_UPDATED_DOLLA_MODE_MSG = line + System.getProperty(LINE_SEPARATOR)
            + CHANGE_MODE_MSG + System.getProperty(LINE_SEPARATOR)
            + dollaModeLogo + System.getProperty(LINE_SEPARATOR)
            + line;

    String EXPECTED_UPDATED_DEBT_MODE_MSG = line + System.getProperty(LINE_SEPARATOR)
            + CHANGE_MODE_MSG + System.getProperty(LINE_SEPARATOR)
            + debtModeLogo + System.getProperty(LINE_SEPARATOR)
            + line;

    String EXPECTED_UPDATED_ENTRY_MODE_MSG = line + System.getProperty(LINE_SEPARATOR)
            + CHANGE_MODE_MSG + System.getProperty(LINE_SEPARATOR)
            + entryModeLogo + System.getProperty(LINE_SEPARATOR)
            + line;

    String EXPECTED_UPDATED_LIMIT_MODE_MSG = line + System.getProperty(LINE_SEPARATOR)
            + CHANGE_MODE_MSG + System.getProperty(LINE_SEPARATOR)
            + limitModeLogo + System.getProperty(LINE_SEPARATOR)
            + line;

    String EXPECTED_UPDATED_SHORTCUT_MODE_MSG = line + System.getProperty(LINE_SEPARATOR)
            + CHANGE_MODE_MSG + System.getProperty(LINE_SEPARATOR)
            + shortcutModeLogo + System.getProperty(LINE_SEPARATOR)
            + line;

    String EXPECTED_ECHO_RECORD_MSG = line + System.getProperty(LINE_SEPARATOR)
            + "\tGot it. I've added this entry: " + System.getProperty(LINE_SEPARATOR)
            + "\t[expense] [$500.0] [1111 sales] [/on 11/11/2019]" + System.getProperty(LINE_SEPARATOR)
            + line;

    String EXPECTED_EXISTING_RECORD_MSG = line + System.getProperty(LINE_SEPARATOR)
            + EXISTING_RECORD_MSG + "limit:" + System.getProperty(LINE_SEPARATOR)
            + "\t[budget] [$10.0] [weekly]" + System.getProperty(LINE_SEPARATOR)
            + MSG_MODIFY + System.getProperty(LINE_SEPARATOR)
            + line;
}
