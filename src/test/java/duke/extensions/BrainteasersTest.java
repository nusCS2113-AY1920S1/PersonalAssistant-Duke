package duke.extensions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class BrainteasersTest {
    private Brainteasers brainteasers = new Brainteasers();

    /**
     * Tests for correct brainteaser, assert true if .getRandom() equals one of the questions
     */
    @Test
    public void getRandomTest() {
        String randomBrainteaser = brainteasers.getRandom();
        String brainTeaser1 = "I left my campsite and hiked south for 3 miles. Then I turned "
                + "east and hiked for 3 \nmiles." + " I then turned north and hiked for 3 miles, at "
                + "which time I came upon a bear \ninside my tent eating my food! What color was the bear?";
        String brainTeaser2 = "A man is looking at a photograph of someone. His friend asks who it is. "
                + "The man \nreplies," + " 'Brothers and sisters, I have none. But that man’s "
                + "father is my father’s \nson.' was in the photograph?";
        String brainTeaser3 = "What five-letter word becomes shorter when you add two letters to it?";
        String brainTeaser4 = "I am the beginning of sorrow and the end of sickness. You cannot express happiness "
                + "\nwithout me yet I am in the midst of crosses. I am always in risk yet never in \ndanger."
                + "You may find me in the sun, but I am never out of darkness.";
        String brainTeaser5 = "A man takes his car to a hotel. Upon reaching the hotel, he is immediately "
                + "declared bankrupt. \nWhy?";
        String brainTeaser6 = "Two fathers and two sons eat exactly one sandwich each, but they only eat "
                + "\nthree sandwiches combined.";
        boolean result = randomBrainteaser.equals(brainTeaser1)
                || randomBrainteaser.equals(brainTeaser2)
                || randomBrainteaser.equals(brainTeaser3)
                || randomBrainteaser.equals(brainTeaser4)
                || randomBrainteaser.equals(brainTeaser5)
                || randomBrainteaser.equals(brainTeaser6);
        assertTrue(result);
    }

    /**
     * Asserts that answer from getAnswer() equals the expected answer
     */
    @Test
    public void getAnswerTest() {
        String brainTeaserTest = "A man is looking at a photograph of someone. His friend asks who it is. "
                + "The man \nreplies," + " 'Brothers and sisters, I have none. But that man’s father "
                + "is my father’s \nson.' was in the photograph?";
        String expectedAnswer = "His son";
        String actualAnswer = brainteasers.getAnswer(brainTeaserTest);
        assertEquals(expectedAnswer, actualAnswer);
    }
}
