package submarine.equipment.diagnostics.wildlife;

import submarine.core.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Lanternfish {
    public static void main(String[] args) {
        System.out.println("Lanternfish population at day 80: "+ growthPrediction(DataTray.getInput(6),80));
    }

    public static int growthPrediction(File file, int atDay)
    {
        try(BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            LinkedList<Integer> fish = new LinkedList<>(Arrays.stream( ( Arrays.stream(reader.readLine().split(",")).mapToInt(Integer::parseInt).toArray() ) ).boxed().collect(Collectors.toList()));
            for (int d = 0; d < atDay; d++)
            {
                for (int f = fish.size()-1; f != -1; f--)
                {
                    if ((fish.set(f,fish.get(f)-1)) == 0)
                    {
                        fish.set(f,6);
                        fish.add(8);
                    }
                    System.out.println(fish.size());
                }
            }
            return fish.size();
        }
        catch(Exception e){}
        return 0;
    } 
}
