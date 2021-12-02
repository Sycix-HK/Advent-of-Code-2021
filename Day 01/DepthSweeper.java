import java.util.*;
import java.io.*;

public class DepthSweeper {

    public static void main(String[] args) {
        int solution = 0;
        try (Scanner in = new Scanner(new FileReader(new File("input.txt"))))
        {
            int depth = Integer.parseInt(in.nextLine());
            while (in.hasNextLine())
            {
                solution = (depth < (depth = Integer.parseInt(in.nextLine()))) ? solution + 1 : solution;
            }
            in.close();
        }catch (IOException e){
            System.err.println(e);
        }
        System.out.println(solution);
    }

}
