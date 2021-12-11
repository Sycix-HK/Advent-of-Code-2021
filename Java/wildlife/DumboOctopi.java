
//////////////////////////////////////////////////////////////
/////////////////////|      Day 11      |/////////////////////
//////////////////////////////////////////////////////////////

package wildlife;

import submarine.core.*;
import java.io.*;
import java.util.*;

public class DumboOctopi {
    public static void main(String[] args) {
        TimeMeasure timer = new TimeMeasure();
        Logger.print(timer, "Flashes after 100 steps", flashSum(DataTray.getInput(11),100));
        Logger.print(timer, "Octopi synchronize on step", getSyncStep(DataTray.getInput(11)));
    }

    public static int flashSum(File file, int at)
    {
        Octopus[][] octopi = scanCave(file);
        int sum = 0;
        for(int i = 0; i < at; i++)
        {
            for (int y = 1; y < octopi.length-1; y++)
            {
                for (int x = 1; x < octopi[y].length-1; x++)
                {
                    sum += octopi[y][x].charge(i);
                }
            }
        }
        return sum;
    }
    public static int getSyncStep(File file)
    {
        Octopus[][] octopi = scanCave(file);
        int flashes = 0;
        int i = 0;
        while (flashes != ((octopi[0].length-2)*(octopi.length-2)))
        {
            flashes = 0;
            for (int y = 1; y < octopi.length-1; y++)
            {
                for (int x = 1; x < octopi[y].length-1; x++)
                {
                    flashes += octopi[y][x].charge(i);
                }
            }
            i++;
        }
        return i;
    }
    public static Octopus[][] scanCave(File file)
    {
        List<String> lines = new InputScanner(file).getResult();
        int linelength = lines.get(0).length();
        Octopus[][] octopi = new Octopus[lines.size()+2][linelength+2];

        // make border
        for(int x = 0; x < linelength+2; x++)
        {
            octopi[0][x] = new Octopus();
            octopi[lines.size()+1][x] = new Octopus();
        }
        for(int y = 1; y < lines.size()+1; y++)
        {
            octopi[y][0] = new Octopus();
            octopi[y][linelength+1] = new Octopus();
        }

        // put values
        for (int y = 1; y < lines.size()+1; y++)
        {
            String line = lines.get(y-1);
            for (int x = 1; x < linelength+1; x++)
            {
                octopi[y][x] = new Octopus( Character.getNumericValue(line.charAt(x-1)) );
            }
        }

        // link
        for (int y = 1; y < lines.size()+1; y++)
        {
            for (int x = 1; x < linelength+1; x++)
            {
                octopi[y][x].linkWith(new Octopus[]{
                    octopi[y-1][x-1], octopi[y-1][x], octopi[y-1][x+1],
                    octopi[y][x-1],        /**/        octopi[y][x+1],
                    octopi[y+1][x-1], octopi[y+1][x], octopi[y+1][x+1]
                });
            }
        }

        return octopi;
    }

}
class Octopus
{
    private int charge;
    private int exhaustedOn;
    private boolean outOfRange;
    private Octopus[] linked;
    public Octopus(int charge)
    {
        this.charge = charge;
        exhaustedOn = -1;
    }
    public Octopus() // border
    {
        this.outOfRange = true;
    }
    public int charge(int step)
    {
        if (!outOfRange && exhaustedOn < step && ++charge > 9)
        {
            exhaustedOn = step;
            charge = 0;
            int flashes = 0;
            for (Octopus dumbo : linked) 
            {
                flashes += dumbo.charge(step);
            }
            return 1 + flashes;
        }
        return 0;
    }
    public void linkWith(Octopus[] octopi)
    {
        linked = octopi;
    }
}
