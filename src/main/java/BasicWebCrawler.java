import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
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
//        BasicWebCrawler crawler = new BasicWebCrawler();
//        crawler.crawle();
//        Tokenizer tokenizer = new Tokenizer();
//        tokenizer.makeTokens();

        String s = null;

        try {
            Process p = Runtime.getRuntime().exec("python C:\\AinaArd\\Crawler\\src\\main\\resources\\lemmatizer.py");

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));

            // read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
            System.exit(0);
        } catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
        }

    }
}