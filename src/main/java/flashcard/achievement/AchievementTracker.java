package flashcard.achievement;

import flashcard.model.FlashCard;
import java.util.ArrayList;
import java.util.List;

/**
 * Tracks and evaluates achievements during a flashcard study session.
 */
public class AchievementTracker {

    private static final double FAST_ANSWER_THRESHOLD_SECONDS = 5.0;
    private static final int REPEAT_THRESHOLD = 5;
    private static final int CONFIDENT_THRESHOLD = 3;

    private final List<String> earnedAchievements = new ArrayList<>();

    /**
     * Checks for the SPEED achievement: average answer time under 5 seconds in a round.
     *
     * @param totalTimeSeconds total time taken in the round
     * @param cardCount        number of cards in the round
     */
    public void checkSpeedAchievement(double totalTimeSeconds, int cardCount) {
        if (cardCount > 0 && (totalTimeSeconds / cardCount) < FAST_ANSWER_THRESHOLD_SECONDS) {
            awardIfNew("SPEED");
        }
    }

    /**
     * Checks for the CORRECT achievement: all cards answered correctly in the last round.
     *
     * @param cards     all cards in the session
     * @param roundSize number of cards in the round
     */
    public void checkCorrectAchievement(List<FlashCard> cards, int roundSize) {
        boolean allCorrect = cards.stream()
            .limit(roundSize)
            .noneMatch(FlashCard::isWrongInLastRound);
        if (allCorrect) {
            awardIfNew("CORRECT");
        }
    }

    /**
     * Checks for the REPEAT achievement: any card answered more than 5 times total.
     *
     * @param cards list of flashcards
     */
    public void checkRepeatAchievement(List<FlashCard> cards) {
        for (FlashCard card : cards) {
            if (card.getTotalAnswered() > REPEAT_THRESHOLD) {
                awardIfNew("REPEAT");
                return;
            }
        }
    }

    /**
     * Checks for the CONFIDENT achievement: any card answered correctly at least 3 times.
     *
     * @param cards list of flashcards
     */
    public void checkConfidentAchievement(List<FlashCard> cards) {
        for (FlashCard card : cards) {
            if (card.getCorrectCount() >= CONFIDENT_THRESHOLD) {
                awardIfNew("CONFIDENT");
                return;
            }
        }
    }

    private void awardIfNew(String achievement) {
        if (!earnedAchievements.contains(achievement)) {
            earnedAchievements.add(achievement);
            System.out.println("🏆 Achievement unlocked: " + achievement + "!");
        }
    }

    public List<String> getEarnedAchievements() {
        return new ArrayList<>(earnedAchievements);
    }
}
