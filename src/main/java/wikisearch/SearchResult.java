package wikisearch;

/**
 * Одна найденная статья.
 *
 * @param pageId  идентификатор страницы в Википедии
 * @param title   заголовок статьи
 * @param snippet краткий фрагмент текста (без HTML-разметки)
 */
public record SearchResult(long pageId, String title, String snippet) {

    private static final String ARTICLE_URL = "https://ru.wikipedia.org/w/index.php?curid=";

    /** Ссылка на статью по её pageid. */
    public String url() {
        return ARTICLE_URL + pageId;
    }
}
