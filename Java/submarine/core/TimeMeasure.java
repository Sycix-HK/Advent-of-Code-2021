package submarine.core;

public class TimeMeasure {
    long start;
    public TimeMeasure()
    {
        start = System.currentTimeMillis();
    }

    public long elapsedTime()
    {
        return System.currentTimeMillis() - start;
    }
}
