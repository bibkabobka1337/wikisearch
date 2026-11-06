package wikisearch;

/**
 * Ошибка при обращении к API Википедии или разборе ответа.
 */
public class WikipediaApiException extends Exception {

    public WikipediaApiException(String message) {
        super(message);
    }

    public WikipediaApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
