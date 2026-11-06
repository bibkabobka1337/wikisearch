package wikisearch;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Связывает компоненты программы и управляет основным циклом.
 */
public class App {

    private final ConsoleInput input = new ConsoleInput(new Scanner(System.in), System.out);
    private final ResultPrinter printer = new ResultPrinter(System.out);
    private final WikipediaClient client = new WikipediaClient();
    private final SearchResponseParser parser = new SearchResponseParser();

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
            SearchResult selected = results.get(choice.get() - 1);
            System.out.println("Выбрана статья: " + selected.title() + " -> " + selected.url()); // временно: этап 5
        }
        System.out.println();
        System.out.println("До свидания!");
    }
}
