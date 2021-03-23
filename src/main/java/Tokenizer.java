import opennlp.tools.tokenize.SimpleTokenizer;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private static final File tokensFile = new File("src/main/resources/tokens.txt");
    private static final File folderPath = new File("src/main/resources/crawler4j");

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
}
