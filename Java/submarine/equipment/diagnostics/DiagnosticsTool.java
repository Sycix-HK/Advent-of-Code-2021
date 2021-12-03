package submarine.equipment.diagnostics;

import java.io.*;
import java.util.*;

import submarine.core.*;

public abstract class DiagnosticsTool {
    public static void main(String[] args) {
        System.out.println(calculateConsumption(DataTray.getInput(3)).toString());
        System.out.println(calculateLifeSupportRating(DataTray.getInput(3)).toString());
    }

    // Part 1

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

    // Part 2

    public static int oxygenGeneratorRating(File data)
    {
        ArrayList<String> dataRaw = new InputScannerString(data).getResult();
        int bitSize = dataRaw.get(0).length();
        for (int i = 0; i < bitSize; i++) {
            int c = 0;
            for (String line : dataRaw) {
                c += Character.getNumericValue(line.charAt(i));
            }
            Boolean dominantBit = (c >= (dataRaw.size()/2.0));
            final int index = i;
            dataRaw.removeIf(line -> line.charAt(index) == (dominantBit?'0':'1'));
            if (dataRaw.size() == 1) break;
        }
        if (dataRaw.size() != 1) throw new RuntimeException("data size not equal to 1");
        int rating = 0;
        for (int i = 0; i < bitSize; i++)
        {
            if (dataRaw.get(0).charAt(i) == '1') rating |= (1 << Math.abs(i-bitSize+1));
        }
        return rating;
    }
    public static int C02ScrubberRating(File data)
    {
        ArrayList<String> dataRaw = new InputScannerString(data).getResult();
        int bitSize = dataRaw.get(0).length();
        for (int i = 0; i < bitSize; i++) {
            int c = 0;
            for (String line : dataRaw) {
                c += Character.getNumericValue(line.charAt(i));
            }
            Boolean dominantBit = (c < (dataRaw.size()/2.0));
            final int index = i;
            dataRaw.removeIf(line -> line.charAt(index) == (dominantBit?'0':'1'));
            if (dataRaw.size() == 1) break;
        }
        if (dataRaw.size() != 1) throw new RuntimeException("data size ("+ dataRaw.size() +") must equal to 1");
        int rating = 0;
        for (int i = 0; i < bitSize; i++)
        {
            if (dataRaw.get(0).charAt(i) == '1') rating |= (1 << Math.abs(i-bitSize+1));
        }
        return rating;
    }

    public static String calculateLifeSupportRating(File data)
    {
        int oxygenGeneratorRating = oxygenGeneratorRating(data);
        int c02ScrubberRating = C02ScrubberRating(data);
        return ("Life Support Rating = Oxygen generator: " + oxygenGeneratorRating + " * C02 scrubber: " + c02ScrubberRating + " = " + oxygenGeneratorRating*c02ScrubberRating);
    }
}
