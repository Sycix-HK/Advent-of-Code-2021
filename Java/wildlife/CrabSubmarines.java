
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
        System.out.println("Minimal simple      fuel usage to group: " + optimalFuelToAlign(DataTray.getInput(7),FuelConsumptionType.simple));
        System.out.println("Minimal incremental fuel usage to group: " + optimalFuelToAlign(DataTray.getInput(7),FuelConsumptionType.incremental));
    }

    public static int optimalFuelToAlign(File file, FuelConsumptionType fuel)
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            ArrayList<Integer> crabs = new ArrayList<Integer>(Stream.of(reader.readLine().split(",")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList()));
            Collections.sort(crabs);
            int fuelCost = 0;
            switch (fuel) {
                case simple:
                    Integer median = crabs.get(crabs.size() / 2);
                    for (Integer c : crabs)
                    {
                        fuelCost += Math.abs(c - median);
                    }
                    break;
                case incremental:
                    Integer mean = (crabs.stream().reduce(0,Integer::sum))/crabs.size();
                    for (Integer c : crabs)
                    {
                        // n^2 + n  /  2
                        int n = Math.abs(c - mean);
                        fuelCost += (((n*n)+n) / 2);
                    }
                    break;
                default: throw new IllegalArgumentException("These crabs are getting creative with their fuel");
            }
            return fuelCost;
        }
        catch (Exception e) {System.err.println(e);}
        return -1;
    }

    enum FuelConsumptionType
    {
        simple, incremental
    }
}
