package owlmoney.model.card;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;

import owlmoney.model.card.exception.CardException;
import owlmoney.model.transaction.Expenditure;
import owlmoney.model.transaction.Transaction;
import owlmoney.storage.Storage;
import owlmoney.ui.Ui;

class CardListTest {
    private static final String NEWLINE = System.lineSeparator();
    private static final DateFormat temp = new SimpleDateFormat("dd/MM/yyyy");
    private static final String FILE_PATH = "data/";
    private static final Storage storage = new Storage(FILE_PATH);

    @Test
    void cardListAddCard_addOneCard_printCardDetails() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Storage testStorage = new Storage("data/");
        testStorage.createDirectoryIfNotExist("data/");
        CardList cardListTemp = new CardList(storage);
        Card newCard = new Card("Test Card", 1000, 1.5);
        Ui uiTest = new Ui();
        try {
            cardListTemp.cardListAddCard(newCard, uiTest);
        } catch (CardException error) {
            System.out.println("Expected no throw, but error thrown");
        }
        assertEquals(1, cardListTemp.getCardListSize());
        String expectedOutput = "Added a new card with the below details: " + NEWLINE
                + "Item No.             Card Name                           Monthly Limit   Remaining Limit "
                + "     Rebate          " + NEWLINE
                + "----------------------------------------------------------------------------"
                + "-----------------------------------------------------" + NEWLINE
                + "1                    Test Card                           $1000.00        "
                + "$1000.00             1.50%           " + NEWLINE
                + "--------------------------------------------------------------------------------------"
                + "-------------------------------------------" + NEWLINE;
        assertEquals(expectedOutput,outContent.toString());
        outContent.reset();
    }

    @Test
    void cardListAddCard_addCardWithDuplicateName_throwsException() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        CardList cardListTemp = new CardList(storage);
        Card firstCard = new Card("Test Card", 1000, 1.5);
        Card cardWithDuplicateName = new Card("Test Card", 2000, 0.5);
        Ui uiTest = new Ui();
        try {
            cardListTemp.cardListAddCard(firstCard, uiTest);
        } catch (CardException error) {
            System.out.println("Expected no throw, but error thrown");
        }
        outContent.reset();
        CardException thrown = assertThrows(CardException.class, () ->
                        cardListTemp.cardListAddCard(cardWithDuplicateName, uiTest),
                "Expected cardListListCards to throw, but it didn't");
        assertEquals("There is already a credit card with the name "
                + cardWithDuplicateName.getName(), thrown.getMessage());
    }

    @Test
    void cardListEditCard_editCardThatExist_printCardDetails() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Storage testStorage = new Storage("data/");
        testStorage.createDirectoryIfNotExist("data/");
        CardList cardListTemp = new CardList(storage);
        Card newCard = new Card("Test Card", 1000, 1.5);
        Ui uiTest = new Ui();
        try {
            cardListTemp.cardListAddCard(newCard, uiTest);
            outContent.reset();
            cardListTemp.cardListEditCard("Test Card", "New Card Name",
                    "1200", "1.0", uiTest);
        } catch (CardException error) {
            System.out.println("Expected no throw, but error thrown");
        }
        assertEquals(1, cardListTemp.getCardListSize());

        String expectedOutput = "New details of the cards: " + NEWLINE
                + "Item No.             Card Name                           Monthly Limit   Remaining Limit "
                + "     Rebate          " + NEWLINE
                + "----------------------------------------------------------------------------"
                + "-----------------------------------------------------" + NEWLINE
                + "1                    New Card Name                       $1200.00        "
                + "$1200.00             1.00%           " + NEWLINE
                + "--------------------------------------------------------------------------------------"
                + "-------------------------------------------" + NEWLINE;
        assertEquals(expectedOutput,outContent.toString());
        outContent.reset();
    }

    @Test
    void cardListEditCard_editCardThatDoNotExist_throwsException() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        CardList cardListTemp = new CardList(storage);
        Card newCard = new Card("Test Card", 1000, 1.5);
        Ui uiTest = new Ui();
        try {
            cardListTemp.cardListAddCard(newCard, uiTest);
        } catch (CardException error) {
            System.out.println("Expected no throw, but error thrown");
        }
        assertEquals(1, cardListTemp.getCardListSize());
        outContent.reset();

        CardException thrown = assertThrows(CardException.class, () ->
                cardListTemp.cardListEditCard("No Such Name", "New Card Name",
                        "1200", "1.0", uiTest),
                "Expected cardListEditCard to throw, but it didn't");
        assertEquals("Card could not be found for editing card details.", thrown.getMessage());
    }

    @Test
    void cardListEditCard_newCardNameClashWithOtherCardName_throwsException() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        CardList cardListTemp = new CardList(storage);
        Card cardToEdit = new Card("Test Card", 1000, 1.5);
        String newCardName = "New Card Name";
        Card cardToClash = new Card(newCardName, 1050, 2.5);
        Ui uiTest = new Ui();

        try {
            cardListTemp.cardListAddCard(cardToEdit, uiTest);
            cardListTemp.cardListAddCard(cardToClash, uiTest);
        } catch (CardException error) {
            System.out.println("Expected no throw, but error thrown");
        }
        assertEquals(2, cardListTemp.getCardListSize());
        outContent.reset();

        CardException thrown = assertThrows(CardException.class, () ->
                        cardListTemp.cardListEditCard("Test Card", newCardName,
                                "1200", "1.0", uiTest),
                "Expected cardListEditCard to throw, but it didn't");
        assertEquals("There is already a credit card with the name: " + newCardName,
                thrown.getMessage());
    }

    @Test
    void cardListEditCard_editCardLimitWithUnpaidExpenditure_throwsException() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Storage testStorage = new Storage("data/");
        testStorage.createDirectoryIfNotExist("data/");
        CardList cardListTemp = new CardList(storage);
        Ui uiTest = new Ui();
        Date newDate = new Date();
        Card newCard = new Card("Test Card", 1000, 1.5);
        try {
            newDate = temp.parse("10/2/2019");
        } catch (ParseException error) {
            System.out.println("Expected no throw, but error thrown");
        }

        Transaction expenditureTest = new Expenditure("test", 10,
                newDate, "Miscellaneous");
        try {
            newCard.addInExpenditure(expenditureTest, uiTest, "card");
            cardListTemp.cardListAddCard(newCard, uiTest);
        } catch (CardException error) {
            System.out.println("Expected no throw, but error thrown");
        }
        assertEquals(1, cardListTemp.getCardListSize());
        outContent.reset();

        CardException thrown = assertThrows(CardException.class, () ->
                        cardListTemp.cardListEditCard("Test Card", "New Card Name",
                                "1200", "1.0", uiTest),
                "Expected cardListEditCard to throw, but it didn't");
        assertEquals("Card limit cannot be edited if there are unpaid expenditures",
                thrown.getMessage());
    }

    @Test
    void cardListEditCard_editCardLimitOnlyWithoutUnpaidExpenditure_printDetails() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        CardList cardListTemp = new CardList(storage);
        Ui uiTest = new Ui();
        Card newCard = new Card("Test Card", 1000, 1.5);

        try {
            cardListTemp.cardListAddCard(newCard, uiTest);
            outContent.reset();
            cardListTemp.cardListEditCard("Test Card", "", "1200", "", uiTest);
        } catch (CardException error) {
            System.out.println("Expected no throw, but error thrown");
        }
        assertEquals(1, cardListTemp.getCardListSize());

        String expectedOutput = "New details of the cards: " + NEWLINE
                + "Item No.             Card Name                           Monthly Limit   "
                + "Remaining Limit      Rebate          "  + NEWLINE
                + "----------------------------------------------------------------------------------"
                + "-----------------------------------------------" + NEWLINE
                + "1                    Test Card                           $1200.00        "
                + "$1200.00             1.50%           " + NEWLINE
                + "-----------------------------------------------------------------------------"
                + "----------------------------------------------------" + NEWLINE;
        assertEquals(expectedOutput,outContent.toString());
        outContent.reset();
    }

    @Test
    void cardListEditCard_editCardNameOnlyWithoutClash_printDetails() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Storage testStorage = new Storage("data/");
        testStorage.createDirectoryIfNotExist("data/");
        CardList cardListTemp = new CardList(storage);
        Ui uiTest = new Ui();
        Card newCard = new Card("Test Card", 1000, 1.5);

        try {
            cardListTemp.cardListAddCard(newCard, uiTest);
            outContent.reset();
            cardListTemp.cardListEditCard("Test Card", "New Name", "", "",
                    uiTest);
        } catch (CardException error) {
            System.out.println("Expected no throw, but error thrown");
        }
        assertEquals(1, cardListTemp.getCardListSize());

        String expectedOutput = "New details of the cards: " + NEWLINE
                + "Item No.             Card Name                           Monthly Limit   "
                + "Remaining Limit      Rebate          "  + NEWLINE
                + "----------------------------------------------------------------------------------"
                + "-----------------------------------------------" + NEWLINE
                + "1                    New Name                            $1000.00        "
                + "$1000.00             1.50%           " + NEWLINE
                + "-----------------------------------------------------------------------------"
                + "----------------------------------------------------" + NEWLINE;
        assertEquals(expectedOutput,outContent.toString());
        outContent.reset();
    }

    @Test
    void cardListEditCard_editCardRebateOnly_printDetails() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Storage testStorage = new Storage("data/");
        testStorage.createDirectoryIfNotExist("data/");
        CardList cardListTemp = new CardList(storage);
        Ui uiTest = new Ui();
        Card newCard = new Card("Test Card", 1000, 1.5);

        try {
            cardListTemp.cardListAddCard(newCard, uiTest);
            outContent.reset();
            cardListTemp.cardListEditCard("Test Card", "", "", "0.1", uiTest);
        } catch (CardException error) {
            System.out.println("Expected no throw, but error thrown");
        }
        assertEquals(1, cardListTemp.getCardListSize());

        String expectedOutput = "New details of the cards: " + NEWLINE
                + "Item No.             Card Name                           Monthly Limit   "
                + "Remaining Limit      Rebate          "  + NEWLINE
                + "----------------------------------------------------------------------------------"
                + "-----------------------------------------------" + NEWLINE
                + "1                    Test Card                           $1000.00        "
                + "$1000.00             0.10%           " + NEWLINE
                + "-----------------------------------------------------------------------------"
                + "----------------------------------------------------" + NEWLINE;
        assertEquals(expectedOutput,outContent.toString());
        outContent.reset();
    }

    @Test
    void cardListDeleteCard_deleteCardThatExist_printDeleteCardDetails() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Storage testStorage = new Storage("data/");
        testStorage.createDirectoryIfNotExist("data/");
        CardList cardListTemp = new CardList(storage);
        Card cardToBeDeleted = new Card("Test Card", 1000, 1.5);
        Ui uiTest = new Ui();

        try {
            cardListTemp.cardListAddCard(cardToBeDeleted, uiTest);
            outContent.reset();
            assertEquals(1, cardListTemp.getCardListSize());
            cardListTemp.cardListDeleteCard("Test Card", uiTest);

        } catch (CardException error) {
            System.out.println("Expected no throw, but error thrown");
        }


        String expectedOutput = "Card with the following details has been removed:" + NEWLINE
                + "Item No.             Card Name                           Monthly Limit   "
                + "Remaining Limit      Rebate          "  + NEWLINE
                + "----------------------------------------------------------------------------------"
                + "-----------------------------------------------" + NEWLINE
                + "1                    Test Card                           $1000.00        "
                + "$1000.00             1.50%           " + NEWLINE
                + "-----------------------------------------------------------------------------"
                + "----------------------------------------------------" + NEWLINE;
        assertEquals(expectedOutput,outContent.toString());
        outContent.reset();
        assertEquals(0, cardListTemp.getCardListSize());

    }

    @Test
    void cardListDeleteCard_deleteCardWithEmptyCardList_throwsException() {
        CardList cardListTemp = new CardList(storage);
        Ui uiTest = new Ui();
        assertEquals(0, cardListTemp.getCardListSize());

        CardException thrown = assertThrows(CardException.class, () ->
                        cardListTemp.cardListDeleteCard("Test Card", uiTest),
                "Expected cardListListCards to throw, but it didn't");
        assertEquals("There are 0 cards in your profile.", thrown.getMessage());
    }

    @Test
    void cardListDeleteCard_deleteCardWithNonExistentName_throwsException() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        CardList cardListTemp = new CardList(storage);
        Card newCard = new Card("Test Card", 1000, 1.5);
        Ui uiTest = new Ui();

        try {
            cardListTemp.cardListAddCard(newCard, uiTest);
        } catch (CardException error) {
            System.out.println("Expected no throw, but error thrown");
        }
        outContent.reset();
        assertEquals(1, cardListTemp.getCardListSize());

        CardException thrown = assertThrows(CardException.class, () ->
                        cardListTemp.cardListDeleteCard("No Such Name", uiTest),
                "Expected cardListListCards to throw, but it didn't");
        assertEquals("No such card exist for deletion.", thrown.getMessage());
        assertEquals(1, cardListTemp.getCardListSize());

    }

    @Test
    void cardListListCards_cardListNotEmpty_listCardDetails() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        CardList cardListTemp = new CardList(storage);
        Card newCard = new Card("Test Card", 1000, 1.5);
        Ui uiTest = new Ui();

        try {
            cardListTemp.cardListAddCard(newCard, uiTest);
            outContent.reset();
            cardListTemp.cardListListCards(uiTest);
        } catch (CardException error) {
            System.out.println("Expected no throw, but error thrown");
        }

        assertEquals(1, cardListTemp.getCardListSize());

        String expectedOutput = "Item No.             Card Name                           "
                + "Monthly Limit   Remaining Limit " + "     Rebate          " + NEWLINE
                + "----------------------------------------------------------------------------"
                + "-----------------------------------------------------" + NEWLINE
                + "1                    Test Card                           $1000.00        "
                + "$1000.00             1.50%           " + NEWLINE
                + "--------------------------------------------------------------------------------------"
                + "-------------------------------------------" + NEWLINE;
        assertEquals(expectedOutput,outContent.toString());
        outContent.reset();
    }

    @Test
    void cardListListCards_cardListEmpty_throwsException() {
        CardList cardListTemp = new CardList(storage);
        Ui uiTest = new Ui();
        CardException thrown = assertThrows(CardException.class, () ->
                        cardListTemp.cardListListCards(uiTest),
                "Expected cardListListCards to throw, but it didn't");
        assertEquals("There are 0 cards in your profile.", thrown.getMessage());
    }
}
