
//////////////////////////////////////////////////////////////
/////////////////////|      Day 07      |/////////////////////
//////////////////////////////////////////////////////////////

package wildlife;

import submarine.core.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;

public class CrabSubmarines {
    public static void main(String[] args) {
        TimeMeasure timer = new TimeMeasure();
        Logger.print(timer, "Minimal simple fuel usage to group",optimalFuelToAlign(DataTray.getInput(7),FuelConsumptionType.SIMPLE));
        Logger.print(timer, "Minimal incremental fuel usage to group",optimalFuelToAlign(DataTray.getInput(7),FuelConsumptionType.INCREMENTAL));
    }

    public static int optimalFuelToAlign(File file, FuelConsumptionType fuel)
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            ArrayList<Integer> crabs = new ArrayList<>(Stream.of(reader.readLine().split(",")).mapToInt(Integer::parseInt).boxed().toList());
            Collections.sort(crabs);
            int fuelCost = 0;
            switch (fuel) {
                case SIMPLE:
                    Integer median = crabs.get(crabs.size() / 2);
                    for (Integer c : crabs)
                    {
                        fuelCost += Math.abs(c - median);
                    }
                    break;
                case INCREMENTAL:
                    Integer mean = (crabs.stream().reduce(0,Integer::sum))/crabs.size();
                    for (Integer c : crabs)
                    {
                        // n^2 + n  /  2
                        int n = Math.abs(c - mean);
                        fuelCost += (((n*n)+n) / 2);
                    }
                    break;
                default: throw new IllegalArgumentException("Damn these crabs are getting creative with their fuel");
            }
            return fuelCost;
        }
        catch (Exception e) {Logger.error(e);}
        return -1;
    }

    enum FuelConsumptionType
    {
        SIMPLE, INCREMENTAL
    }
}
