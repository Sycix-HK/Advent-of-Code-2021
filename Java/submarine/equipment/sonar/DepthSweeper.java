
//////////////////////////////////////////////////////////////
/////////////////////|      Day 01      |/////////////////////
//////////////////////////////////////////////////////////////

package submarine.equipment.sonar;

import submarine.core.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class DepthSweeper {

    public static void main(String[] args) {
        TimeMeasure timer = new TimeMeasure();
        Logger.print(timer, "Simple scan", sweepSimple(DataTray.getInput(1)));
        Logger.print(timer, "Denoised scan", sweepAdvanced(DataTray.getInput(1)));
    }

    public static int sweepSimple(File f)
    {
        int elevations = 0;

        try (Scanner in = new Scanner(new FileReader(f)))
        {
            int depth = Integer.parseInt(in.nextLine());
            while (in.hasNextLine())
            {
                elevations = (depth < (depth = Integer.parseInt(in.nextLine()))) ? elevations + 1 : elevations;
            }
        }
        catch (IOException e){
            Logger.error(e);
        }

        return elevations;
    }

    public static int sweepAdvanced(File input) {
        List<Integer> scan = new submarine.core.InputScanner(input).getResult().stream().map(Integer::parseInt).collect(Collectors.toList());
        
        // denoise
        for (int i = 0; i < scan.size()-2; i++) {
            scan.set(i, (scan.get(i) + scan.get(i+1) + scan.get(i+2)));
        }

        // count
        int elevations = 0;
        for (int i = 0; i < scan.size()-2; i++) {
            if (scan.get(i) < scan.get(i+1)) {elevations++;}
        }

        return elevations;
    }
}
