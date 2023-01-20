package pl.edu.pwr.aczekalski.lab06.model.ship;

import java.util.Random;

public class Direction {
    public int x=0;
    public int y=0;

    public static Direction randomDirection(){
        Direction direction = new Direction();

        direction.x=new Random().nextInt(3)-1;
        direction.y=new Random().nextInt(3)-1;

        return direction;
    }
}
