import opennlp.tools.tokenize.SimpleTokenizer;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class Tokenizer {
    private static final File tokensFile = new File("src/main/resources/tokens.txt");
    private static final File folderPath = new File("src/main/resources/crawler4j");

    private String[] tokenizeLine(String line) {
        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
        return tokenizer.tokenize(line);
    }

    private void readFile(File file) {
        try {
            String fileInLine = FileUtils.readFileToString(file, Charset.defaultCharset());
            String formattedString = fileInLine.replaceAll("[^а-яА-я]", " ");
            String[] tokens = tokenizeLine(formattedString);
            writeToFile(tokens);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToFile(String[] tokens) {
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
        for (File file : list) {
            readFile(file);
        }
    }

    public void makeTokens() {
        getAllFiles();
    }
}
