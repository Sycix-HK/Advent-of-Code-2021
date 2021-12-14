
//////////////////////////////////////////////////////////////
/////////////////////|      Day 14      |/////////////////////
//////////////////////////////////////////////////////////////

package submarine.equipment.polymerizator;

import submarine.core.*;
import java.util.*;
import java.io.*;
import java.util.regex.*;

public class Polymerizator {
    public static void main(String[] args) {
        TimeMeasure timer = new TimeMeasure();
        Logger.print(timer, "Most - least common element after step 10", leastAndMostCommonCharDifference(DataTray.getInput(14),10));
        Logger.print(timer, "Most - least common element after step 40", leastAndMostCommonCharDifference(DataTray.getInput(14),40));
    }

    public static long leastAndMostCommonCharDifference(File file, int steps)
    {
        Manual manual = new Manual(file);
        Map<String, Long> polymer = polymerizate(manual,steps);
        Map<Character,Long> occurrence = new HashMap<>();

        for (Map.Entry<String, Long> entry : polymer.entrySet())
        {
            occurrence.put(entry.getKey().charAt(0), 
                occurrence.containsKey(entry.getKey().charAt(0)) ? 
                    occurrence.get(entry.getKey().charAt(0))+entry.getValue() :
                    entry.getValue()
            );
            occurrence.put(entry.getKey().charAt(1), 
                occurrence.containsKey(entry.getKey().charAt(1)) ? 
                    occurrence.get(entry.getKey().charAt(1))+entry.getValue() :
                    entry.getValue()
            );
        }

        // fix ends
        occurrence.put(manual.startingPolymer.charAt(0),occurrence.get(manual.startingPolymer.charAt(0))-1);
        occurrence.put(manual.startingPolymer.charAt(manual.startingPolymer.length()-1),occurrence.get(manual.startingPolymer.charAt(manual.startingPolymer.length()-1))-1);
        for (Map.Entry<Character,Long> entry : occurrence.entrySet()) 
        {
            occurrence.put(entry.getKey(),entry.getValue()/2);
        }
        occurrence.put(manual.startingPolymer.charAt(0),occurrence.get(manual.startingPolymer.charAt(0))+1);
        occurrence.put(manual.startingPolymer.charAt(manual.startingPolymer.length()-1),occurrence.get(manual.startingPolymer.charAt(manual.startingPolymer.length()-1))+1);

        long maxval = 0;
        long minval = Long.MAX_VALUE;
        for (Map.Entry<Character,Long> entry : occurrence.entrySet())
        {
            if (entry.getValue() > maxval)
            {
                maxval = entry.getValue();
            }
            if (entry.getValue() < minval)
            {
                minval = entry.getValue();
            }
        }

        return maxval - minval;
    }

    public static Map<String, Long> polymerizate(Manual manual,int steps)
    {
        Map<String, Long> occ = manual.getStartingPolymer();
        for (int step = 0; step < steps; step++)
        {
            Map<String, Long> occbuild = new HashMap<>();
            
            for(Map.Entry<String,Long> entry : occ.entrySet())
            {
                String[] outpkeys = manual.getAddition(entry.getKey());
                occbuild.put(outpkeys[0], occbuild.containsKey(outpkeys[0]) ? occbuild.get(outpkeys[0])+entry.getValue() : entry.getValue());
                occbuild.put(outpkeys[1], occbuild.containsKey(outpkeys[1]) ? occbuild.get(outpkeys[1])+entry.getValue() : entry.getValue());
            }

            occ = occbuild;
        }
        return occ;
    }
    
}

class Manual
{
    private final HashMap<String,String[]> entries = new HashMap<>();
    public String startingPolymer;

    public Manual(File file)
    {
        for (String line : new InputScanner(file).getResult())
        {
            if (line. length() == 7)
            {
                Matcher match = Pattern.compile("(\\w\\w) -> (\\w)").matcher(line); 
                match.find();
                entries.put(match.group(1), new String[] {match.group(1).charAt(0)+match.group(2),match.group(2)+match.group(1).charAt(1)} );
            }
            else if (line.trim().length() != 0)
            {
                startingPolymer = line;
            }
        }
    }
    public String[] getAddition(String ab)
    {
        return entries.get(ab);
    }
    public Map<String, Long> getStartingPolymer()
    {
        Map<String, Long> poly = new HashMap<>();
        for (int i = 0; i < startingPolymer.length()-1; i++)
        {
            String val = (""+Character.valueOf(startingPolymer.charAt(i)) +""+ Character.valueOf(startingPolymer.charAt(i+1))+"");
            poly.put(val, poly.containsKey(val) ? poly.get(val)+1 : 1);
        }
        return poly;
    }
}
