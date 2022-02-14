import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class Layers {
    int length, loop, start;

    Layers(int loop, int start, int length) {
        this.length = length;
        this.start = start;
        this.loop = loop;

    }
}

public class others {
    public static ArrayList<Subject> bubbleSort(ArrayList<Subject> subjects) {

        for (int i = subjects.size(); i > 0; i--) {
            boolean noSwap = true;
            for (int j = 0; j < i - 1; j++) {
                Subject sub1 = subjects.get(j);
                Subject sub2 = subjects.get(j + 1);
                if (sub2.dino.score > sub1.dino.score) {
                    noSwap = false;
                    subjects.set(j + 1, sub1);
                    subjects.set(j, sub2);
                }
            }
            if (noSwap) break;
        }
        return subjects;

    }

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
