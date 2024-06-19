java
// File: FileUtils.java
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {

    public static String readFile(String filename) throws IOException {
        
        // Validate input
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be null or empty");
        }
        
        File file = new File(filename);
        
        // Check if file exists and is readable
        if (!file.exists() || !file.canRead()) { 
            throw new IOException("File does not exist or cannot be read: " + filename);
        }

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
