package submarine.core;

public class TimeMeasure {
    long start;
    long segmentStart;
    public TimeMeasure()
    {
        start = System.currentTimeMillis();
        segmentStart = System.currentTimeMillis();
    }

    public long elapsedTime()
    {
        return System.currentTimeMillis() - start;
    }

    public long elapsedSegment()
    {
        long outp = System.currentTimeMillis() - segmentStart;
        segmentStart = System.currentTimeMillis();
        return outp;
    }
}
