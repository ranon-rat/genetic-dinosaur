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
    public static int getBiggerIndex(ArrayList<Float> output) {
        int index = 0;
        for (int i = output.size() - 1; i > 0; i--) {
            if (output.get(index) < output.get(i)) {
                index = i;
            }
        }
        return index;
    }

    public static Subject getBiggerSubject(ArrayList<Subject> subjects) {
        ArrayList<Subject> temp = (ArrayList<Subject>) subjects.clone();

        for (int i = temp.size() - 1; i > 0; i--) {

                if (temp.get(i - 1).dino.score < temp.get(i).dino.score ) {
                    temp.remove(i - 1);
                }
             else {
                temp.remove(i);
            }
        }
        return temp.get(0);

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
