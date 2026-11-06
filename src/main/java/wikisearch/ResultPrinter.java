package wikisearch;

import java.io.PrintStream;
import java.util.List;

/**
 * Отвечает только за форматирование и вывод результатов поиска в консоль.
 */
public class ResultPrinter {

    private final PrintStream out;

    public ResultPrinter(PrintStream out) {
        this.out = out;
    }

    public void printResults(List<SearchResult> results) {
        out.println();
        out.println("Результаты поиска:");
        for (int i = 0; i < results.size(); i++) {
            SearchResult result = results.get(i);
            out.printf("%2d. %s%n", i + 1, result.title());
            if (!result.snippet().isEmpty()) {
                out.println("    " + result.snippet());
            }
        }
        out.println();
    }

    public void printNothingFound(String query) {
        out.println("По запросу «" + query + "» ничего не найдено. Попробуйте изменить запрос.");
    }

    public void printError(String message) {
        out.println("Ошибка: " + message);
    }
}
