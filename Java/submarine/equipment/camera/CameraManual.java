
//////////////////////////////////////////////////////////////
/////////////////////|      Day 13      |/////////////////////
//////////////////////////////////////////////////////////////

package submarine.equipment.camera;

import submarine.core.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class CameraManual {
    public static void main(String[] args) {
        TimeMeasure timer = new TimeMeasure();
        Logger.print(timer, "Dots after first fold", foldOnce(DataTray.getInput(13)));
        foldAll(DataTray.getInput(13));
    }

    public static int foldOnce(File file)
    {
        PageData data = new PageData(file);
        data.foldPage();
        return data.sumOfMarks();
    }

    public static void foldAll(File file)
    {
        PageData data = new PageData(file);
        int folds = data.folds.size();
        for (int i = 0; i < folds; i++)
        {
            data.foldPage();
        }
        data.display();
    }

}
class PageData
{
    private boolean[][] dots;
    Deque<Fold> folds = new ArrayDeque<>();
    public PageData(File file)
    {
        final int GRID_SIZE = 1500;
        List<String> lines = new InputScanner(file).getResult();
        dots = new boolean[GRID_SIZE][GRID_SIZE];
        int i = 0;
        String[] parts;
        while ((parts = lines.get(i++).split(",")).length == 2)
        {
            dots [Integer.parseInt(parts[1])] [Integer.parseInt(parts[0])] = true;
        }
        while (i<lines.size())
        {
            Matcher match = Pattern.compile("fold along ([xy])=(\\d*)").matcher(lines.get(i++)); 
            match.find();
            folds.push(new Fold(match.group(1).charAt(0),Integer.parseInt(match.group(2))));
        }
    }
    public int sumOfMarks()
    {
        int c = 0;
        for (boolean[] ba : dots)
        {
            for (boolean b : ba)
            {
                c += b ? 1 : 0;
            }
        }
        return c;
    }
    public void foldPage()
    {
        Fold foldat = folds.pollLast();
        int scopeSize = (foldat.at * 2);
        if (foldat.axis == Fold.Axis.X)
        {
            boolean[][] newdots = new boolean[dots.length][foldat.at];
            for (int y = 0; y < dots.length; y++)
            {
                for (int x = 0; x < foldat.at; x++)
                {
                    newdots[y][x] = (dots[y][x] || dots[y][scopeSize-x]);
                }
            }
            dots = newdots;
        }
        else 
        {
            boolean[][] newdots = new boolean[foldat.at][dots[0].length];
            for (int y = 0; y < foldat.at; y++)
            {
                for (int x = 0; x < dots[0].length; x++)
                {
                    newdots[y][x] = (dots[y][x] || dots[scopeSize-y][x]);
                }
            }
            dots = newdots;
        }
    }
    public void display()
    {
        for (int y = 0; y < dots.length; y++)
        {
            StringBuilder s = new StringBuilder();
            for (int x = 0; x < dots[0].length; x++)
            {
                s.append( dots[y][x] ? "■" : " ");
            }
            System.out.println(s);
        }
    }

    class Fold{
        enum Axis{X,Y}
        public final Axis axis;
        public final int at; 
        public Fold(char axis, int at)
        {
            this.at = at;
            switch (axis) {
                case 'x':
                    this.axis = Axis.X;
                    break;
                case 'y':
                    this.axis = Axis.Y;
                    break;
                default:
                    throw new IllegalArgumentException("I can't fold that way.. ("+axis+")");
            }
        }
    }
}
