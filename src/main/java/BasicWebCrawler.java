import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

public class BasicWebCrawler {

    private static HashSet<String> links;
    private int i = 1;
    private static final File archive = new File("src/main/resources/crawler4j/archive.txt");
    private static final File index = new File("src/main/resources/crawler4j/index.txt");

    public BasicWebCrawler() {
        links = new HashSet<>();
    }

    public void getPageLinks(String URL) {
        if (!links.contains(URL)) {
            try {
                links.add(URL);
                System.out.println(URL + " " + i);
                i++;

                Document document = Jsoup.connect(URL).get();
                Elements linksOnPage = document.select("a[href]");

                savePagesToArchive(document.text());
                saveToIndex(i, URL);

                for (Element page : linksOnPage) {
                    getPageLinks(page.attr("abs:href"));
                }
            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
    }

    private void savePagesToArchive(String text) {
        try {
            FileUtils.writeStringToFile(archive, text, true);
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
        new BasicWebCrawler().getPageLinks("http://www.mkyong.com/");
        System.out.println(links.size());
    }
}