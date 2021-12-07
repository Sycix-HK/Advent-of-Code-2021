package wildlife;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import submarine.core.*;

public class CrabSubmarines {
    public static void main(String[] args) {
        System.out.println("Minimal fuel usage to group: " + optimalFuelToAlign(DataTray.getInput(7)));
    }

    public static int optimalFuelToAlign(File file)
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            ArrayList<Integer> crabs = new ArrayList<Integer>(Stream.of(reader.readLine().split(",")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList()));
            Collections.sort(crabs);
            Integer median =  crabs.get(crabs.size() / 2);
            int fuelCost = 0;
            for (Integer c : crabs)
            {
                fuelCost += Math.abs(c - median);
            }
            return fuelCost;
        }
        catch (Exception e) {System.err.println(e);}
        return -1;
    }
}
