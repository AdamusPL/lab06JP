echo Compiling.

::javac BuoyMain.java

For /L %%y IN (1,1,64) DO start "Buoy" java -Xmx32M pl.edu.pwr.aczekalski.lab06.BuoyMain

echo Running!