package submarine.equipment.diagnostics.powerConsumption;

import java.io.*;
import java.util.*;

public class ConsumptionCalculator
{
    ArrayList<int[]> data = new ArrayList<>();
    int bitSize;
    //BitSet gamma = new BitSet(5);
    //BitSet epsilon = new BitSet(5);
    int gamma = 0;
    int epsilon = 0;

    public ConsumptionCalculator(File input)
    {
        ArrayList<String> dataRaw = new submarine.core.InputScannerString(input).getResult();
        for (String line : dataRaw) {
            bitSize = line.length();
            int[] lineData = new int[bitSize];
            for (int i = 0; i < bitSize; i++) {
                lineData[i] = Character.getNumericValue(line.charAt(i));
            }
            data.add(lineData);
        }
        ProcessData();
    }
    public void ProcessData()
    {
        for (int i = 0; i < bitSize; i++) {
            int c = 0;
            for (int[] b : data)
            {
                c += b[i];
            }
            if (c > (data.size()/2))    gamma   |= (1 << Math.abs(i-bitSize+1));   //gamma.set(c);
            else                        epsilon |= (1 << Math.abs(i-bitSize+1));   //epsilon.set(c);
        }
    }
    @Override
    public String toString()
    {
        return ("Consumption = gamma: " + gamma + " * epsilon: " + epsilon + " = " + gamma*epsilon);
    }
}