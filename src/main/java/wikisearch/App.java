package wikisearch;

import java.util.Optional;
import java.util.Scanner;

/**
 * Связывает компоненты программы и управляет основным циклом.
 */
public class App {

    private final ConsoleInput input = new ConsoleInput(new Scanner(System.in), System.out);

    public void run() {
        while (true) {
            Optional<String> query = input.readQuery();
            if (query.isEmpty()) {
                System.out.println();
                System.out.println("До свидания!");
                return;
            }
            System.out.println("Вы ввели: " + query.get());
        }
    }
}
