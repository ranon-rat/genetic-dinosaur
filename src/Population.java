import java.awt.*;
import java.util.ArrayList;

public class Population {
    ArrayList<Subject> subjects=new ArrayList<>();
    int howManyDie = 0;
    int epoch=0;
    int lastBestScore=0;

    Population(int howMany) {
        for (int i = 0; i < howMany; i++) {

            subjects.add(new Subject(""+i));
        }

    }

    void doSomething(Obstacle obs, Graphics2D g,Game screen) {
        for (Subject subject : subjects) {
            System.out.print(subject.death+" ");
            subject.doSomething(obs);

            if (subject.death) {
                howManyDie++;
            }

        }
        System.out.println();
       others.bubbleSort(subjects);
        g.drawString(""+epoch,screen.width-30,20);
        g.drawString(""+lastBestScore,screen.width/2,20);
        g.drawString(""+subjects.get(0).dino.score,0,20);
        g.drawString(subjects.get(0).name, screen.width/2, 50);
        subjects.get(0).show(g,screen);

        if (howManyDie >= subjects.size()) {
            obs.x= -obs.width;
            System.out.println(epoch);
            Subject theBest = subjects.get(0);
            howManyDie = 0;
            epoch++;
            for (int i = 0; i < subjects.size(); i++) {
                try {

                    subjects.set(i,(Subject) theBest.clone());
                    subjects.get(i).brain.quitReferences();
                    subjects.get(i).brain.mutate();
                } catch (CloneNotSupportedException e
                ) {
                    e.printStackTrace();
                }


            }

        }
    }


}


