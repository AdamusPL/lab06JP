echo Compiling.

::javac BuoyMain.java

For /L %%y IN (1,1,14) DO start java -Xmx8M pl.edu.pwr.aczekalski.lab06.BuoyMain

echo Running!