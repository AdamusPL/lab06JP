package pl.edu.pwr.aczekalski.lab06.maker.buoy;

import pl.edu.pwr.aczekalski.lab06.model.board.Board;
import pl.edu.pwr.aczekalski.lab06.model.buoy.Buoy;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class MakeBuoys {
    int buoyPositionX;
    int buoyPositionY;
    ArrayList<Buoy> buoys;
    Board board;

    public MakeBuoys(Socket clientSocket, int id, int buoyPositionX, int buoyPositionY, ArrayList<Buoy> buoys, Board board){
        this.clientSocket=clientSocket;
        this.id=id;
        this.buoyPositionX=buoyPositionX;
        this.buoyPositionY=buoyPositionY;
        this.buoys=buoys;
        this.board=board;
    }

    int id;
    Socket clientSocket;

    public ArrayList<Buoy> addBuoy() throws IOException {
        Buoy buoy = new Buoy(id,clientSocket,buoyPositionX,buoyPositionY,board);
        buoy.start();
        buoys.add(buoy);
        return buoys;
    }

}
