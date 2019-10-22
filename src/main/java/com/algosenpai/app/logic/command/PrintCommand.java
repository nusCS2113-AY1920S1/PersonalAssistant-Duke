package com.algosenpai.app.logic.command;

import com.algosenpai.app.logic.models.QuestionModel;
import com.algosenpai.app.utility.PdfDocumentWriterUtility;
import com.itextpdf.text.DocumentException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class PrintCommand extends Command {

    ArrayList<QuestionModel> printList;

    PdfDocumentWriterUtility pdfWriter = new PdfDocumentWriterUtility();

    /**
     * Create new command.
     *
     * @param commandType type of command.
     * @param specifier specifier.
     * @param input input from user.
     */
    public PrintCommand(CommandEnum commandType, int specifier, String input) {
        super(commandType, specifier, input);
    }

    private PrintCommand(CommandEnum commandType, int specifier, String input, ArrayList<QuestionModel> printList)  {
        this(commandType, specifier, input);
        this.printList = printList;
        this.execute();
    }

    public PrintCommand(Command command, ArrayList<QuestionModel> printList) {
        this(command.getType(), command.getParameter(), command.getUserString(), printList);
    }

    @Override
    public ArrayList<String> parser() {
        return new ArrayList<>(Arrays.asList(userString.split(" ")));
    }

    @Override
    public String execute() {
        try {
            ArrayList<String> paragraphs = new ArrayList<>();
            int questionCount = 1;
            for (QuestionModel question: printList) {
                paragraphs.add("Q" + questionCount++ + ")");
                paragraphs.add(question.getQuestion());
                paragraphs.add(question.getUserAnswer());
                paragraphs.add(question.getAnswer());
                paragraphs.add(question.checkAnswer() ? "Correct" : "Wrong");
            }
            ArrayList<String> parsedInput = parser();
            pdfWriter.saveToPdf(paragraphs, parsedInput.get(2));
            return userString;
        } catch (DocumentException | FileNotFoundException e) {
            return "Error writing to file";
        }

    }
}
