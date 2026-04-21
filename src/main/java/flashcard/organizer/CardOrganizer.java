package flashcard.organizer;

import flashcard.model.FlashCard;
import java.util.List;

/**
 * Interface for card organizers that determine the order of flashcard presentation.
 */
public interface CardOrganizer {

    /**
     * Organizes the given list of flashcards and returns them in a new order.
     *
     * @param cards the list of cards to organize
     * @return a new list of cards in the desired order
     */
    List<FlashCard> organize(List<FlashCard> cards);
}
