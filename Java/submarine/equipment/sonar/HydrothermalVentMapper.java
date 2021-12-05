
//////////////////////////////////////////////////////////////
/////////////////////|      Day 05      |/////////////////////
//////////////////////////////////////////////////////////////

package submarine.equipment.sonar;

import java.io.*;
import java.util.regex.*;

import submarine.core.*;

public class HydrothermalVentMapper {
    public enum BathymetryMapType 
    {
        simple, diagonal
    }
    public static void main(String[] args) {
        System.out.println(SimpleMap(DataTray.getInput(5), BathymetryMapType.simple).dangerZones);
        System.out.println(SimpleMap(DataTray.getInput(5), BathymetryMapType.diagonal).dangerZones);
    }
    
    public static Bathymetry SimpleMap(File file, BathymetryMapType mapType)
    {
        Bathymetry mapping = new Bathymetry(); 
        for (String line : (new InputScannerString(file).getResult()))
        {
            Matcher match = Pattern.compile("(\\d*),(\\d*) -> (\\d*),(\\d*)").matcher(line); 
            match.find();
            int[] coord = new int[] { 
                Integer.parseInt(match.group(1)), 
                Integer.parseInt(match.group(2)), 
                Integer.parseInt(match.group(3)), 
                Integer.parseInt(match.group(4))};

            if (coord[0] == coord[2] && coord[1] != coord[3])
            {
                mapping.add(coord[0],coord[1]);
                int inc = coord[1] > coord[3] ? -1 : + 1;
                while (coord[1] != coord[3])
                {
                    coord[1] += inc;
                    mapping.add(coord[0],coord[1]);
                }
            }
            else if (coord[0] != coord[2] && coord[1] == coord[3])
            {
                mapping.add(coord[0],coord[1]);
                int inc = coord[0] > coord[2] ? -1 : + 1;
                while (coord[0] != coord[2])
                {
                    coord[0] += inc;
                    mapping.add(coord[0],coord[1]);
                }
            }
            else if (mapType == BathymetryMapType.diagonal)
            {

            }
        }
        return mapping;
    }
}

class Bathymetry
{
    public int[][] map = new int[1000][1000];
    public int dangerZones = 0;
    public void add(int x, int y)
    {
        if (++map[x][y] == 2) dangerZones++;
    }
}