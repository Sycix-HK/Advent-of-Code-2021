
//////////////////////////////////////////////////////////////
/////////////////////|      Day 09      |/////////////////////
//////////////////////////////////////////////////////////////

package submarine.equipment.sonar;

import submarine.core.*;
import java.io.*;
import java.util.*;

public class HeightMapper {
    public static void main(String[] args) {
        System.out.println("Sum of risk levels: "+riskLevel(DataTray.getInput(9)));
    }

    public static int riskLevel(File file)
    {
        ArrayList<ArrayList<Integer>> map = new ArrayList<ArrayList<Integer>>();
        ArrayList<String> lines = new InputScannerString(file).getResult();
        ArrayList<Integer> lowPoints = new ArrayList<Integer>();
        for (int y = 0; y < lines.size(); y++)
        {
            ArrayList<Integer> row = new ArrayList<Integer>();
            int linelength = lines.get(y).length();
            for (int x = 0; x < linelength; x++)
            {
                row.add(Character.getNumericValue(lines.get(y).charAt(x)));
                if (
                    (y != 0) &&
                    (y == 1 || map.get(y-2).get(x) > map.get(y-1).get(x)) &&
                    (x == linelength-1 || map.get(y-1).get(x+1) > map.get(y-1).get(x)) &&
                    (row.get(x) > map.get(y-1).get(x)) &&
                    (x == 0 || map.get(y-1).get(x-1) > map.get(y-1).get(x))
                )
                {
                    lowPoints.add(map.get(y-1).get(x));
                }

                if (y == lines.size()-1) // last row
                {
                    if (
                        (map.get(y-1).get(x) > row.get(x)) &&
                        (x == linelength-1 || Character.getNumericValue(lines.get(y).charAt(x+1)) > row.get(x)) &&
                        (x == 0 || Character.getNumericValue(lines.get(y).charAt(x-1)) > row.get(x))
                    )
                    {
                        lowPoints.add(row.get(x));
                    }
                }
            }
            map.add(row);
        }

        return lowPoints.stream().mapToInt(Integer::intValue).sum() + lowPoints.size();
    }
}
