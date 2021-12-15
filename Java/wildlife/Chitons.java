
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
        System.out.println("(!) Expected runtime: 801 ms");
        Logger.print(timer, "Lowest risk level in small map", minimumRiskRoutePartial(DataTray.getInput(15)));
        System.out.println("(!) Expected runtime: 510220 ms (~ 8 minutes)");
        Logger.print(timer, "Lowest risk level in full map", minimumRiskRouteFull(DataTray.getInput(15)));
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

        return dijkstra(set,cave,input,linelength,lines.size());
    }

    public static int minimumRiskRouteFull(File file)
    {
        List<String> lines = new InputScanner(file).getResult();
        int linelength = lines.get(0).length();
        
        Tile[][] cave = new Tile[lines.size()*5][linelength*5];
        int[][] input = new int[lines.size()*5][linelength*5];
        MaxIndexedUniqueTileSet set = new MaxIndexedUniqueTileSet();

        // put values
        for (int j = 0; j < 5; j++)
        {
            for (int i = 0; i < 5; i++)
            {
                for (int y = 0; y < lines.size(); y++)
                {
                    String line = lines.get(y);
                    for (int x = 0; x < linelength; x++)
                    {
                        int nx = x+(i*linelength);
                        int ny = y+(j*lines.size());
                        input[nx][ny] = warp((Character.getNumericValue(line.charAt(x)))+i+j);
                        Tile t = new Tile(nx,ny);
                        cave[nx][ny] = t;
                        set.insert(t);
                    }
                }
            }
        }

        cave[0][0].risk = 0;
        set.update(cave[0][0]);

        return dijkstra(set,cave,input,linelength*5,lines.size()*5);
    }

    private static int dijkstra(MaxIndexedUniqueTileSet set, Tile[][] cave, int[][] input, int linelengthX, int linelengthY)
    {
        while (set.size() != 0)
        {
            Tile t = set.pop();
            t.locked = true;

            //up
            if (t.y!=0) updateTile(t, cave[t.x][t.y-1], t.risk+input[t.x][t.y-1], set);

            //right
            if (t.x!=linelengthX-1) updateTile(t, cave[t.x+1][t.y], t.risk+input[t.x+1][t.y], set);

            //down
            if (t.y!=linelengthY-1) updateTile(t, cave[t.x][t.y+1], t.risk+input[t.x][t.y+1], set);

            //left
            if (t.x!=0) updateTile(t, cave[t.x-1][t.y], t.risk+input[t.x-1][t.y], set);
        }

        return cave[linelengthY-1][linelengthX-1].risk;
    }

    private static void updateTile(Tile t, Tile t2, int newRisk, MaxIndexedUniqueTileSet set)
    {
        if (!t2.locked && t2.risk > newRisk)
        {
            t2.risk = newRisk;
            t2.fromX = t.x;
            t2.fromY = t.y;
            set.update(t2);
        }
    }
    public static int warp(int n)
    {
        while (n > 9)
        {
            n-=9;
        }
        return n;
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
