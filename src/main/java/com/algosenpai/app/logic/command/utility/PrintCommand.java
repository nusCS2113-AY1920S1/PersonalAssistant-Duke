package com.algosenpai.app.logic.command.utility;

import com.algosenpai.app.logic.command.Command;
import com.algosenpai.app.logic.models.QuestionModel;
import com.algosenpai.app.stats.UserStats;
import com.algosenpai.app.utility.PdfDocumentWriterUtility;
import java.util.ArrayList;

public class PrintCommand extends Command {

    private ArrayList<QuestionModel> quizList;
    private ArrayList<QuestionModel> archiveList;
    private UserStats userStats;

    /**
     * Pdf writer.
     */
    PdfDocumentWriterUtility pdfWriter = new PdfDocumentWriterUtility();

    /**
     * Initializes command to decide what print action to take.
     * @param inputs input from user.
     */
    public PrintCommand(ArrayList<String> inputs) {
        super(inputs);
    }

    /**
     * Initializes command to decide what print action to take.
     * @param inputs The input from the user.
     * @param userStats The user's stats.
     * @param archiveList The user's archive list.
     * @param quizList The user's quiz list.
     */
    public PrintCommand(ArrayList<String> inputs, UserStats userStats,
                        ArrayList<QuestionModel> archiveList, ArrayList<QuestionModel> quizList) {
        super(inputs);
        this.userStats = userStats;
        this.archiveList = archiveList;
        this.quizList = quizList;
    }

    /**
     * Returns message to show users how to use print functionality.
     * @return message to show print functionality.
     */
    @Override
    public String execute() {
        String errorString = "Please use the following format\n"
                + "print <archive | quiz |  user> <filename>.pdf";
        try {
            switch (inputs.get(1)) {
            case "user":
                return new PrintUserCommand(inputs, userStats).execute();
            case "archive":
                return new PrintArchiveCommand(inputs, archiveList).execute();
            case "quiz":
                return new PrintQuizCommand(inputs, quizList).execute();
            default:
                return errorString;
            }
        } catch (IndexOutOfBoundsException e) {
            return errorString;
        }
    }

    boolean isPdfFileExtension(String filename) {
        return filename.toLowerCase().endsWith(".pdf");
    }

    boolean isEmpty(ArrayList<String> paragraphs) {
        return paragraphs.size() == 0;
    }

}
