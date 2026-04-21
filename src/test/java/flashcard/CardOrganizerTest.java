package flashcard;

import flashcard.model.FlashCard;
import flashcard.organizer.RandomSorter;
import flashcard.organizer.RecentMistakesFirstSorter;
import flashcard.organizer.WorstFirstSorter;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for card organizer implementations.
 */
class CardOrganizerTest {

    @Test
    void randomSorterReturnsSameCards() {
        FlashCard a = new FlashCard("Q1", "A1");
        FlashCard b = new FlashCard("Q2", "A2");
        FlashCard c = new FlashCard("Q3", "A3");
        List<FlashCard> cards = Arrays.asList(a, b, c);

        RandomSorter sorter = new RandomSorter();
        List<FlashCard> result = sorter.organize(cards);

        assertEquals(3, result.size());
        assertTrue(result.containsAll(cards));
    }

    @Test
    void worstFirstSorterOrdersByMostIncorrect() {
        FlashCard a = new FlashCard("Q1", "A1");
        FlashCard b = new FlashCard("Q2", "A2");
        FlashCard c = new FlashCard("Q3", "A3");

        a.recordIncorrect();
        b.recordIncorrect();
        b.recordIncorrect();
        b.recordIncorrect();
        c.recordIncorrect();
        c.recordIncorrect();

        List<FlashCard> cards = Arrays.asList(a, b, c);
        WorstFirstSorter sorter = new WorstFirstSorter();
        List<FlashCard> result = sorter.organize(cards);

        assertEquals(b, result.get(0));
        assertEquals(c, result.get(1));
        assertEquals(a, result.get(2));
    }

    @Test
    void recentMistakesFirstPutsWrongCardsFirst() {
        FlashCard a = new FlashCard("Q1", "A1");
        FlashCard b = new FlashCard("Q2", "A2");
        FlashCard c = new FlashCard("Q3", "A3");

        // b was wrong in last round
        b.recordIncorrect();

        List<FlashCard> cards = Arrays.asList(a, b, c);
        RecentMistakesFirstSorter sorter = new RecentMistakesFirstSorter();
        List<FlashCard> result = sorter.organize(cards);

        assertEquals(b, result.get(0));
        // a and c maintain relative order
        assertEquals(a, result.get(1));
        assertEquals(c, result.get(2));
    }

    @Test
    void recentMistakesFirstPreservesInternalOrder() {
        FlashCard a = new FlashCard("Q1", "A1");
        FlashCard b = new FlashCard("Q2", "A2");
        FlashCard c = new FlashCard("Q3", "A3");
        FlashCard d = new FlashCard("Q4", "A4");

        // b and d were wrong
        b.recordIncorrect();
        d.recordIncorrect();

        List<FlashCard> cards = Arrays.asList(a, b, c, d);
        RecentMistakesFirstSorter sorter = new RecentMistakesFirstSorter();
        List<FlashCard> result = sorter.organize(cards);

        // Wrong cards: b, d (in original order)
        assertEquals(b, result.get(0));
        assertEquals(d, result.get(1));
        // Correct cards: a, c (in original order)
        assertEquals(a, result.get(2));
        assertEquals(c, result.get(3));
    }

    @Test
    void flashCardRecordsCorrectAndIncorrect() {
        FlashCard card = new FlashCard("Hello", "World");
        card.recordCorrect();
        card.recordCorrect();
        card.recordIncorrect();

        assertEquals(2, card.getCorrectCount());
        assertEquals(1, card.getIncorrectCount());
        assertEquals(3, card.getTotalAnswered());
        assertTrue(card.isWrongInLastRound());
    }

    @Test
    void flashCardResetRoundFlag() {
        FlashCard card = new FlashCard("Hello", "World");
        card.recordIncorrect();
        assertTrue(card.isWrongInLastRound());
        card.resetRoundFlag();
        assertFalse(card.isWrongInLastRound());
    }
}
