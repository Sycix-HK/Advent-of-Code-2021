package submarine.equipment.sonar;

import java.io.*;
import java.util.*;
import submarine.core.*;

public class DepthSweeper {

    public static void main(String[] args) {
        System.out.println("Simple scan:    "+ SweepSimple  (DataTray.getInput(1)));
        System.out.println("Denoised scan:  "+ SweepAdvanced(DataTray.getInput(1)));
    }

    public static int SweepSimple(File f)
    {
        int elevations = 0;

        try (Scanner in = new Scanner(new FileReader(f)))
        {
            int depth = Integer.parseInt(in.nextLine());
            while (in.hasNextLine())
            {
                elevations = (depth < (depth = Integer.parseInt(in.nextLine()))) ? elevations + 1 : elevations;
            }
            in.close();
        }
        catch (IOException e){}

        return elevations;
    }

    public static int SweepAdvanced(File input) {
        ArrayList<Integer> scan = new submarine.core.InputScannerInteger(input).getResult();
        
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
