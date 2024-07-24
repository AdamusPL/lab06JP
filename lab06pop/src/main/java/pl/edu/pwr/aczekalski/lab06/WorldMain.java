/**
 * @author Adam Czekalski
 * dir /b /s *.java > sources.txt <- copy all paths to txt file
 * javac -d bin --module-path (path to the module) @sources.txt <- compile
 * jar cvf lab06pop.jar -C bin . <- create .jar
 * java -p (path to the module) -m (module name)/(path to the main class) <- launch .jar
 */
package pl.edu.pwr.aczekalski.lab06;

import pl.edu.pwr.aczekalski.lab06.server.WorldServer;

import javax.swing.*;

public class WorldMain extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                WorldServer worldServer = new WorldServer();
                worldServer.startT();
            }
        });
    }

    public WorldMain() {

    }

}
