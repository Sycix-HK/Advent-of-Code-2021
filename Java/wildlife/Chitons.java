
//////////////////////////////////////////////////////////////
/////////////////////|      Day 15      |/////////////////////
//////////////////////////////////////////////////////////////

package wildlife;

import submarine.core.*;
import java.io.*;
import java.util.*;

public class Chitons {
    public static void main(String[] args) {
        TimeMeasure timer = new TimeMeasure();
        Logger.print(timer, "Lowest risk level in small map", minimumRiskRoutePartial(DataTray.getInput(15)));
        //Logger.print(timer, "Lowest risk level in full map", minimumRiskRouteFull(DataTray.getInput(15)));
    }

    public static int minimumRiskRoutePartial(File file)
    {
        List<String> lines = new InputScanner(file).getResult();
        int linelength = lines.get(0).length();
        
        Tile[][] cave = new Tile[lines.size()][linelength];
        int[][] input = new int[lines.size()][linelength];
        MaxIndexedUniqueTileSet set = new MaxIndexedUniqueTileSet();

        // put values
        for (int y = 0; y < lines.size(); y++)
        {
            String line = lines.get(y);
            for (int x = 0; x < linelength; x++)
            {
                input[x][y] = Character.getNumericValue(line.charAt(x));
                Tile t = new Tile(x,y);
                cave[x][y] = t;
                set.insert(t);
            }
        }
        cave[0][0].risk = 0;
        set.update(cave[0][0]);

        //dijkstra
        while (set.size() != 0)
        {
            Tile t = set.pop();
            t.locked = true;

            if (t.y!=0) //up
            {
                Tile t2 = cave[t.x][t.y-1];
                if (!t2.locked && t2.risk > t.risk+input[t.x][t.y-1])
                {
                    t2.risk = t.risk+input[t.x][t.y-1];
                    t2.fromX = t.x;
                    t2.fromY = t.y;
                    set.update(t2);
                }
            }
            if (t.x!=linelength-1) //right
            {
                Tile t2 = cave[t.x+1][t.y];
                if (!t2.locked && t2.risk > t.risk+input[t.x+1][t.y])
                {
                    t2.risk = t.risk+input[t.x+1][t.y];
                    t2.fromX = t.x;
                    t2.fromY = t.y;
                    set.update(t2);
                }
            }
            if (t.y!=lines.size()-1) //down
            {
                Tile t2 = cave[t.x][t.y+1];
                if (!t2.locked && t2.risk > t.risk+input[t.x][t.y+1])
                {
                    t2.risk = t.risk+input[t.x][t.y+1];
                    t2.fromX = t.x;
                    t2.fromY = t.y;
                    set.update(t2);
                }
            }
            if (t.x!=0) //left
            {
                Tile t2 = cave[t.x-1][t.y];
                if (!t2.locked && t2.risk > t.risk+input[t.x-1][t.y])
                {
                    t2.risk = t.risk+input[t.x-1][t.y];
                    t2.fromX = t.x;
                    t2.fromY = t.y;
                    set.update(t2);
                }
            }
        }

        //debug
        for (int y = 0; y < lines.size(); y++)
        {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < linelength; x++)
            {
                sb.append(cave[x][y].risk < 10?(" "+cave[x][y].risk):(cave[x][y].risk)).append(' ');
            }
            System.out.println(sb);
        }

        return cave[lines.size()-1][linelength-1].risk;

    }

}
class MaxIndexedUniqueTileSet 
{
    private List<Tile> maxIndexedList = new LinkedList<>();

    public void insert(Tile tile)
    {
        maxIndexedList.add(tile);
        int i = maxIndexedList.size()-1;
        while (i != 0 && maxIndexedList.get(i-1).risk < maxIndexedList.get(i).risk) {
            swap(i,i-1);
            i--;
        }
    }
    
    public void update(Tile tile)
    {
        maxIndexedList.remove(tile);
        insert(tile);
    }

    public Tile pop()
    {
        Tile t = maxIndexedList.get(maxIndexedList.size()-1);
        maxIndexedList.remove(t);
        return t;
    }

    public int size()
    {
        return maxIndexedList.size();
    }
    
    void swap(int a, int b)
    {
        Tile t = maxIndexedList.get(a);
        maxIndexedList.set(a,maxIndexedList.get(b));
        maxIndexedList.set(b,t);
    }
}

class Tile
{
    int x;
    int y;
    int fromX;
    int fromY;
    int risk;
    boolean locked;
    public Tile(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.risk = Integer.MAX_VALUE;
        locked = false;
    }
}
