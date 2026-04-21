package flashcard;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import flashcard.model.FlashCard;

public class CardLoader {

    private static final String SEPARATOR = "|";

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
