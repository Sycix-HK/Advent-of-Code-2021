package submarine.core;

import java.io.File;

public class DataTray {

    private DataTray(){}
    
    public static File getInput(int day)
    {
        return new File("data/Day "+((day < 10)?"0"+day:day)+"/input.txt");
    }
    public static File getTest(int day)
    {
        return new File("data/Day "+((day < 10)?"0"+day:day)+"/test.txt");
    }
}