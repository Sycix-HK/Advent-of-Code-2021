package submarine.equipment.diagnostics;

import java.io.*;
import java.util.*;
import submarine.core.*;

public abstract class DiagnosticsTool {
    public static void main(String[] args) {
        System.out.println(calculateConsumption(DataTray.getInput(3)).toString());
    }

    public static String calculateConsumption(File data)
    {
        int gamma = 0;
        int epsilon = 0;

        ArrayList<String> dataRaw = new InputScannerString(data).getResult();
        int bitSize = dataRaw.get(0).length();
        int dataSize = dataRaw.size();
        for (int i = 0; i < bitSize; i++) {
            int c = 0;
            for (String line : dataRaw) {
                c += Character.getNumericValue(line.charAt(i));
            }
            if (c > (dataSize/2))   gamma   |= (1 << Math.abs(i-bitSize+1));
            else                    epsilon |= (1 << Math.abs(i-bitSize+1));
        }

        return ("Consumption = gamma: " + gamma + " * epsilon: " + epsilon + " = " + gamma*epsilon);
    }
}
