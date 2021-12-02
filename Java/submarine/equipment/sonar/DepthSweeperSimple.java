package submarine.equipment.sonar;

import java.io.*;
import java.util.Scanner;

public class DepthSweeperSimple{
    public static int Sweep(File f)
    {
        int solution = 0;

        try (Scanner in = new Scanner(new FileReader(f)))
        {
            int depth = Integer.parseInt(in.nextLine());
            while (in.hasNextLine())
            {
                solution = (depth < (depth = Integer.parseInt(in.nextLine()))) ? solution + 1 : solution;
            }
            in.close();
        }
        catch (IOException e){}

        return solution;
    }
}