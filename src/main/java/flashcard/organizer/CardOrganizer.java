package flashcard.organizer;

import java.util.List;

import flashcard.model.FlashCard;


public interface CardOrganizer {
    List<FlashCard> organize(List<FlashCard> cards);
}
