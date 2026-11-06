package wikisearch;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class App {
    private final PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
    private final ConsoleInput input = new ConsoleInput(new Scanner(System.in, StandardCharsets.UTF_8), out);
    private final ResultPrinter printer = new ResultPrinter(out);
    private final WikipediaClient client = new WikipediaClient();
    private final SearchResponseParser parser = new SearchResponseParser();
    private final ArticleOpener opener = new ArticleOpener(out);

    public void run() {
        while (true) {
            Optional<String> query = input.readQuery();
            if (query.isEmpty()) {
                break;
            }

            List<SearchResult> results;
            try {
                results = parser.parse(client.search(query.get()));
            } catch (WikipediaApiException e) {
                printer.printError(e.getMessage());
                continue;
            }

            if (results.isEmpty()) {
                printer.printNothingFound(query.get());
                continue;
            }

            printer.printResults(results);
            Optional<Integer> choice = input.readChoice(results.size());
            if (choice.isEmpty()) {
                break;
            }
            if (choice.get() == 0) {
                continue;
            }
            opener.open(results.get(choice.get() - 1));
        }
        out.println();
        out.println("До свидания!");
    }
}
