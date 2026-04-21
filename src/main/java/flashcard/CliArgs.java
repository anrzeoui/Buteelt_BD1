package flashcard;

import com.beust.jcommander.Parameter;

public class CliArgs {

    @Parameter(description = "<cards-file>")
    private java.util.List<String> mainArgs = new java.util.ArrayList<>();

    @Parameter(names = "--help", help = true, description = "Show help information")
    private boolean help = false;

    @Parameter(
        names = "--order",
        description = "Card order: random, worst-first, recent-mistakes-first"
    )
    private String order = "random";

    @Parameter(
        names = "--repetitions",
        description = "Number of correct answers required per card"
    )
    private int repetitions = 1;

    @Parameter(
        names = "--invertCards",
        description = "Swap question and answer sides of cards"
    )
    private boolean invertCards = false;

    public boolean isHelp() {
        return help;
    }

    public String getOrder() {
        return order;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public boolean isInvertCards() {
        return invertCards;
    }

    public java.util.List<String> getMainArgs() {
        return mainArgs;
    }

    public String getCardsFile() {
        return mainArgs.isEmpty() ? null : mainArgs.get(0);
    }
}
