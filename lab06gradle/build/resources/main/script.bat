echo Compiling.

::javac BuoyMain.java

For /L %%y IN (1,1,64) DO start java -Xmx32M pl.edu.pwr.aczekalski.BuoyMain

echo Running!