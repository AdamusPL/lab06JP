package pl.edu.pwr.aczekalski.lab06.model.ship;

import java.util.Random;

public class Direction {
    private int x = 0;
    private int y = 0;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
//directions encoding:
    //0, 1 - N
    //1, 1 - NE
    //1, 0 - E
    //1, -1 - SE
    //0, -1 - S
    //-1, -1 - SW
    //-1, 0 - W
    //-1, 1 - NW

    public static Direction randomDirection() { //randomise new direction
        Direction direction = new Direction();

        direction.x = new Random().nextInt(3) - 1;
        direction.y = new Random().nextInt(3) - 1;

        return direction;
    }
}
