import java.awt.*;
import java.util.ArrayList;

class Subject{
    Brain brain=new Brain();
    Dinosaur dino=new Dinosaur();
    String[] names={"width","height","distance","y obstacle","speed","y player"};
    /*
                             ///0| ---> 0 ---> 0 --->\
     |width of obstacle     ////0| ---> 0 ---> 0 ---->\
     |height of obstacle   /////0| ---> 0 ---> 0 ----->\--->small jump
     |distance of next obstacle/0| ---> 0 ---> 0 ------>\--> big jump
     |y position of obstacle\\\\0| ---> 0 ---> 0 ------>/--> duck
     |speed                \\\\\0| ---> 0 ---> 0 ----->/
     |player y pos          \\\\0| ---> 0 ---> 0 ---->/
                             \\\0| ---> 0 ---> 0 --->/
    */
    Subject(){
        for(int i=0;i<names.length;i++){
            brain.network.get(i).name=names[i];
        }
    }
    void doSomething(Obstacle obstacle){
        ArrayList<Double> input=new ArrayList<>();
        input.add((double) obstacle.width);
        input.add((double) obstacle.height);
        input.add((double) obstacle.x);
        input.add((double) obstacle.y);
        input.add((double) obstacle.movePerFrame);
        input.add((double) dino.y);
        brain.passToInput(input);

       ArrayList<Double> output= brain.result();
      if( output.get(0)>70){
          dino.jump(false);
      }else if(output.get(1)>70){
          dino.jump(true);
      }else if(output.get(2)>70){
          dino.duck();
      }

      brain.clearNodes();

    }
    void show(Graphics2D g,Game screen){
        dino.show(g,screen);
        brain.show(g,screen);

    }

}