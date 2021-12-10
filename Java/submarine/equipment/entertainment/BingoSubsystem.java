
//////////////////////////////////////////////////////////////
/////////////////////|      Day 04      |/////////////////////
//////////////////////////////////////////////////////////////

package submarine.equipment.entertainment;

import submarine.core.*;
import java.io.*;
import java.util.*;
import java.util.stream.Stream;

public class BingoSubsystem {
    public static void main(String[] args) {
        System.out.println("Winner board has "+getWinner(DataTray.getInput(4)).getScore()+" points");
        System.out.println("Last winning board has "+getLastWinning(DataTray.getInput(4)).getScore()+" points");
    }
    public static Board getWinner(File file)
    {
        Game game = new Game(file); 
        ArrayList<Integer> states = new ArrayList<>();
        for (Board board : game.boards)
        {
            states.add( board.winsAt(game.draws) );
        }
        return game.boards.get(states.indexOf(Collections.min(states)));
    }
    public static Board getLastWinning(File file)
    {
        Game game = new Game(file); 
        ArrayList<Integer> states = new ArrayList<>();
        for (Board board : game.boards)
        {
            states.add( board.winsAt(game.draws) );
        }
        return game.boards.get(states.indexOf(Collections.max(states)));
    }
}
class Game 
{
    List<Board> boards = new ArrayList<>();
    int[] draws;

    public Game(File file)
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            draws = Stream.of(reader.readLine().split(",")).mapToInt(Integer::parseInt).toArray();
            String line;
            ArrayList<Integer> fill = new ArrayList<>();
            while ((line = reader.readLine()) != null)
            {
                if (line.trim().length() == 0) 
                {
                    if (fill.size() == 25) boards.add(new Board(fill.stream().mapToInt(i -> i).toArray()));
                    fill.clear();
                }
                else
                {
                    String[] segments = line.trim().split(" ");
                    for (String segment : segments)
                    {
                        try
                        {
                            fill.add(Integer.parseInt(segment));
                        }catch(Exception e){}
                    }
                }
            }
        }
        catch (IOException e){}
    }
}
class Board
{
    enum State 
    {
        UNSOLVED, ROWWIN, COLUMNWIN
    }
    State state = State.UNSOLVED;
    int stateCode = 0;
    int winsOnValue = 0;
    boolean[] data;
    HashMap<Integer, Integer> indexof = new HashMap<>();
    private int score = 0;

    public Board(int[] data) { 
        this.data = new boolean[data.length];
        for (int i = 0; i < data.length; i++) indexof.put(data[i], i);
    }

    public int winsAt(int[] draws)
    {
        for (int i = 0; i < draws.length; i++)
        {
            try {
                int idx = indexof.get(draws[i]);
                data[idx] = true;
                if ((state = checkWin(idx)) != State.UNSOLVED)
                {
                    stateCode = (i*1000) + (idx + (state == State.COLUMNWIN ? 25 : 0));
                    winsOnValue = draws[i];
                    return stateCode;
                }
            } catch (Exception e) {}
        }
        return 0;
    }
    public State checkWin(int at)
    {
        int c = 0;
        int row = at - (at % 5);
        try {
            while (c != 5 && data[row++])
            {
                c++;
            }
        }catch(Exception e){}
        if (c == 5) return State.ROWWIN;
        c = 0;
        int col = at % 5;
        while (c != 5 && data[col])
        {
            col+=5;
            c++;
        }
        return (c == 5) ? State.COLUMNWIN : State.UNSOLVED;
    }
    public int getScore()
    {
        if (state == State.UNSOLVED) return 0;
        score = 0;
        for (int key : indexof.keySet())
        {
            if (!data[indexof.get(key)])
            {
                score += key;
            }
        }
        return score * winsOnValue;
    }
    @Override
    public String toString() {
        return indexof.toString();
    }
}
