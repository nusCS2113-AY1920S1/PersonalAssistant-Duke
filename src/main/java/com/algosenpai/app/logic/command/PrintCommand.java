package com.algosenpai.app.logic.command;

import com.algosenpai.app.logic.models.QuestionModel;
import com.algosenpai.app.stats.ChapterStat;
import com.algosenpai.app.stats.UserStats;
import com.algosenpai.app.utility.PdfDocumentWriterUtility;
import com.itextpdf.text.DocumentException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class PrintCommand extends Command {

    private ArrayList<QuestionModel> printList;
    private UserStats userStats;

    private ArrayList<String> paragraphs;

    private PdfDocumentWriterUtility pdfWriter = new PdfDocumentWriterUtility();

    /**
     * Create new command.
     * @param commandType type of command.
     * @param specifier specifier.
     * @param input input from user.
     */
    public PrintCommand(CommandEnum commandType, int specifier, String input) {
        super(commandType, specifier, input);
        this.paragraphs = new ArrayList<>();
    }


    /**
     * Creates print command.
     * @param command command type.
     * @param printList list of questions to write to pdf.
     */
    public PrintCommand(Command command, ArrayList<QuestionModel> printList) {
        this(command.getType(), command.getParameter(), command.getUserString());
        this.printList = printList;
        this.prepareQuestions();
    }

    /**
     * Creates print command.
     * @param command command type.
     * @param userStats user stats to write to pdf.
     */
    public PrintCommand(Command command, UserStats userStats) {
        this(command.getType(), command.getParameter(), command.getUserString());
        this.userStats = userStats;
        this.prepareUserStates();
    }

    @Override
    public ArrayList<String> parser() {
        return new ArrayList<>(Arrays.asList(userString.split(" ")));
    }

    /**
     * Prepares question to write to pdf.
     */
    private void prepareQuestions() {
        int questionCount = 1;
        for (QuestionModel question: printList) {
            paragraphs.add("Q" + questionCount++ + ")");
            paragraphs.add(question.getQuestion());
            paragraphs.add(question.getUserAnswer());
            paragraphs.add(question.getAnswer());
            paragraphs.add(question.checkAnswer() ? "Correct" : "Wrong");
        }
    }

    /**
     * Prepares user stats to write to pdf.
     */
    private void prepareUserStates() {
        paragraphs.add(userStats.toString());
    }

    @Override
    public String execute() {
        try {
            ArrayList<String> parsedInput = parser();
            pdfWriter.saveToPdf(paragraphs, parsedInput.get(2));
            return userString;
        } catch (DocumentException | FileNotFoundException e) {
            return "Error writing to file";
        }
    }
}
