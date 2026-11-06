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

    /**
     * Запрашивает номер статьи, пока пользователь не введёт корректное значение.
     *
     * @param max количество статей в списке
     * @return номер статьи от 1 до {@code max}; {@code 0} - новый поиск;
     *         {@link Optional#empty()}, если ввод закончился
     */
    public Optional<Integer> readChoice(int max) {
        while (true) {
            out.print("Введите номер статьи (1-" + max + ") или 0 для нового поиска: ");
            if (!scanner.hasNextLine()) {
                return Optional.empty();
            }
            String line = scanner.nextLine().trim();
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
