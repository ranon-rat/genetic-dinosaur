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
            //    System.out.print(subject.name+" , "+subject.death + " , " +  subject.dino.score+" ; ");

            if (subject.death) {
                howManyDie++;
                subject.dino.y = 0;
                continue;
            }
            subject.dino.show(g, screen);

        }
        //  System.out.println();

        Subject bestOne = others.getBiggerSubject(subjects);//this is for get the better one

        int separationWidth = screen.width / 5;
        g.drawString("epoch: " + epoch, 0, 20);
        g.drawString("last: " + lastBestScore, separationWidth, 20);
        g.drawString("score: " + bestOne.dino.score, separationWidth * 2, 20);
        g.drawString("best one: " + bestOne.name, separationWidth * 3, 20);
        g.drawString("die: " + howManyDie + " ", separationWidth * 4, 20);
        bestOne.show(g, screen);

        if (howManyDie == subjects.size()) {
            obs.x = -obs.width - 30;
            lastBestScore = bestOne.dino.score;
            epoch++;

            for (Subject subject : subjects) {
                subject.death = false;
                subject.dino.score = 0;

                if (subject == bestOne) continue;
                subject.brain.copyOtherBrain(bestOne.brain);
                subject.brain.mutate();
            }
            bestOne.brain.mutate();

        }
    }


}


