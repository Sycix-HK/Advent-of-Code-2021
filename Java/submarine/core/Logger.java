package submarine.core;

public class Logger { 
    
    private Logger(){}

    public static void print(TimeMeasure timer, String explaination, long solution)
    {
        System.out.println(String.format("(%d ms) %s: \u001B[33m%d\u001B[0m",timer.elapsedSegment(),explaination, solution));
    }
    public static void print(TimeMeasure timer, String explaination, int solution)
    {
        System.out.println(String.format("(%d ms) %s: \u001B[33m%d\u001B[0m",timer.elapsedSegment(),explaination, solution));
    }
    public static void error(Exception e)
    {
        System.err.println(e);
    }
}
