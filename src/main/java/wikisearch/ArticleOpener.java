package wikisearch;

import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;

/**
 * Открывает статью в браузере по умолчанию.
 * Если браузер недоступен (например, нет графической среды), выводит ссылку в консоль.
 */
public class ArticleOpener {

    private final PrintStream out;

    public ArticleOpener(PrintStream out) {
        this.out = out;
    }

    public void open(SearchResult article) {
        String url = article.url();
        if (!isBrowseSupported()) {
            out.println("Не удалось открыть браузер автоматически. Откройте ссылку вручную: " + url);
            return;
        }
        try {
            Desktop.getDesktop().browse(URI.create(url));
            out.println("Статья «" + article.title() + "» открыта в браузере.");
        } catch (IOException | RuntimeException e) {
            out.println("Не удалось открыть браузер (" + e.getMessage() + "). Откройте ссылку вручную: " + url);
        }
    }

    private boolean isBrowseSupported() {
        return Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE);
    }
}
