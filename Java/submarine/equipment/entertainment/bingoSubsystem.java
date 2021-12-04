package submarine.equipment.entertainment;

import submarine.core.*;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

public class bingoSubsystem {
    public static Board getWinner(File file)
    {
        ArrayList<String> data = 
        return new Board();
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
           HashMap<Integer, Boolean> fill = new HashMap<>();
            while ((line = reader.readLine()) != null)
            {
                if (line.trim().length() == 0) 
                {
                    if (fill.size() == 25) boards.add(new Board(fill));
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
    HashMap<Integer,Boolean> data;
    int score = 0;
    public Board(HashMap<Integer,Boolean> data) { this.data = data; }

    public int winsAt(int[] draws)
    {
        for (int i = 0; i < draws.length; i++)
        {
            try {
                data.replace(draws[i],true);
                if ((state = checkWin(i)) != State.unsolved) return i + (state == State.columnWin ? 25 : 0);
            } catch (Exception e) {}
        }
        return 0;
    }
    public State checkWin(int at)
    {
        int row = at - (at % 5);
        while (row!=5 && data.get(row))
        {
            row++;
        }
        if (row == 5) return State.rowWin;
        int col = at % 5;
        while (col<25 && data.get(col))
        {
            col+=5;
        }
        return col > 24 ? State.columnWin : State.unsolved;
    }
}
