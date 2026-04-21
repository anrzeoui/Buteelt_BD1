package flashcard;

import java.io.IOException;
import java.util.List;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import flashcard.model.FlashCard;
import flashcard.organizer.CardOrganizer;
import flashcard.organizer.RandomSorter;
import flashcard.organizer.RecentMistakesFirstSorter;
import flashcard.organizer.WorstFirstSorter;

public class Main {

    public static void main(String[] args) {
        CliArgs cliArgs = new CliArgs();
        JCommander jc = JCommander.newBuilder()
            .addObject(cliArgs)
            .programName("flashcard")
            .build();

        try {
            jc.parse(args);
        } catch (ParameterException e) {
            System.err.println("Error: " + e.getMessage());
            printUsage(jc);
            System.exit(1);
        }

        if (cliArgs.isHelp()) {
            printHelp(jc);
            System.exit(0);
        }

        if (cliArgs.getCardsFile() == null) {
            System.err.println("Error: No cards file specified.");
            printUsage(jc);
            System.exit(1);
        }

        String order = cliArgs.getOrder();
        if (!order.equals("random") && !order.equals("worst-first") && !order.equals("recent-mistakes-first")) {
            System.err.println("Error: Invalid order '" + order + "'.");
            System.err.println("Valid options: random, worst-first, recent-mistakes-first");
            System.exit(1);
        }

        if (cliArgs.getRepetitions() < 1) {
            System.err.println("Error: --repetitions must be at least 1.");
            System.exit(1);
        }

        CardLoader loader = new CardLoader();
        List<FlashCard> cards;
        try {
            cards = loader.loadCards(cliArgs.getCardsFile());
        } catch (IOException e) {
            System.err.println("Error reading file '" + cliArgs.getCardsFile() + "': " + e.getMessage());
            System.exit(1);
            return;
        } catch (IllegalArgumentException e) {
            System.err.println("Error in cards file: " + e.getMessage());
            System.exit(1);
            return;
        }

        System.out.println("Loaded " + cards.size() + " cards from '" + cliArgs.getCardsFile() + "'");


        CardOrganizer organizer = createOrganizer(order);

        StudySession session = new StudySession(
            cards,
            organizer,
            cliArgs.getRepetitions(),
            cliArgs.isInvertCards()
        );
        session.run();
    }

    private static CardOrganizer createOrganizer(String order) {
        switch (order) {
            case "worst-first":
                return new WorstFirstSorter();
            case "recent-mistakes-first":
                return new RecentMistakesFirstSorter();
            case "random":
            default:
                return new RandomSorter();
        }
    }

    private static void printUsage(JCommander jc) {
        System.err.println("Usage: flashcard <cards-file> [options]");
        System.err.println("Use --help for more information.");
    }

    private static void printHelp(JCommander jc) {
        System.out.println("Flashcard CLI - CSA311 HW1");
        System.out.println();
        System.out.println("Usage: flashcard <cards-file> [options]");
        System.out.println();
        System.out.println("Options:");
        System.out.println("  --help                        Show this help message");
        System.out.println("  --order <order>               Card order (default: random)");
        System.out.println("                                Options: random, worst-first, recent-mistakes-first");
        System.out.println("  --repetitions <num>           Required correct answers per card (default: 1)");
        System.out.println("  --invertCards                 Swap question and answer sides (default: false)");
        System.out.println();
        System.out.println("Cards file format:");
        System.out.println("  # This is a comment");
        System.out.println("  Question 1 | Answer 1");
        System.out.println("  Question 2 | Answer 2");
        System.out.println();
        System.out.println("Example:");
        System.out.println("  flashcard cards.txt --order worst-first --repetitions 3");
    }
}
