package org.example;

import opennlp.tools.tokenize.SimpleTokenizer;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Component
public class Tokenizer {
    private static final File tokensFile = new File("src/main/resources/tokens.txt");
    private static final File folderPath = new File("src/main/resources/crawler4j");
    private static final File transitionFile = new File("src/main/resources/transition.txt");

    private List<String> tokenizeLine(String line, int i) {
        List<String> result = new ArrayList<>();
        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        String[] words = line.split(" ");
        for (String word : words) {
            if (!word.equals("")) {
                tokenizer.tokenize(word);
                result.add(word + " " + i);
            }
        }
        return result;
    }

    private void readFile(File file, int i) {
        try {
            String fileInLine = FileUtils.readFileToString(file, Charset.defaultCharset());
            String formattedString = fileInLine.replaceAll("[^а-яА-я]", " ");
            List<String> tokens = tokenizeLine(formattedString, i);
            writeToFile(tokens);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToFile(List<String> tokens) {
        try {
            for (String token : tokens) {
                if (token.length() >= 3) {
                    FileUtils.writeStringToFile(tokensFile, token + "\n", true);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getAllFiles() {
        File[] list = folderPath.listFiles();
        for (int i = 1; i <= 101; i++) {
            readFile(list[i], i);
        }
    }

    public void makeTokens() {
        getAllFiles();
    }

    public String lemmatizeWord(String word) {
        String result = "";
        try {
            FileUtils.writeStringToFile(transitionFile, word, Charset.defaultCharset());

            try {
                Process p = Runtime.getRuntime().exec("python C:\\AinaArd\\Crawler\\src\\main\\resources\\lemmatizeOneWord.py");

                BufferedReader stdError = new BufferedReader(new
                        InputStreamReader(p.getErrorStream()));

                while ((word = stdError.readLine()) != null) {
                    System.out.println(word);
                }
            } catch (IOException e) {
                System.out.println("exception happened - here's what I know: ");
                e.printStackTrace();
            }

            result = String.valueOf(FileUtils.readLines(transitionFile, Charset.defaultCharset()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
