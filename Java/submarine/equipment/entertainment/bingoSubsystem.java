package submarine.equipment.entertainment;

public class bingoSubsystem {
    public static Board getWinner()
    {
        return new Board();
    }
}
class Board
{
    int[][] data;
    int score = 0;
    public Board(int[][] data) { this.data = data; }

    public int winsAt(int[] draws)
    {
        for (int i = 0; i < draws.length; i++)
        {

        }
    }
}
