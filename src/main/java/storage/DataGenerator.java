package storage;

import common.LoggerController;
import gui.Window;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//@@author AugGust
public class DataGenerator {
    /**
     * Checks if data files exist, if not generate them with premade data
     *
     * @param taskFile   taskFile File object
     * @param memberFile memberFile File object
     */
    public static void generateFiles(File taskFile, File memberFile) {
        taskFile.getParentFile().mkdirs();
        memberFile.getParentFile().mkdirs();

        boolean filesExist = taskFile.exists() && memberFile.exists();
        if (filesExist) {
            return;
        }

        LoggerController.logInfo(DataGenerator.class, "Unable to locate data files. Generating dummy data");

        try {
            taskFile.createNewFile();
            memberFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter writer = new FileWriter(taskFile);
            writer.write("[\n"
                    + "  {\n"
                    + "    \"name\": \"Do UI Mockup\",\n"
                    + "    \"isDone\": true,\n"
                    + "    \"memberList\": [\n"
                    + "      \"Jason\"\n"
                    + "    ],\n"
                    + "    \"skillReqList\": []\n"
                    + "  },\n"
                    + "  {\n"
                    + "    \"name\": \"Add Data Generator\",\n"
                    + "    \"isDone\": false,\n"
                    + "    \"memberList\": [\n"
                    + "      \"Jiaying\"\n"
                    + "    ],\n"
                    + "    \"skillReqList\": []\n"
                    + "  },\n"
                    + "  {\n"
                    + "    \"name\": \"Restucture backend\",\n"
                    + "    \"isDone\": true,\n"
                    + "    \"memberList\": [\n"
                    + "      \"Justin\"\n"
                    + "    ],\n"
                    + "    \"skillReqList\": []\n"
                    + "  },\n"
                    + "  {\n"
                    + "    \"name\": \"Submit PPP\",\n"
                    + "    \"isDone\": false,\n"
                    + "    \"memberList\": [\n"
                    + "      \"Annie\",\n"
                    + "      \"Jiaying\",\n"
                    + "      \"Justin\",\n"
                    + "      \"Yuheng\",\n"
                    + "      \"Jason\"\n"
                    + "    ],\n"
                    + "    \"skillReqList\": [],\n"
                    + "    \"time\": \"Nov 11, 2019, 11:59:00 PM\"\n"
                    + "  },\n"
                    + "  {\n"
                    + "    \"name\": \"Finish Developer Guide\",\n"
                    + "    \"isDone\": false,\n"
                    + "    \"memberList\": [],\n"
                    + "    \"skillReqList\": [],\n"
                    + "    \"time\": \"Nov 11, 2019, 11:59:00 PM\"\n"
                    + "  },\n"
                    + "  {\n"
                    + "    \"name\": \"Finish User Guide\",\n"
                    + "    \"isDone\": false,\n"
                    + "    \"memberList\": [],\n"
                    + "    \"skillReqList\": [],\n"
                    + "    \"time\": \"Nov 11, 2019, 11:59:00 PM\"\n"
                    + "  },\n"
                    + "  {\n"
                    + "    \"name\": \"Release New JAR v1.4\",\n"
                    + "    \"isDone\": false,\n"
                    + "    \"memberList\": [],\n"
                    + "    \"skillReqList\": [],\n"
                    + "    \"time\": \"Nov 11, 2019, 11:59:00 PM\"\n"
                    + "  },\n"
                    + "  {\n"
                    + "    \"name\": \"Study for CS2113 Paper\",\n"
                    + "    \"isDone\": false,\n"
                    + "    \"memberList\": [],\n"
                    + "    \"skillReqList\": []\n"
                    + "  },\n"
                    + "  {\n"
                    + "    \"name\": \"Generate JavaDocs\",\n"
                    + "    \"isDone\": false,\n"
                    + "    \"memberList\": [],\n"
                    + "    \"skillReqList\": []\n"
                    + "  },\n"
                    + "  {\n"
                    + "    \"name\": \"Submit Documents on Luminus\",\n"
                    + "    \"isDone\": false,\n"
                    + "    \"memberList\": [],\n"
                    + "    \"skillReqList\": [],\n"
                    + "    \"time\": \"Nov 11, 2019, 11:59:00 PM\"\n"
                    + "  },\n"
                    + "  {\n"
                    + "    \"name\": \"Demo Project\",\n"
                    + "    \"isDone\": false,\n"
                    + "    \"memberList\": [\n"
                    + "      \"Jiaying\",\n"
                    + "      \"Annie\",\n"
                    + "      \"Jason\",\n"
                    + "      \"Yuheng\",\n"
                    + "      \"Justin\"\n"
                    + "    ],\n"
                    + "    \"skillReqList\": [],\n"
                    + "    \"time\": \"Nov 14, 2019, 2:00:00 PM\"\n"
                    + "  },\n"
                    + "  {\n"
                    + "    \"name\": \"Test Other\\'s Project\",\n"
                    + "    \"isDone\": false,\n"
                    + "    \"memberList\": [],\n"
                    + "    \"skillReqList\": [],\n"
                    + "    \"time\": \"Nov 15, 2019, 4:00:00 PM\"\n"
                    + "  },\n"
                    + "  {\n"
                    + "    \"name\": \"View issues reported by others\",\n"
                    + "    \"isDone\": false,\n"
                    + "    \"memberList\": [],\n"
                    + "    \"skillReqList\": []\n"
                    + "  },\n"
                    + "  {\n"
                    + "    \"name\": \"Verify authencity of bugs\",\n"
                    + "    \"isDone\": false,\n"
                    + "    \"memberList\": [],\n"
                    + "    \"skillReqList\": []\n"
                    + "  },\n"
                    + "  {\n"
                    + "    \"name\": \"Report false bugs\",\n"
                    + "    \"isDone\": false,\n"
                    + "    \"memberList\": [],\n"
                    + "    \"skillReqList\": []\n"
                    + "  },\n"
                    + "  {\n"
                    + "    \"name\": \"Take CS2113 Final Paper\",\n"
                    + "    \"isDone\": false,\n"
                    + "    \"memberList\": [],\n"
                    + "    \"skillReqList\": [],\n"
                    + "    \"time\": \"Nov 23, 2019, 1:00:00 PM\",\n"
                    + "    \"reminder\": \"Nov 22, 2019, 1:00:00 PM\"\n"
                    + "  },\n"
                    + "  {\n"
                    + "    \"name\": \"Submit Feedback for Module\",\n"
                    + "    \"isDone\": true,\n"
                    + "    \"memberList\": [],\n"
                    + "    \"skillReqList\": []\n"
                    + "  },\n"
                    + "  {\n"
                    + "    \"name\": \"Attend CS2113 Lecture\",\n"
                    + "    \"isDone\": true,\n"
                    + "    \"memberList\": [],\n"
                    + "    \"skillReqList\": []\n"
                    + "  },\n"
                    + "  {\n"
                    + "    \"name\": \"Storyboarding for Project Demo\",\n"
                    + "    \"isDone\": false,\n"
                    + "    \"memberList\": [],\n"
                    + "    \"skillReqList\": []\n"
                    + "  },\n"
                    + "  {\n"
                    + "    \"name\": \"Finish CS2113\",\n"
                    + "    \"isDone\": false,\n"
                    + "    \"memberList\": [],\n"
                    + "    \"skillReqList\": []\n"
                    + "  }\n"
                    + "]");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter writer = new FileWriter(memberFile);
            writer.write("[\n"
                    + "  {\n"
                    + "    \"name\": \"Jason\",\n"
                    + "    \"taskList\": [\n"
                    + "      \"Do UI Mockup\",\n"
                    + "      \"Submit PPP\",\n"
                    + "      \"Demo Project\"\n"
                    + "    ],\n"
                    + "    \"skillList\": []\n"
                    + "  },\n"
                    + "  {\n"
                    + "    \"name\": \"Annie\",\n"
                    + "    \"taskList\": [\n"
                    + "      \"Submit PPP\",\n"
                    + "      \"Demo Project\"\n"
                    + "    ],\n"
                    + "    \"skillList\": []\n"
                    + "  },\n"
                    + "  {\n"
                    + "    \"name\": \"Jiaying\",\n"
                    + "    \"taskList\": [\n"
                    + "      \"Add Data Generator\",\n"
                    + "      \"Submit PPP\",\n"
                    + "      \"Demo Project\"\n"
                    + "    ],\n"
                    + "    \"skillList\": []\n"
                    + "  },\n"
                    + "  {\n"
                    + "    \"name\": \"Justin\",\n"
                    + "    \"taskList\": [\n"
                    + "      \"Restucture backend\",\n"
                    + "      \"Submit PPP\",\n"
                    + "      \"Demo Project\"\n"
                    + "    ],\n"
                    + "    \"skillList\": []\n"
                    + "  },\n"
                    + "  {\n"
                    + "    \"name\": \"Yuheng\",\n"
                    + "    \"taskList\": [\n"
                    + "      \"Submit PPP\",\n"
                    + "      \"Demo Project\"\n"
                    + "    ],\n"
                    + "    \"skillList\": []\n"
                    + "  }\n"
                    + "]");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
