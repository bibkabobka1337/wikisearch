package wikisearch;

public record SearchResult(long pageId, String title, String snippet) {

    private static final String ARTICLE_URL = "https://ru.wikipedia.org/w/index.php?curid=";

    public String url() {
        return ARTICLE_URL + pageId;
    }
}
