
//////////////////////////////////////////////////////////////
/////////////////////|      Day 12      |/////////////////////
//////////////////////////////////////////////////////////////

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
        CaveSystem system = new CaveSystem(file);

        return deepSearch( system.caves.get("start") , new HashSet<>());
    }

    @SuppressWarnings("unchecked")
    static int deepSearch(Cave cave, HashSet<String> pathway)
    {
        if (cave.getName().equals("end")) return 1;
        HashSet<String> journey = (HashSet<String>) pathway.clone();
        journey.add(cave.getName());
        int count = 0;
        for (Cave route : cave.connections)
        {
            if ( route.size == Cave.Size.LARGE || !pathway.contains(route.getName()) )
            {
                count += deepSearch(route, journey);
            }
        }
        return count;
    }
}

class CaveSystem
{
    public HashMap<String, Cave> caves = new HashMap<String, Cave>();
    public CaveSystem(File file)
    {
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
