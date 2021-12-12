
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
        Logger.print(timer, "Paths in cave visiting smalls once", countPaths(DataTray.getInput(12),SearchType.QUICK));
        Logger.print(timer, "Paths in cave visiting smalls twice", countPaths(DataTray.getInput(12),SearchType.YEAHIVEGOTTIME));
    }

    enum SearchType{  QUICK, YEAHIVEGOTTIME  }

    public static long countPaths(File file, SearchType searchType)
    {
        CaveSystem system = new CaveSystem(file);

        return searchType == SearchType.QUICK ? 
            deepSearchQuick  ( system.caves.get("start") , new HashSet<>()) :
            deepSearchLeisure( system.caves.get("start") , new HashSet<>()) ;
    }

    @SuppressWarnings("unchecked")
    static long deepSearchQuick(Cave cave, HashSet<String> pathway)
    {
        if (cave.getName().equals("end")) return 1;
        HashSet<String> journey = (HashSet<String>) pathway.clone();
        journey.add(cave.getName());
        int count = 0;
        for (Cave route : cave.connections)
        {
            if ( route.size == Cave.Size.LARGE || !pathway.contains(route.getName()) )
            {
                count += deepSearchQuick(route, journey);
            }
        }
        return count;
    }
    @SuppressWarnings("unchecked")
    static long deepSearchLeisure(Cave cave, HashSet<String> pathway)
    {
        if (cave.getName().equals("end")) return 1;
        HashSet<String> journey = (HashSet<String>) pathway.clone();
        journey.add( 
            (journey.contains(cave.getName()) && cave.size == Cave.Size.SMALL) ? "CLOSED-FOR-REPEATS" :  cave.getName()
        );

        long count = 0;
        for (Cave route : cave.connections)
        {
            if ( !route.getName().equals("start") && 
                (route.size == Cave.Size.LARGE || !journey.contains("CLOSED-FOR-REPEATS") || !pathway.contains(route.getName())) )
            {
                count += deepSearchLeisure(route, journey);
            }
        }
        return count;
    }
}

class CaveSystem
{
    public final Map<String, Cave> caves = new HashMap<>();
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
