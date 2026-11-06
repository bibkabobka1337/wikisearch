package wikisearch;

import java.io.PrintStream;
import java.util.Optional;
import java.util.Scanner;

/**
 * Отвечает только за чтение данных, введённых пользователем с консоли.
 */
public class ConsoleInput {

    private final Scanner scanner;
    private final PrintStream out;

    public ConsoleInput(Scanner scanner, PrintStream out) {
        this.scanner = scanner;
        this.out = out;
    }

    /**
     * Запрашивает поисковый запрос, пока пользователь не введёт непустую строку.
     *
     * @return запрос или {@link Optional#empty()}, если ввод закончился (Ctrl+D / Ctrl+Z)
     */
    public Optional<String> readQuery() {
        while (true) {
            out.print("Введите поисковый запрос (пустая строка - повторить, Ctrl+D - выход): ");
            if (!scanner.hasNextLine()) {
                return Optional.empty();
            }
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                return Optional.of(line);
            }
            out.println("Запрос не может быть пустым.");
        }
    }
}
