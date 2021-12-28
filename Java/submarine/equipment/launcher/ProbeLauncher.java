
//////////////////////////////////////////////////////////////
/////////////////////|      Day 17      |/////////////////////
//////////////////////////////////////////////////////////////

package submarine.equipment.launcher;

import submarine.core.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class ProbeLauncher {
    public static void main(String[] args) {
        TimeMeasure timer = new TimeMeasure();
        Area area = new Area(DataTray.getInput(17));
        Logger.print(timer, "Maximum height", getMaximumHeight(area));
        Logger.print(timer, "Possible shots", bruteforceHits(area));
    }

    public static int getMaximumHeight(Area area) {
        int n = Math.abs(area.yStart)-1;
        return (((n*n)+n) / 2);
    }

    public static int bruteforceHits(Area area)
    {
        int sum = 0;
        for (int Yvel = area.yStart; Yvel < Math.abs(area.yStart); Yvel++)
        {
            for (int Xvel = 1; Xvel <= area.xEnd; Xvel++)
            {
                sum += shoot(Xvel, Yvel, area) ? 1:0;
                int d = 0;
            }
        }
        return sum;
    }

    public static boolean shoot(int initx, int inity, Area area) {
        Vector2 velocity = new Vector2(initx,inity);
        Vector2 position = new Vector2(0,0);
        while (position.y > area.yEnd)
        {
            position.add(velocity);
            velocity.decrease();
        }
        while (position.y >= area.yStart)
        {
            if (position.x >= area.xStart && position.x <= area.xEnd) return true;
            position.add(velocity);
            velocity.decrease();
        }
        return false;
    }
}

class Vector2
{
    public long x,y;
    public Vector2(long x, long y)
    {
        this.x = x;
        this.y = y;
    }
    public void add(Vector2 other)
    {
        this.x += other.x;
        this.y += other.y;
    }
    public void decrease()
    {
        if (x != 0) x = x > 0 ? x-1L : x+1L;
        y -= 1;
    }
}

class Area
{
    public int xStart, xEnd, yStart, yEnd;

    public Area(File file)
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            Matcher match = Pattern.compile("target area: x=(-?\\d*)..(-?\\d*), y=(-?\\d*)..(-?\\d*)").matcher(reader.readLine()); 
            match.find();
            xStart = Integer.parseInt(match.group(1));
            xEnd = Integer.parseInt(match.group(2));
            yStart = Integer.parseInt(match.group(3));
            yEnd = Integer.parseInt(match.group(4));
        }
        catch (Exception e)
        {
            Logger.error(e);
        }
    }
}
