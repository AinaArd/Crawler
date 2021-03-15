import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class BasicWebCrawler {

    private static final String path = "src/main/resources/crawler4j/";
    private static final String type = ".txt";
    private static final File index = new File("src/main/resources/crawler4j/index.txt");
    private static final File articles = new File("src/main/resources/articles.txt");

    public void crawle() {
        int i = 1;
        try {
            List<String> articlesList = FileUtils.readLines(articles, Charset.defaultCharset());
            for (String article : articlesList) {
                Document document = Jsoup.connect(article).get();
                saveArticleToArchive(document.text(), new File(path + i + type));
                saveToIndex(i, article);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveArticleToArchive(String text, File fileName) {
        try {
            FileUtils.writeStringToFile(fileName, text, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToIndex(int docNumber, String link) {
        String fullLine = docNumber + " " + link + "\n";
        try {
            FileUtils.writeStringToFile(index, fullLine, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BasicWebCrawler crawler = new BasicWebCrawler();
        crawler.crawle();
    }
}