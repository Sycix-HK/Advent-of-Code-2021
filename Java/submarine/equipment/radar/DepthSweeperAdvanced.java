package submarine.equipment.radar;

import java.io.*;
import java.util.*;

public class DepthSweeperAdvanced{
    public static int Sweep(File input) {
        List<Integer> scan = new submarine.core.InputScanner<Integer>(input).getResult();
        
        // denoise
        for (int i = 0; i < scan.size()-3; i++) {
            scan.set(i, (scan.get(i)+scan.get(i+1)+scan.get(i+2)));
        }

        // count
        int c = 0;
        for (int i = 0; i < scan.size()-3; i++) {
            if (scan.get(i) > scan.get(i+1)) {c++;}
        }

        return c;
    }
}