
//////////////////////////////////////////////////////////////
/////////////////////|      Day 09      |/////////////////////
//////////////////////////////////////////////////////////////

package submarine.equipment.sonar;

import submarine.core.*;
import java.io.*;
import java.util.*;

public class CaveMapper {
    public static void main(String[] args) {
        TimeMeasure timer = new TimeMeasure();
        Logger.print(timer, "Sum of risk levels", riskLevel(DataTray.getInput(9)));
        Logger.print(timer, "3 largest basin sizes multiplied", largestBasinsRisk(DataTray.getInput(9)));
    }

    public static int riskLevel(File file)
    {
        ArrayList<ArrayList<Integer>> map = new ArrayList<ArrayList<Integer>>();
        ArrayList<String> lines = new InputScanner(file).getResult();
        ArrayList<Integer> lowPoints = new ArrayList<>();
        for (int y = 0; y < lines.size(); y++)
        {
            ArrayList<Integer> row = new ArrayList<>();
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

    public static int largestBasinsRisk(File file)
    {
        ArrayList<String> lines = new InputScanner(file).getResult();
        int linelength = lines.get(0).length();
        int[][] map = new int[lines.size()+2][linelength+2];

        // make border
        for(int x = 0; x < linelength+2; x++)
        {
            map[0][x] = 9;
            map[lines.size()+1][x] = 9;
        }
        for(int y = 1; y < lines.size()+1; y++)
        {
            map[y][0] = 9;
            map[y][linelength+1] = 9;
        }

        // put values
        for (int y = 1; y < lines.size()+1; y++)
        {
            String line = lines.get(y-1);
            for (int x = 1; x < linelength+1; x++)
            {
                map[y][x] = Character.getNumericValue(line.charAt(x-1));
            }
        }

        // count
        int[] sizes = new int[] {0,0,0};

        for(int y = 0; y < linelength+2; y++)
        {
            for(int x = 0; x < lines.size()+2; x++)
            {
                int size = next(x,y,map);

                if (size > sizes[0])
                {
                    sizes[0] = size;
                    Arrays.sort(sizes);
                }
            }
        }

        return sizes[0] * sizes[1] * sizes[2];
    }
    static int next(int x, int y, int[][] map)
    {
        if (map[x][y] == 9) return 0;
        map[x][y] = 9;
        return 1 + next(x+1,y,map) + next(x-1,y,map) + next(x,y+1,map) + next(x,y-1,map);
    }

}
