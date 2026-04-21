package flashcard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import flashcard.achievement.AchievementTracker;
import flashcard.model.FlashCard;
import flashcard.organizer.CardOrganizer;

public class StudySession {

    private final List<FlashCard> allCards;
    private final CardOrganizer organizer;
    private final int requiredRepetitions;
    private final boolean invertCards;
    private final AchievementTracker achievementTracker;
    private final Scanner scanner;

    public StudySession(
            List<FlashCard> allCards,
            CardOrganizer organizer,
            int requiredRepetitions,
            boolean invertCards) {
        this.allCards = new ArrayList<>(allCards);
        this.organizer = organizer;
        this.requiredRepetitions = requiredRepetitions;
        this.invertCards = invertCards;
        this.achievementTracker = new AchievementTracker();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        Map<FlashCard, Integer> correctCounts = new HashMap<>();
        for (FlashCard card : allCards) {
            correctCounts.put(card, 0);
        }

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println(" Welcome to Flashcard CLI!");
        
        System.out.println("Cards: " + allCards.size() + " | Required correct answers: " + requiredRepetitions);
        System.out.println("Type your answer and press Enter. Type 'quit' to exit.\n");

        for (FlashCard card : allCards) {
            card.resetRoundFlag();
        }

        boolean sessionActive = true;
        while (sessionActive) {
            List<FlashCard> remaining = getRemainingCards(correctCounts);
            if (remaining.isEmpty()) {
                System.out.println("\n✅ Congratulations! You've mastered all cards!");
                break;
            }

            List<FlashCard> roundCards = organizer.organize(remaining);
            long roundStartTime = System.currentTimeMillis();
            int roundCorrect = 0;

            for (FlashCard card : allCards) {
                card.resetRoundFlag();
            }

            int cardIndex = 0;
            for (FlashCard card : roundCards) {
                System.out.println("Card #" + cardIndex + " --------------------");
                String displayQuestion = invertCards ? card.getAnswer() : card.getQuestion();
                String displayAnswer = invertCards ? card.getQuestion() : card.getAnswer();

                System.out.print("? " + displayQuestion + " ->  ");

                String userInput = scanner.nextLine().trim();
                if (userInput.equalsIgnoreCase("quit")) {
                    sessionActive = false;
                    break;
                }

                if (userInput.equalsIgnoreCase(displayAnswer)) {
                    System.out.println("** Correct! **\n");
                    card.recordCorrect();
                    correctCounts.put(card, correctCounts.get(card) + 1);
                    roundCorrect++;
                } else {
                    System.out.println("__ Wrong! The answer was " + displayAnswer + " __\n");
                    card.recordIncorrect();
                }
                cardIndex++;
            }

            if (!sessionActive) {
                break;
            }

            long roundTimeMs = System.currentTimeMillis() - roundStartTime;
            double roundTimeSec = roundTimeMs / 1000.0;

            System.out.println("--- Round complete ---");
            System.out.printf("Correct: %d/%d | Time: %.1fs%n",
                roundCorrect, roundCards.size(), roundTimeSec);

            // Check achievements
            achievementTracker.checkSpeedAchievement(roundTimeSec, roundCards.size());
            achievementTracker.checkCorrectAchievement(allCards, allCards.size());
            achievementTracker.checkRepeatAchievement(allCards);
            achievementTracker.checkConfidentAchievement(allCards);

            System.out.println();
        }

        printSummary();
    }

    private List<FlashCard> getRemainingCards(Map<FlashCard, Integer> correctCounts) {
        List<FlashCard> remaining = new ArrayList<>();
        for (FlashCard card : allCards) {
            if (correctCounts.get(card) < requiredRepetitions) {
                remaining.add(card);
            }
        }
        return remaining;
    }

    private void printSummary() {
        System.out.println("\n=== Session Summary ===");
        for (FlashCard card : allCards) {
            System.out.printf("  [%s] Correct: %d | Wrong: %d%n",
                card.getQuestion().substring(0, Math.min(card.getQuestion().length(), 30)),
                card.getCorrectCount(),
                card.getIncorrectCount());
        }
        List<String> earned = achievementTracker.getEarnedAchievements();
        if (!earned.isEmpty()) {
            System.out.println("Achievements: " + String.join(", ", earned));
        }
    }
}
