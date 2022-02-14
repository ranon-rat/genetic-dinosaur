import java.awt.*;
import java.util.ArrayList;

public class Population {
    ArrayList<Subject> subjects = new ArrayList<>();

    int epoch = 0;
    int lastBestScore = 0;

    Population(int howMany) {
        for (int i = 0; i < howMany; i++) {

            subjects.add(new Subject(i + ""));
        }

    }

    void doSomething(Obstacle obs, Graphics2D g, Game screen) {
        int howManyDie = 0;
        for (Subject subject : subjects) {

            subject.doSomething(obs);
            System.out.print(subject.dino.score + " , " + subject.death + " ; ");
            if (subject.death) {
                howManyDie++;
            }
        }
        System.out.println();
        Subject bestOne = others.getBigger(subjects);//this is for get the better one

        g.drawString("" + epoch, screen.width - 30, 20);
        g.drawString("" + lastBestScore, screen.width / 2, 20);
        g.drawString("" + bestOne.dino.score, 0, 20);
        g.drawString(bestOne.name, screen.width / 2, screen.height / 2);
        bestOne.show(g, screen);

        if (howManyDie == subjects.size()) {
            obs.x = -obs.width;
            lastBestScore = bestOne.dino.score;
            epoch++;

            for (Subject subject : subjects) {
                subject.brain.copyOtherBrain(bestOne.brain);
                subject.brain.mutate();
                subject.death = false;
                subject.dino.score = 0;
                subject.brain.clearNodes();
            }

        }
    }


}


