java
// File: FileUtils.java
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {

    public static String readFile(String filename) throws IOException {
        
        // Use try-with-resources to automatically close resources
        try (FileReader reader = new FileReader(filename)) {
            
            StringBuilder content = new StringBuilder();
            char[] buffer = new char[1024];
            int length;
            
            // Check for EOF
            while ((length = reader.read(buffer)) > 0) {
                content.append(buffer, 0, length);
            }
            
            return content.toString();
            
        } catch (IOException e) {
            // Handle exception
            throw e; 
        }
    }
    
    // Additional helper method using Java 7 NIO
    public static String readFileNio(String filepath) throws IOException {
    
        return new String(Files.readAllBytes(Paths.get(filepath)), StandardCharsets.UTF_8);
        
    }
}
