Here is one way to remediate the code issues:

```java
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {

    public static String readFile(String filename) throws IOException {
        
        File file = new File(filename);
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }
            return content.toString();
        }
    }
}
```

Major changes:

- Use BufferedReader instead of FileReader directly for better performance
- Read line by line instead of arbitrary byte chunks 
- Append a line separator after each line to preserve formatting
- Use try-with-resources to automatically close the reader

Other minor cleanups:

- Removed unused buffer array
- Inline the file opening into try resource
- Use more descriptive loop variable name

Let me know if you have any other questions!