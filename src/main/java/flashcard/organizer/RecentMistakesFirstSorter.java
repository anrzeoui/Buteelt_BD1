package flashcard.organizer;

import flashcard.model.FlashCard;
import java.util.ArrayList;
import java.util.List;

/**
 * Organizer that places cards answered incorrectly in the previous round at the front.
 * The relative order within each group (wrong and correct) is preserved.
 */
public class RecentMistakesFirstSorter implements CardOrganizer {

    @Override
    public List<FlashCard> organize(List<FlashCard> cards) {
        List<FlashCard> wrongCards = new ArrayList<>();
        List<FlashCard> otherCards = new ArrayList<>();

        for (FlashCard card : cards) {
            if (card.isWrongInLastRound()) {
                wrongCards.add(card);
            } else {
                otherCards.add(card);
            }
        }

        List<FlashCard> result = new ArrayList<>();
        result.addAll(wrongCards);
        result.addAll(otherCards);
        return result;
    }
}
