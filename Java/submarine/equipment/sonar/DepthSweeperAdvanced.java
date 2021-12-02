package submarine.equipment.sonar;

import java.io.*;
import java.util.*;

public class DepthSweeperAdvanced{
    public static int Sweep(File input) {
        ArrayList<Integer> scan = new submarine.core.InputScannerInteger(input).getResult();
        
        // denoise
        for (int i = 0; i < scan.size()-2; i++) {
            scan.set(i, (scan.get(i) + scan.get(i+1) + scan.get(i+2)));
        }

        // count
        int c = 0;
        for (int i = 0; i < scan.size()-2; i++) {
            if (scan.get(i) < scan.get(i+1)) {c++;}
        }

        return c;
    }
}