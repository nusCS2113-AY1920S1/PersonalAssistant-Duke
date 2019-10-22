package scene;

import Dictionary.WordBank;
import command.QuizCommand;
import exception.*;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import storage.Storage;
import ui.Ui;

import java.util.ArrayList;

public class QuizScene extends NewScene {
    private QuizCommand quizCommand;
    boolean startQuiz = false;
    private Integer countQuiz;
    private Integer wrongQuiz;
    private ArrayList<String> quizArray;

    public QuizScene(Ui ui, WordBank wordBank, Storage storage, Stage window) {
        super(ui, wordBank, storage, ui.quizGreet(), window);
        setupHandleInput();
        this.countQuiz = 0;
        this.wrongQuiz = 0;
        this.quizArray = new ArrayList<>();
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public String getResponse(String fullCommand)
            throws ChangeSceneException, InvalidAnswerException, WordBankNotEnoughForQuizException, CommandInvalidException {
        if (fullCommand.equals("exit_quiz")) {
            throw new ChangeSceneException();
        } else if (!startQuiz && fullCommand.equals("start")) {
            this.generateQuiz();
            startQuiz = true;
            return ui.quizDisplay(quizCommand.question, quizCommand.options, quizCommand.optionSequence);
        } else if(countQuiz==5){
            startQuiz = false;
            return ui.quizIncorrect(wrongQuiz, countQuiz, quizArray);
        }
        else {
            if(!startQuiz){
                throw new CommandInvalidException(fullCommand);
            } else{
                String s;
                try {
                    int i = Integer.parseInt(fullCommand);
                    if (i < 1 || i > 4) {
                        throw new InvalidAnswerException();
                    }
                    if (fullCommand.equals(Integer.toString((4 - quizCommand.optionSequence) % 4 + 1))) {
                        s = ui.quizResponse(true, quizCommand.answer);
                    } else{
                        s = ui.quizResponse(false, quizCommand.answer);
                        quizArray.add(quizCommand.question+": "+quizCommand.answer);
                        wrongQuiz+=1;
                    }
                    this.generateQuiz();
                    return s + "\n" + ui.quizDisplay(quizCommand.question, quizCommand.options, quizCommand.optionSequence);
                } catch (NumberFormatException e) {
                    throw new InvalidAnswerException();
                }
            }

        }
    }

    private void generateQuiz() throws WordBankNotEnoughForQuizException {
        quizCommand = new QuizCommand();
        this.countQuiz+=1;
        quizCommand.generateQuiz(wordBank);
    }

    @Override
    protected void handleUserInput() throws ChangeSceneException {
        Label userText = new Label(userInput.getText());
        Label dukeText;
        try {
            dukeText = new Label(getResponse(userInput.getText()));
        } catch (InvalidAnswerException | WordBankNotEnoughForQuizException | CommandInvalidException e) {
            dukeText = new Label(e.showError());
        }
        dialogContainer.getChildren().addAll(
                Box.getUserDialog(userText, new ImageView(user)),
                Box.getDukeDialog(dukeText, new ImageView(duke))
        );
        userInput.clear();
    }

    @Override
    public void resolveException(WordUpException e) {
        if (e instanceof ChangeSceneException) {
            window.setScene(new MainScene(ui, wordBank, storage, window).getScene());
        }
    }


}
