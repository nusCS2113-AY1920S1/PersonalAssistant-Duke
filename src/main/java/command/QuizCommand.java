package command;
import Dictionary.WordBank;
import exception.WordBankNotEnoughForQuizException;

import java.util.*;

/**
 * Generate a 4-option MCQ. User should answer by index of options, namely 1, 2, 3, 4.
 */
public class QuizCommand extends Command  {
    public String question;
    public String answer;
    public String[] options;
    protected Random random;
    protected ArrayList<Integer> randomNumbers;
    public int optionSequence;

    public QuizCommand(){
        this.random = new Random();
        this.options = new String[4];
        this.randomNumbers  = new ArrayList<>();
    }

    private boolean isDuplicate(int r){
        for(int i=0; i<randomNumbers.size(); i++){
            if(randomNumbers.get(i).equals(r)){
                return true;
            }
        }
        return false;
    }

    public void generateQuiz(WordBank wordBank) throws WordBankNotEnoughForQuizException {
        int sizeOfWordBank = wordBank.getWordBank().size();
        if(sizeOfWordBank<4){
            throw new WordBankNotEnoughForQuizException();
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

        optionSequence = random.nextInt(4);
    }

}
