//import opennlp.tools.tokenize.SimpleTokenizer;
//import org.apache.commons.io.FileUtils;
//
//import java.io.File;
//import java.io.IOException;
//
//public class Tokenizer {
//    private static final File tokensFile = new File("src/main/resources/tokens.txt");
//
//    private String[] tokenizeLine(String line) {
//        SimpleTokenizer tokenizer = SimpleTokenizer.INSTANCE;
//        return tokenizer.tokenize(line);
//    }
//
//    private void readFile(File file) {
//        try {
//            String fileInLine = FileUtils.readFileToString(file, "UTF_8");
//            String[] tokens = tokenizeLine(fileInLine);
//            writeToFile(tokens);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void writeToFile(String[] tokens) {
//        FileUtils.writeStringToFile();
//    }
//
//    public void makeTokens() {
//        File file
//        getAllFiles();
//
//    }
//}
