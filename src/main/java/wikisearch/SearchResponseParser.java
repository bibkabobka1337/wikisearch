package wikisearch;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.util.ArrayList;
import java.util.List;

/**
 * Разбирает JSON-ответ Википедии с помощью Gson и превращает его в список {@link SearchResult}.
 */
public class SearchResponseParser {

    private final Gson gson = new Gson();

    /**
     * @throws WikipediaApiException если ответ не является корректным JSON или содержит ошибку API
     */
    public List<SearchResult> parse(String json) throws WikipediaApiException {
        ApiResponse response;
        try {
            response = gson.fromJson(json, ApiResponse.class);
        } catch (JsonParseException e) {
            throw new WikipediaApiException("Не удалось разобрать ответ сервера", e);
        }
        if (response == null) {
            throw new WikipediaApiException("Сервер вернул пустой ответ");
        }
        if (response.error != null) {
            throw new WikipediaApiException("Ошибка API Википедии: " + response.error.info);
        }
        if (response.query == null || response.query.search == null) {
            return List.of();
        }

        List<SearchResult> results = new ArrayList<>();
        for (SearchItem item : response.query.search) {
            if (item == null || item.title == null) {
                continue;
            }
            results.add(new SearchResult(item.pageid, item.title, cleanSnippet(item.snippet)));
        }
        return results;
    }

    /** Убирает HTML-теги подсветки и основные HTML-сущности. */
    static String cleanSnippet(String html) {
        if (html == null) {
            return "";
        }
        return html.replaceAll("<[^>]*>", "")
                .replace("&quot;", "\"")
                .replace("&#039;", "'")
                .replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("&nbsp;", " ")
                .replace("&amp;", "&")
                .trim();
    }

    // --- классы, описывающие структуру JSON ---

    private static class ApiResponse {
        ApiError error;
        QueryPart query;
    }

    private static class ApiError {
        String info;
    }

    private static class QueryPart {
        List<SearchItem> search;
    }

    private static class SearchItem {
        long pageid;
        String title;
        String snippet;
    }
}
