package flashcard.model;

/**
 * Represents a single flashcard with a question and an answer.
 */
public class FlashCard {

    private final String question;
    private final String answer;
    private int correctCount;
    private int incorrectCount;
    private boolean wrongInLastRound;

    /**
     * Constructs a FlashCard with the given question and answer.
     *
     * @param question the question side of the card
     * @param answer   the answer side of the card
     */
    public FlashCard(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.correctCount = 0;
        this.incorrectCount = 0;
        this.wrongInLastRound = false;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public int getIncorrectCount() {
        return incorrectCount;
    }

    public boolean isWrongInLastRound() {
        return wrongInLastRound;
    }

    /**
     * Records a correct answer for this card.
     */
    public void recordCorrect() {
        correctCount++;
        wrongInLastRound = false;
    }

    /**
     * Records an incorrect answer for this card.
     */
    public void recordIncorrect() {
        incorrectCount++;
        wrongInLastRound = true;
    }

    /**
     * Resets the wrong-in-last-round flag (used between rounds).
     */
    public void resetRoundFlag() {
        wrongInLastRound = false;
    }

    /**
     * Returns the total number of times this card has been answered.
     *
     * @return total answer count
     */
    public int getTotalAnswered() {
        return correctCount + incorrectCount;
    }
}
