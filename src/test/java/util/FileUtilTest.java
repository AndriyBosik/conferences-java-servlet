package util;

import com.conferences.util.FileUtil;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileUtilTest {

    @Test
    public void shouldReturnFileExtension() {
        String extension = FileUtil.getFileExtension("app.properties");
        assertEquals("properties", extension);
    }

    @Test
    public void shouldReturnCorrectFileExtensionWithFilenameContainsOnlyExtension() {
        String extension = FileUtil.getFileExtension(".gitignore");
        assertEquals("gitignore", extension);
    }

    @Test
    public void shouldRemoveForbiddenFileSymbols() {
        String filename = "!?fil^&*ena@##$m^e";
        assertEquals("filename", FileUtil.removeFileForbiddenSymbols(filename));
    }

}
