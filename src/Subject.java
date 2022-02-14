import java.awt.*;
import java.util.ArrayList;

class Subject implements Cloneable {
    Brain brain=new Brain();
    Dinosaur dino=new Dinosaur();
    String[] names={"width","height","distance","y obstacle","speed","y player"};
    boolean death=false;
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

        if(death){
            dino.actualSprite=dino.dinoDie;
            return;
        }
        death=obstacle.isOnArea(dino);


        // after all that I clear the nodes
        brain.clearNodes();

        // This is for pass the values to the input nodes of the neural network

        ArrayList<Double> input=new ArrayList<>();

        input.add((double) obstacle.width);         // width of obstacle
        input.add((double) obstacle.height);        // height of obstacle
        input.add((double) obstacle.x);             // distance of obstacle
        input.add((double) obstacle.y);             // y pos of obstacle
        input.add((double) obstacle.movePerFrame);  // speed
        input.add((double) dino.y);                 // y pos of player

        brain.passToInput(input);
        //then I get the result
       ArrayList<Double> output= brain.result();
      if( output.get(0)>0.8){
          dino.jump(false);
      }else if(output.get(1)>0.8){
          dino.jump(true);
      }else if(output.get(2)>0.8){
          dino.duck();
      }
      dino.moving();

    }
    void show(Graphics2D g,Game screen){
        if(!death) {
            dino.show(g, screen);
            brain.show(g, screen);
        }

    }
    public Object clone() throws CloneNotSupportedException {
        death=false;
        dino.score=0;
        brain.clearNodes();
        return super.clone();
    }
}