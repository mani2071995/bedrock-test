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
    
    // Alternative implementation using Files.readAllBytes
    public static String readFileAlternative(String filename) throws IOException {
       
        byte[] bytes = Files.readAllBytes(Paths.get(filename));
        return new String(bytes, StandardCharsets.UTF_8);
        
    }

}
