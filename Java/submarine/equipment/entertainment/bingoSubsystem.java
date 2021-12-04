package submarine.equipment.entertainment;

import submarine.core.*;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

public class BingoSubsystem {
    public static void main(String[] args) {
        System.out.println("Winner board has "+getWinner(DataTray.getInput(4)).getScore()+" points");
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
}
class Game 
{
    public ArrayList<Board> boards = new ArrayList<>();
    public int[] draws;
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
            reader.close();
        }
        catch (IOException e){}
    }
}
class Board
{
    enum State 
    {
        unsolved, rowWin, columnWin
    }
    State state = State.unsolved;
    int stateCode = 0;
    int winsOnValue = 0;
    Boolean[] data;
    HashMap<Integer, Integer> indexof = new HashMap<>();
    private int score = 0;

    public Board(int[] data) { 
        this.data = new Boolean[data.length];
        for (int i = 0; i < data.length; i++) indexof.put(data[i], i);
    }

    public int winsAt(int[] draws)
    {
        for (int i = 0; i < draws.length; i++)
        {
            try {
                int idx = indexof.get(draws[i]);
                data[idx] = true;
                if ((state = checkWin(idx)) != State.unsolved)
                {
                    stateCode = (i*1000) + (idx + (state == State.columnWin ? 25 : 0));
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
        if (c == 5) return State.rowWin;
        c = 0;
        int col = at % 5;
        while (c != 5 && data[col])
        {
            col+=5;
            c++;
        }
        return (c == 5) ? State.columnWin : State.unsolved;
    }
    public int getScore()
    {
        if (state == State.unsolved) return 0;
        score = 0;
        for (int key : indexof.keySet())
        {
            if (data[indexof.get(key)] == null)
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
