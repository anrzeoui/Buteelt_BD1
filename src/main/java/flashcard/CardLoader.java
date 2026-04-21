package flashcard;

import flashcard.model.FlashCard;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for loading flashcards from a text file.
 *
 * <p>File format:
 * <pre>
 * # This is a comment
 * Question 1 | Answer 1
 * Question 2 | Answer 2
 * </pre>
 * Lines starting with '#' are ignored. Each card line must contain '|' as separator.
 */
public class CardLoader {

    private static final String SEPARATOR = "|";

    /**
     * Loads flashcards from the specified file path.
     *
     * @param filePath path to the cards file
     * @return list of loaded flashcards
     * @throws IOException if the file cannot be read
     * @throws IllegalArgumentException if the file format is invalid
     */
    public List<FlashCard> loadCards(String filePath) throws IOException {
        List<FlashCard> cards = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNum = 0;
            while ((line = reader.readLine()) != null) {
                lineNum++;
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                int sepIndex = line.indexOf(SEPARATOR);
                if (sepIndex < 0) {
                    throw new IllegalArgumentException(
                        "Invalid format at line " + lineNum + ": missing '|' separator. Got: " + line
                    );
                }
                String question = line.substring(0, sepIndex).trim();
                String answer = line.substring(sepIndex + 1).trim();
                if (question.isEmpty() || answer.isEmpty()) {
                    throw new IllegalArgumentException(
                        "Empty question or answer at line " + lineNum
                    );
                }
                cards.add(new FlashCard(question, answer));
            }
        }

        if (cards.isEmpty()) {
            throw new IllegalArgumentException("No valid cards found in file: " + filePath);
        }

        return cards;
    }
}
