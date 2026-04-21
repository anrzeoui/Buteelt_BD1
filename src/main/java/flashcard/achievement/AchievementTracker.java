package flashcard.achievement;

import java.util.ArrayList;
import java.util.List;

import flashcard.model.FlashCard;

public class AchievementTracker {

    private static final double FAST_ANSWER_THRESHOLD_SECONDS = 5.0;
    private static final int REPEAT_THRESHOLD = 5;
    private static final int CONFIDENT_THRESHOLD = 3;

    private final List<String> earnedAchievements = new ArrayList<>();

    public void checkSpeedAchievement(double totalTimeSeconds, int cardCount) {
        if (cardCount > 0 && (totalTimeSeconds / cardCount) < FAST_ANSWER_THRESHOLD_SECONDS) {
            awardIfNew("SPEED");
        }
    }


    public void checkCorrectAchievement(List<FlashCard> cards, int roundSize) {
        boolean allCorrect = cards.stream()
            .limit(roundSize)
            .noneMatch(FlashCard::isWrongInLastRound);
        if (allCorrect) {
            awardIfNew("CORRECT");
        }
    }

    public void checkRepeatAchievement(List<FlashCard> cards) {
        for (FlashCard card : cards) {
            if (card.getTotalAnswered() > REPEAT_THRESHOLD) {
                awardIfNew("REPEAT");
                return;
            }
        }
    }


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
