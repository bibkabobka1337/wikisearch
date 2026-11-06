package wikisearch;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class WikipediaClient {

    private static final String API_URL = "https://ru.wikipedia.org/w/api.php";
    private static final int RESULTS_LIMIT = 10;
    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(TIMEOUT)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    public String search(String query) throws WikipediaApiException {
        HttpRequest request = HttpRequest.newBuilder(buildSearchUri(query))
                .timeout(TIMEOUT)
                .header("User-Agent", "WikiSearchConsole/1.0 (educational project)")
                .GET()
                .build();
        try {
            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (response.statusCode() != 200) {
                throw new WikipediaApiException("Сервер вернул код " + response.statusCode());
            }
            return response.body();
        } catch (IOException e) {
            throw new WikipediaApiException("Не удалось связаться с Википедией: проверьте подключение к сети", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new WikipediaApiException("Запрос был прерван", e);
        }
    }

    static URI buildSearchUri(String query) {
        String encoded = URLEncoder.encode(query, StandardCharsets.UTF_8);
        return URI.create(API_URL
                + "?action=query&list=search&utf8=&format=json"
                + "&srlimit=" + RESULTS_LIMIT
                + "&srsearch=" + encoded);
    }
}
