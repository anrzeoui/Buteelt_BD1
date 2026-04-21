package flashcard.organizer;

import flashcard.model.FlashCard;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Organizer that sorts cards by most incorrect answers first (worst performance first).
 */
public class WorstFirstSorter implements CardOrganizer {

    @Override
    public List<FlashCard> organize(List<FlashCard> cards) {
        List<FlashCard> sorted = new ArrayList<>(cards);
        sorted.sort(Comparator.comparingInt(FlashCard::getIncorrectCount).reversed());
        return sorted;
    }
}
