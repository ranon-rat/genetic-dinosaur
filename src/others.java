import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class others {
    public static Image getImage(String path) {
        try {
            File pathToFile = new File(path);
            return ImageIO.read(pathToFile);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;

    }
}
