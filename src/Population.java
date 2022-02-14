import java.awt.*;
import java.util.ArrayList;

public class Population {
    ArrayList<Subject> subjects=new ArrayList<>();
    int howManyDie = 0;
    int epoch=0;

    Population(int howMany) {
        for (int i = 0; i < howMany; i++) {

            subjects.add(new Subject());
        }

    }

    void doSomething(Obstacle obs, Graphics2D g,Game screen) {
        for (Subject subject : subjects) {
            System.out.print(subject.brain.network.size()+" ");
            subject.doSomething(obs);

            if (subject.death) {
                howManyDie++;
            }

        }
        System.out.println();
        subjects.sort(new Compare());

        subjects.get(0).show(g,screen);
        if (howManyDie == subjects.size()) {
            obs.x=screen.width;
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


