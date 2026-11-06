package wikisearch;

import java.io.PrintStream;
import java.util.Optional;
import java.util.Scanner;

/**
 * Отвечает только за чтение данных, введённых пользователем с консоли.
 */
public class ConsoleInput {

    /** Команда выхода из программы. */
    public static final String EXIT_COMMAND = "/exit";

    private final Scanner scanner;
    private final PrintStream out;

    public ConsoleInput(Scanner scanner, PrintStream out) {
        this.scanner = scanner;
        this.out = out;
    }

    /**
     * Запрашивает поисковый запрос, пока пользователь не введёт непустую строку.
     *
     * @return запрос или {@link Optional#empty()}, если пользователь ввёл команду выхода или ввод закончился (Ctrl+D / Ctrl+Z)
     */
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

    /**
     * Запрашивает номер статьи, пока пользователь не введёт корректное значение.
     *
     * @param max количество статей в списке
     * @return номер статьи от 1 до {@code max}; {@code 0} - новый поиск;
     *         {@link Optional#empty()}, если выбран выход или ввод закончился
     */
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
                // сообщение об ошибке выводится ниже
            }
            out.println("Некорректный выбор: введите целое число от 0 до " + max + ".");
        }
    }
}
