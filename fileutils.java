// File: FileUtils.java
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {
    public static String readFile(String filename) throws IOException {
        File file = new File(filename);
        StringBuilder content = new StringBuilder();
        try (FileReader reader = new FileReader(file)) {
            char[] buffer = new char[1024];
            int read;
            while ((read = reader.read(buffer)) != -1) {
                content.append(buffer, 0, read);
            }
        }
        return content.toString();
    }
}
