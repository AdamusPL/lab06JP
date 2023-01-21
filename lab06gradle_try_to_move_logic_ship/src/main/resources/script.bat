echo Compiling.

::javac BuoyMain.java

For /L %%y IN (1,1,5) DO start java -Xmx32M pl.edu.pwr.aczekalski.lab06.BuoyMain

echo Running!