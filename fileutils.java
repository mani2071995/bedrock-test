java
// File: FileUtils.java
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
        
        try (FileReader reader = new FileReader(file)) {
            
            char[] buffer = new char[1024];
            int read;
            
            while((read = reader.read(buffer)) != -1) {
                content.append(buffer, 0, read);
            }
            
        } catch(IOException e) {
            throw new IOException("Error reading file", e);
        }
        
        return content.toString();
    }

}
