import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

class Layers {
    int length, loop, start;

    Layers(int loop, int start, int length) {
        this.length = length;
        this.start = start;
        this.loop = loop;

    }
}

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
