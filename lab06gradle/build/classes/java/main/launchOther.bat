echo Compiling.

::javac WorldMain.java

start java -Xmx32M pl.edu.pwr.aczekalski.lab06.WorldMain

TIMEOUT /T 5

::javac HeadQuartersMain.java

start java -Xmx32M pl.edu.pwr.aczekalski.lab06.HeadQuartersMain

TIMEOUT /T 5

::javac ShipMain.java

start java -Xmx32M pl.edu.pwr.aczekalski.lab06.ShipMain

TIMEOUT /T 5

::javac BuoyMain.java

start java -Xmx32M pl.edu.pwr.aczekalski.lab06.BuoyMain

echo Running!