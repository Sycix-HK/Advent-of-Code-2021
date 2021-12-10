
//////////////////////////////////////////////////////////////
/////////////////////|      Day 02      |/////////////////////
//////////////////////////////////////////////////////////////

package submarine.equipment.propeller;

import submarine.core.*;
import java.io.*;
import java.util.*;

enum Direction {
    forward,
    up,down
}

public class PathCalculation {

    public static void main(String[] args) {
        PathCalculation path = new PathCalculation(DataTray.getInput(2));
        path.evaulateSimple();
        System.out.println("Simple calculations: ");
        System.out.println("Horizontal: "+ path.getHorizontal() + ", Depth: " + path.getDepth());
        System.out.println("Horizontal * depth = " + path.getHorizontal() * path.getDepth());
        path.resetPosition();
        path.evaulateAdvanced();
        System.out.println("Advanced calculations: ");
        System.out.println("Horizontal: "+ path.getHorizontal() + ", Depth: " + path.getDepth());
        System.out.println("Horizontal * depth = " + path.getHorizontal() * path.getDepth());
    }

    private int depth = 0;
    private int horizontal = 0;
    private int aim = 0;
    private ArrayList<Instruction> instructions = new ArrayList<>();

    public int getDepth() {return depth; }
    public int getHorizontal() {return horizontal; }

    public PathCalculation(File file) {
        ArrayList<String> data = new submarine.core.InputScanner(file).getResult();

        for (String line : data) {
            String[] splitline = line.trim().split(" ");
            instructions.add(new Instruction(
                Direction.valueOf(splitline[0]),
                Integer.parseInt(splitline[1])
            ));
        }
    }

    public void evaulateSimple()
    {
        for (Instruction ins : instructions) {
            switch (ins.dir)
            {
                case forward:
                    horizontal += ins.units;
                    break;
                case up:
                    depth -= ins.units;
                    break;
                case down:
                    depth += ins.units;
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

    public void evaulateAdvanced()
    {
        for (Instruction ins : instructions) {
            switch (ins.dir)
            {
                case forward:
                    horizontal += ins.units;
                    depth += aim*ins.units;
                    break;
                case up:
                    aim -= ins.units;
                    break;
                case down:
                    aim += ins.units;
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

    public void resetPosition()
    {
        depth = 0;
        horizontal = 0;
        aim = 0;
    }
}


class Instruction {
    public final Direction dir;
    public final int units;

    public Instruction(Direction dir, int units) {
        this.dir = dir;
        this.units = units;
    }
}
