java
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class FileUtils {

    public static String readFile(String filename) throws IOException {
        
        File file = new File(filename);
        
        if(!file.exists()) {
            throw new IllegalArgumentException("File does not exist: " + filename);
        }
        
        byte[] encoded = Files.readAllBytes(Paths.get(filename));
        return new String(encoded, StandardCharsets.UTF_8);
        
    }

}
