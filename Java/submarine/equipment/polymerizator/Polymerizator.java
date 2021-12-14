
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
        String polymer = polymerizate(new Manual(file),steps);
        Map<Character, Long> occurences = new LinkedHashMap<>();
        for (int i = 0; i < polymer.length(); i++)
        {
            Character c = polymer.charAt(i);
            occurences.put(c, occurences.containsKey(c) ? occurences.get(c)+1 : 1);
        }
        long maxval = 0;
        long minval = Long.MAX_VALUE;
        for (Character c : occurences.keySet())
        {
            if (occurences.get(c) > maxval)
            {
                maxval = occurences.get(c);
            }
            if (occurences.get(c) < minval)
            {
                minval = occurences.get(c);
            }
        }

        return maxval - minval;
    }

    public static String polymerizate(Manual manual,int steps)
    {
        StringBuilder s = new StringBuilder();
        s.append(manual.getStartingPolymer());
        for (int step = 0; step < steps; step++)
        {
            System.out.println(step);
            String polymer = s.toString();
            s = new StringBuilder();

            for (int i = 0; i < polymer.length()-1; i++)
            {
                s.append(polymer.charAt(i));
                s.append(manual.getAddition(polymer.charAt(i), polymer.charAt(i+1)));
            }
            s.append(polymer.charAt(polymer.length()-1));
        }
        return s.toString();
    }
    
}

class Manual
{
    private final HashMap<String,Character> entries = new HashMap<>();
    private String startingPolymer;
    public String getStartingPolymer() { return startingPolymer; }

    public Manual(File file)
    {
        for (String line : new InputScanner(file).getResult())
        {
            if (line. length() == 7)
            {
                Matcher match = Pattern.compile("(\\w)(\\w) -> (\\w)").matcher(line); 
                match.find();
                entries.put((match.group(1)+match.group(2)), Character.valueOf(match.group(3).charAt(0)));
            }
            else if (line.trim().length() != 0)
            {
                startingPolymer = line;
            }
        }
    }
    public Character getAddition(char a, char b)
    {
        return entries.get(String.valueOf(a) + String.valueOf(b));
    }
}
