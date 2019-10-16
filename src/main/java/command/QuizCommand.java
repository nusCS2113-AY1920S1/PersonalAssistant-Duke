package command;
import Dictionary.WordBank;
import storage.Storage;
import ui.Ui;

import java.util.*;

/**
 * Generate a 4-option MCQ. User should answer by index of options, namely 1, 2, 3, 4.
 */
public class QuizCommand extends Command  {
    protected String question;
    protected String answer;
    protected String[] options;
    protected Random random;
    protected ArrayList<Integer> randomNumbers;

    public QuizCommand(){
        this.random = new Random();
        this.options = new String[4];
        this.randomNumbers  = new ArrayList<Integer>();
    }

    @Override
    public void execute(Ui ui, WordBank wordBank, Storage storage){
        int sizeOfWordBank = wordBank.getWordBank().size();
        if(sizeOfWordBank<4){
            ui.quizWordsNotEnough();
            return;
        }
        Object[] wordBankWord = wordBank.getWordBank().keySet().toArray();

        while(randomNumbers.size()<4){
            int r = random.nextInt(sizeOfWordBank);
            if(!isDuplicate(r)){
                randomNumbers.add(r);
            }
        }
        this.question = toString().valueOf(wordBankWord[randomNumbers.get(0)]);
        this.answer = toString().valueOf(wordBank.getWordBank().get(question).getMeaning());
        this.options[0] = answer;
        for(int i=0; i<3; i++){
            String tempWord = toString().valueOf(wordBankWord[randomNumbers.get(i+1)]);
            this.options[i+1] = wordBank.getWordBank().get(tempWord).getMeaning();
        }

        int optionSequence = random.nextInt(4);
        ui.quizDisplay(question, options, optionSequence);
        Scanner in = new Scanner(System.in);
        String userAns =  in.nextLine();
        if(userAns.equals(Integer.toString((4-optionSequence)%4+1))){
            ui.quizResponse(true, answer);
        }
        else{
            ui.quizResponse(false, answer);
        }
        return;
    }


    private boolean isDuplicate(int r){
        for(int i=0; i<randomNumbers.size(); i++){
            if(randomNumbers.get(i).equals(r)){
                return true;
            }
        }
        return false;
    }
}
