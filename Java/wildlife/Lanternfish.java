
//////////////////////////////////////////////////////////////
/////////////////////|      Day 06      |/////////////////////
//////////////////////////////////////////////////////////////

package wildlife;

import submarine.core.*;
import java.io.*;
import java.util.*;

public class Lanternfish {
    public static void main(String[] args) {
        TimeMeasure timer = new TimeMeasure();
        Logger.print(timer, "Lanternfish population at day 80", growthPrediction(DataTray.getInput(6),80));
        Logger.print(timer, "Lanternfish population at day 256", growthPrediction(DataTray.getInput(6),256));
    }

    public static long growthPrediction(File file, int atDay)
    {
        try(BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            long[] fish = new long[10];
            for (String s : reader.readLine().split(",")) {
                fish[Integer.parseInt(s)]++;
            }
            for (int day = 0; day < atDay; day++)
            {
                fish[7] += fish[0]; 
                fish[9] = fish[0];
                for (int i = 0; i < 9; i++) {
                    fish[i] = fish[i+1];
                }
                fish[9] = 0;
            }
            return Arrays.stream(fish).sum();
        }
        catch(Exception e){Logger.error(e);}
        return 0;
    } 
}
