import java.util.ArrayList;

public class Population {
    ArrayList<Subject> subjects;
    Population(int howMany){
        for(int i=0;i<howMany;i++){
           subjects.add(new Subject());
        }

    }

}
