package submarine.equipment.sonar.caves;

import submarine.core.*;
import java.io.*;
import java.util.*;

public class PathFinder {
    public static void main(String[] args) {
        TimeMeasure timer = new TimeMeasure();
        Logger.print(timer, "Paths in cave", countPaths(DataTray.getInput(12)));
    }

    public static long countPaths(File file)
    {
        HashMap<String, Cave> caves = new HashMap<>();
        for (String line : new InputScanner(file).getResult() )
        {
            String[] linePieces = line.split("-");
            if (!caves.containsKey(linePieces[0]))
            {
                caves.put(linePieces[0], new Cave(linePieces[0]));
            }
            if (!caves.containsKey(linePieces[1]))
            {
                caves.put(linePieces[1], new Cave(linePieces[1]));
            }

            Cave.connect(caves.get(linePieces[0]), caves.get(linePieces[1]));
        }
        return 0;
    }
}

class Cave
{
    public final List<Cave> connections = new ArrayList<>();

    enum Size {  SMALL,LARGE  }
    public final Size size;

    private final String name;
    public String getName() { return name;}

    public Cave(String name)
    {
        this.name = name;
        size = Character.isUpperCase(name.charAt(0)) ? Size.LARGE : Size.SMALL;
    }

    public static void connect(Cave first, Cave second)
    {
        if (!first.connections.contains(second))
        {
            first.connections.add(second);
            second.connections.add(first);
        }
    }
}
