package flashcard;

import flashcard.model.FlashCard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the CardLoader class.
 */
class CardLoaderTest {

    @TempDir
    Path tempDir;

    @Test
    void loadsBasicCards(@TempDir Path dir) throws IOException {
        Path file = dir.resolve("cards.txt");
        Files.writeString(file, "What is 2+2 | 4\nCapital of France | Paris\n");

        CardLoader loader = new CardLoader();
        List<FlashCard> cards = loader.loadCards(file.toString());

        assertEquals(2, cards.size());
        assertEquals("What is 2+2", cards.get(0).getQuestion());
        assertEquals("4", cards.get(0).getAnswer());
        assertEquals("Capital of France", cards.get(1).getQuestion());
        assertEquals("Paris", cards.get(1).getAnswer());
    }

    @Test
    void skipsCommentsAndEmptyLines(@TempDir Path dir) throws IOException {
        Path file = dir.resolve("cards.txt");
        Files.writeString(file, "# This is a comment\n\nHello | World\n");

        CardLoader loader = new CardLoader();
        List<FlashCard> cards = loader.loadCards(file.toString());

        assertEquals(1, cards.size());
        assertEquals("Hello", cards.get(0).getQuestion());
    }

    @Test
    void throwsOnMissingSeparator(@TempDir Path dir) throws IOException {
        Path file = dir.resolve("cards.txt");
        Files.writeString(file, "No separator here\n");

        CardLoader loader = new CardLoader();
        assertThrows(IllegalArgumentException.class, () -> loader.loadCards(file.toString()));
    }

    @Test
    void throwsOnEmptyFile(@TempDir Path dir) throws IOException {
        Path file = dir.resolve("empty.txt");
        Files.writeString(file, "");

        CardLoader loader = new CardLoader();
        assertThrows(IllegalArgumentException.class, () -> loader.loadCards(file.toString()));
    }
}
