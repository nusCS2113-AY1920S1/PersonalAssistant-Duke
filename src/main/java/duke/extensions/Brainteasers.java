package duke.extensions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Brainteaser class that contains questions and answers stored in an array list and a hashmap for question answer pair
 */
public class Brainteasers {
    private ArrayList<String> questions = new ArrayList<>();
    private  HashMap<String,String> questionsAndAnswer = new HashMap<>();
    private String none = "none";
    private String noneReply = "There has not been any question asked yet!";
    private  String brainTeaser1 = "I left my campsite and hiked south for 3 miles. Then I turned " +
            "east and hiked for 3 \nmiles." + " I then turned north and hiked for 3 miles, at which time I came upon a "
            + "bear \ninside my tent eating my food! What color was the bear?";
    private  String answer1 = "White. The only place you can hike 3 miles south, then east for 3 miles, " +
            "then north for 3 " + "miles and end up back at your starting point is the North Pole. "
            + "Polar bears are the only bears that live at the North Pole, and they are white.";
    private   String brainTeaser2 = "A man is looking at a photograph of someone. His friend asks who it is. " +
            "The man \nreplies," + " 'Brothers and sisters, I have none. But that man’s father is my father’s \nson.'"
            + " was in the photograph?";
    private   String answer2 = "His son";
    private   String brainTeaser3 = "What five-letter word becomes shorter when you add two letters to it?";
    private  String answer3 = "Short";
    private  String brainTeaser4 = "I am the beginning of sorrow and the end of sickness. You cannot express happiness "
            + "\nwithout me yet I am in the midst of crosses. I am always in risk yet never in \ndanger."
            + "You may find me in the sun, but I am never out of darkness.";
    private String answer4 = "The letter S";
    private  String brainTeaser5 = "A man takes his car to a hotel. Upon reaching the hotel, he is immediately " +
            "declared bankrupt. \nWhy?";
    private String answer5 = "The man is playing Monopoly. He lands on a property with a hotel and doesn’t " +
            "have enough money to pay the rent.";
    private  String brainTeaser6 = "Two fathers and two sons eat exactly one sandwich each, but they only eat " +
            "\nthree sandwiches combined.";
    private String answer6 = "Grandfather, father and son";

    /**
     * Constructor that adds all the answers and questions to the respective data structures
     */
    public Brainteasers() {
        questions.add(brainTeaser1);
        questions.add(brainTeaser2);
        questions.add(brainTeaser3);
        questions.add(brainTeaser4);
        questions.add(brainTeaser5);
        questions.add(brainTeaser6);
        questionsAndAnswer.put(brainTeaser1, answer1);
        questionsAndAnswer.put(brainTeaser2, answer2);
        questionsAndAnswer.put(brainTeaser3, answer3);
        questionsAndAnswer.put(brainTeaser4, answer5);
        questionsAndAnswer.put(brainTeaser5, answer5);
        questionsAndAnswer.put(brainTeaser6, answer6);
        questionsAndAnswer.put(none, noneReply);
    }

    /**
     * Method to get a random question
     * @return a string of the question to be asked
     */
    public String getRandom() {
        Random random = new Random();
        int randomIndex = random.nextInt(6);
        return questions.get(randomIndex);
    }

    /**
     * References the hashmap to get answer of the question
     * @param question
     * @return
     */
    public String getAnswer(String question) {
        return questionsAndAnswer.get(question);
    }
}
