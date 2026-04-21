package flashcard.organizer;

import java.util.ArrayList;
import java.util.List;

import flashcard.model.FlashCard;

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
