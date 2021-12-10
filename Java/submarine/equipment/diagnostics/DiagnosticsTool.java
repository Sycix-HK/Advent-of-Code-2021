
//////////////////////////////////////////////////////////////
/////////////////////|      Day 03      |/////////////////////
//////////////////////////////////////////////////////////////

package submarine.equipment.diagnostics;

import submarine.core.*;
import java.io.*;
import java.util.*;

public interface DiagnosticsTool {
    public static void main(String[] args) {
        TimeMeasure timer = new TimeMeasure();
        Logger.print(timer, "Consumption", calculateConsumption(DataTray.getInput(3)));
        Logger.print(timer, "Life support rating", calculateLifeSupportRating(DataTray.getInput(3)));
    }

    // Part 1

    public static int calculateConsumption(File data)
    {
        int gamma = 0;
        int epsilon = 0;

        ArrayList<String> dataRaw = new InputScanner(data).getResult();
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

        return gamma*epsilon;
    }

    // Part 2

    public static int oxygenGeneratorRating(File data)
    {
        ArrayList<String> dataRaw = new InputScanner(data).getResult();
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
        int rating = 0;
        for (int i = 0; i < bitSize; i++)
        {
            if (dataRaw.get(0).charAt(i) == '1') rating |= (1 << Math.abs(i-bitSize+1));
        }
        return rating;
    }
    public static int c02ScrubberRating(File data)
    {
        ArrayList<String> dataRaw = new InputScanner(data).getResult();
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
        int rating = 0;
        for (int i = 0; i < bitSize; i++)
        {
            if (dataRaw.get(0).charAt(i) == '1') rating |= (1 << Math.abs(i-bitSize+1));
        }
        return rating;
    }

    public static int calculateLifeSupportRating(File data)
    {
        int oxygenGeneratorRating = oxygenGeneratorRating(data);
        int c02ScrubberRating = c02ScrubberRating(data);
        return oxygenGeneratorRating*c02ScrubberRating;
    }
}
