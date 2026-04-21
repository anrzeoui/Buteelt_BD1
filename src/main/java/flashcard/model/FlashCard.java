package flashcard.model;
public class FlashCard {

    private final String question;
    private final String answer;
    private int correctCount;
    private int incorrectCount;
    private boolean wrongInLastRound;

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

    public void recordCorrect() {
        correctCount++;
        wrongInLastRound = false;
    }

    public void recordIncorrect() {
        incorrectCount++;
        wrongInLastRound = true;
    }

    public void resetRoundFlag() {
        wrongInLastRound = false;
    }

    public int getTotalAnswered() {
        return correctCount + incorrectCount;
    }
}
