package submarine.core;

import java.io.File;

public class DataTray {
    public static File getInput(int day)
    {
        return new File("data/Day "+((day < 10)?"0"+day:day)+"/input.txt");
    }
}