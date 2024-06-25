java
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {

    public static String readFile(String filename) throws IOException {
        
        File file = new File(filename);
        
        if(!file.exists()) {
            throw new IOException("File not found");
        }
        
        if(!file.canRead()) {
            throw new IOException("Cannot read file");
        }

        StringBuilder content = new StringBuilder();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            
            while((line = reader.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }
        }
        
        return content.toString();
    }
}
