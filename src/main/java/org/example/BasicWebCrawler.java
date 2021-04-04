package org.example;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Component
public class BasicWebCrawler {

    private static final String path = "src/main/resources/crawler4j/";
    private static final String type = ".txt";
    private static final File index = new File("src/main/resources/crawler4j/index.txt");
    private static final File articles = new File("src/main/resources/articles.txt");
    private static final File tfIdf = new File("src/main/resources/result.txt");

    private static final Tokenizer tokenizer = new Tokenizer();

    public String getResult(String search) {
        return findMostLikely(tokenizer.lemmatizeWord(search));
    }

    private String findMostLikely(String tokenizedWord) {
        return findWithBiggerValue(tokenizedWord);
    }

    private String findWithBiggerValue(String tokenizedWord) {
        HashMap<Integer, String> candidates = new HashMap<>();
        String word = tokenizedWord.replace('[', ' ');
        word = word.replace(']', ' ');
        word = word.replaceAll("\\s", "");
        try {
            List<String> lines = FileUtils.readLines(tfIdf, Charset.defaultCharset());
            for (String line : lines) {
                String[] words = line.split(" ");
                if (words[0].equals(word)) {
                    candidates.put(lines.indexOf(line), line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String result = checkValue(candidates.values());

        Set<Map.Entry<Integer, String>> entrySet = candidates.entrySet();
        for (Map.Entry<Integer, String> pair : entrySet) {
            if (pair.getValue().equals(result)) {
                String lineNumber = String.valueOf(pair.getKey());
                return findDoc(lineNumber);
            }
        }

        return "";
    }

    private String findDoc(String lineNumber) {
        String pattern = "C/AinaArd/Crawler/src/main/resources/crawler4j/";
        HashMap<Integer, String> candidates = new HashMap<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(String.valueOf(tfIdf)), StandardCharsets.UTF_8);
            for (String line : lines) {
                if (line.contains(pattern)) {
                    if (lines.indexOf(line) < Integer.parseInt(lineNumber)) {
                        candidates.put(lines.indexOf(line), line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getMaxEntryInMapBasedOnValue(candidates).getValue();
    }

    public static <K, V extends Comparable<V>> Map.Entry<K, V> getMaxEntryInMapBasedOnValue(Map<K, V> map) {
        Map.Entry<K, V> entryWithMaxValue = null;

        for (Map.Entry<K, V> currentEntry : map.entrySet()) {

            if (entryWithMaxValue == null || currentEntry.getValue().compareTo(entryWithMaxValue.getValue()) > 0) {
                entryWithMaxValue = currentEntry;
            }
        }

        return entryWithMaxValue;
    }

    private String checkValue(Collection<String> candidates) {
        return Collections.max(candidates, Comparator.comparing(this::getValue));
    }

    private Double getValue(String c) {
        String[] sp = c.split(" ");
        return Double.valueOf(sp[2]);
    }

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
//        org.example.BasicWebCrawler crawler = new org.example.BasicWebCrawler();
//        crawler.crawle();
        tokenizer.makeTokens();

//        String s = null;
//
//        try {
//            Process p = Runtime.getRuntime().exec("python C:\\AinaArd\\Crawler\\src\\main\\resources\\lemmatizer.py");
//
//            BufferedReader stdError = new BufferedReader(new
//                    InputStreamReader(p.getErrorStream()));
//
//            // read any errors from the attempted command
//            System.out.println("Here is the standard error of the command (if any):\n");
//            while ((s = stdError.readLine()) != null) {
//                System.out.println(s);
//            }
//            System.exit(0);
//        } catch (IOException e) {
//            System.out.println("exception happened - here's what I know: ");
//            e.printStackTrace();
//        }

    }
}