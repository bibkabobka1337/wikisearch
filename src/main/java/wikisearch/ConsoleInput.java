package wikisearch;

import java.io.PrintStream;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleInput {
    public static final String EXIT_COMMAND = "/exit";

    private final Scanner scanner;
    private final PrintStream out;

    public ConsoleInput(Scanner scanner, PrintStream out) {
        this.scanner = scanner;
        this.out = out;
    }

    public Optional<String> readQuery() {
        while (true) {
            out.print("Введите поисковый запрос (" + EXIT_COMMAND + " - выход): ");
            if (!scanner.hasNextLine()) {
                return Optional.empty();
            }
            String line = scanner.nextLine().trim();
            if (line.equalsIgnoreCase(EXIT_COMMAND)) {
                return Optional.empty();
            }
            if (!line.isEmpty()) {
                return Optional.of(line);
            }
            out.println("Запрос не может быть пустым.");
        }
    }

    public Optional<Integer> readChoice(int max) {
        while (true) {
            out.print("Введите номер статьи (1-" + max + "), 0 - новый поиск, " + EXIT_COMMAND + " - выход: ");
            if (!scanner.hasNextLine()) {
                return Optional.empty();
            }
            String line = scanner.nextLine().trim();
            if (line.equalsIgnoreCase(EXIT_COMMAND)) {
                return Optional.empty();
            }
            try {
                int number = Integer.parseInt(line);
                if (number >= 0 && number <= max) {
                    return Optional.of(number);
                }
            } catch (NumberFormatException ignored) {
            }
            out.println("Некорректный выбор: введите целое число от 0 до " + max + ".");
        }
    }
}
